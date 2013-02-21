package vb6buildpatcher.agent;

import java.io.File;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 20/02/13
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */
public interface Patcher {

    @NotNull
    public abstract String getName();

    @NotNull
    public abstract String getPattern();

    public abstract void patchFile(@NotNull VB6BuildPatcherSettings paramAssemblyInfoPatcherSettings, @NotNull File paramFile)
            throws IOException;

}
