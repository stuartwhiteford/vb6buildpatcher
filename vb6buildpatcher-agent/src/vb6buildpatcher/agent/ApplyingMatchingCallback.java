package vb6buildpatcher.agent;

import com.intellij.openapi.diagnostic.Logger;
import java.io.File;
import java.io.IOException;
import jetbrains.buildServer.agent.BuildProgressLogger;
import org.jetbrains.annotations.NotNull;
/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 20/02/13
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */

public class ApplyingMatchingCallback
        implements MatchingCallback
{
    private static final Logger LOG = Logger.getInstance(ApplyingMatchingCallback.class.getName());
    private final VB6BuildPatcherSettings mySettings;

    public ApplyingMatchingCallback(VB6BuildPatcherSettings settings)
    {
        this.mySettings = settings;
    }

    public void onFileFound(@NotNull Patcher patcher, @NotNull File file)
    {
        if (patcher == null)
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of vb6buildpatcher/agent/ApplyingMatchingCallback.onFileFound must not be null"); if (file == null) throw new IllegalArgumentException("Argument 1 for @NotNull parameter of vb6buildpatcher/agent/ApplyingMatchingCallback.onFileFound must not be null");
        try
        {
            patcher.patchFile(this.mySettings, file);
        }
        catch (IOException e)
        {
            String msg = "Failed to patch assembly info in " + file + " with " + patcher.getName() + ". " + e.getMessage();
            LOG.warn(msg, e);
            this.mySettings.getLogger().warning(msg);
        }
    }
}
