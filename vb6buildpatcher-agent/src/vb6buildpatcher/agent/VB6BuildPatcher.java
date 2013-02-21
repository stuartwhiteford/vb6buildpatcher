package vb6buildpatcher.agent;

import java.io.File;
import java.util.Collection;
import jetbrains.buildServer.agent.BuildProgressLogger;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 20/02/13
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
public class VB6BuildPatcher {

    private static final String TYPE = "vb6buildPatcher";
    private static final String APPLY = "Update assembly versions";
    private static final String REVERT = "Reverting patched assembly versions";
    private final VB6BuildPatcherSettings mySettings;
    private final Collection<Patcher> myPatchers;
    private final BackupMatchingCallback myCallback;

    public VB6BuildPatcher(@NotNull VB6BuildPatcherSettings settings, @NotNull Collection<Patcher> patchers)
    {
        this.mySettings = settings;
        this.myPatchers = patchers;
        this.myCallback = new BackupMatchingCallback(settings, new ApplyingMatchingCallback(settings));
    }

    public void start(File checkoutDirectory) {
        String description = "scanning checkout directory for .vbp files to update version to " + this.mySettings.getVersion();
        this.mySettings.getLogger().activityStarted(APPLY, description, TYPE);
        try {
            int count = new SimpleMatcher(this.myPatchers, this.myCallback).matcher(checkoutDirectory, this.mySettings.getScanDeep());

            if (count == 0)
                this.mySettings.getLogger().warning("Found no *.vbp files");
        }
        finally {
            this.mySettings.getLogger().activityFinished(APPLY, TYPE);
        }
    }

    public void dispose() {
        this.mySettings.getLogger().activityStarted(REVERT, TYPE);
        try {
            this.myCallback.restoreState();
        } finally {
            this.mySettings.getLogger().activityFinished(REVERT, TYPE);
        }
    }

}
