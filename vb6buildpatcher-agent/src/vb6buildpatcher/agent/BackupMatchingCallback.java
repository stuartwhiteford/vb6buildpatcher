package vb6buildpatcher.agent;

import com.intellij.openapi.diagnostic.Logger;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.util.FileUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 20/02/13
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */

public class BackupMatchingCallback implements MatchingCallback
{
    private static final Logger LOG = Logger.getInstance(BackupMatchingCallback.class.getName());
    private final File myBackupRoot;
    private final VB6BuildPatcherSettings mySettings;
    private final MatchingCallback myNext;
    private final AtomicInteger myCounter = new AtomicInteger(42);

    @SuppressWarnings("unchecked")
    private final Map<File, File> myBackup = new TreeMap();

    public BackupMatchingCallback(@NotNull VB6BuildPatcherSettings settings, @NotNull MatchingCallback next) {
        this.mySettings = settings;
        this.myNext = next;
        this.myBackupRoot = settings.getFilesCachePath();
    }

    private void backupFile(@NotNull File file) {
        if (file == null) throw new IllegalArgumentException("Argument 0 for @NotNull parameter of vb6buildpatcher/agent/BackupMatchingCallback.backupFile must not be null"); if (!file.isFile()) {
            LOG.warn("Failed to backup :" + file + ", path does not exist or is not a file");
        }
        File backup;
        try
        {
            backup = FileUtil.createTempFile(this.myBackupRoot, String.valueOf(this.myCounter.incrementAndGet()), ".AssemblyInfo", false);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create backup file for " + file + ". " + e.getMessage(), e);
        }
        try
        {
            FileUtil.setReadOnlyAttribute(file.getPath(), false);
        } catch (IOException e) {
            LOG.warn("Failed to clean readonly attribute for: " + file);
        }
        try
        {
            FileUtil.copy(file, backup);
            this.myBackup.put(file, backup);
        } catch (IOException e) {
            String message = "Failed to create backup copy of file: " + file + " to " + backup + ". " + e;
            LOG.warn(message, e);
            this.mySettings.getLogger().warning(message);
        }
    }

    public void restoreState() {
        for (Map.Entry entry : this.myBackup.entrySet()) {
            File file = (File)entry.getKey();
            File backup = (File)entry.getValue();

            this.mySettings.getLogger().message("Restoring " + file);
            try {
                if (file.exists()) {
                    FileUtil.setReadOnlyAttribute(file.getPath(), false);
                }
                FileUtil.copy(backup, file);
            }
            catch (IOException e) {
                String message = "Failed to restore file: " + file + " from " + backup + ". " + e.getMessage();
                LOG.warn(message, e);
                this.mySettings.getLogger().warning(message);
            } finally {
                FileUtil.delete(backup);
            }
        }
    }

    public void onFileFound(@NotNull Patcher patcher, @NotNull File file) {
        if (patcher == null)
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of vb6buildpatcher/agent/BackupMatchingCallback.onFileFound must not be null"); if (file == null) throw new IllegalArgumentException("Argument 1 for @NotNull parameter of vb6buildpatcher/agent/BackupMatchingCallback.onFileFound must not be null");
        backupFile(file);
        this.myNext.onFileFound(patcher, file);
    }
}
