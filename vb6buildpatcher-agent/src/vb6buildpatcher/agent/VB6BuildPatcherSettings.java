package vb6buildpatcher.agent;

import java.io.File;
import java.util.Collection;
import jetbrains.buildServer.agent.BuildProgressLogger;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 20/02/13
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
public interface VB6BuildPatcherSettings {

    public abstract boolean isEnabled();

    @NotNull
    public abstract File getCheckoutDirectory();

    @NotNull
    public abstract File getFilesCachePath();

    @NotNull
    public abstract String getVersion();

    @NotNull
    public abstract Collection<String> getEncoding();

    @NotNull
    public abstract BuildProgressLogger getLogger();

    public abstract int getScanDeep();

}