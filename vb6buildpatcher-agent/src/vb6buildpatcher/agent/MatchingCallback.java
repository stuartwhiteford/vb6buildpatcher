package vb6buildpatcher.agent;

import java.io.File;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 20/02/13
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */
public abstract interface MatchingCallback
{

    public abstract void onFileFound(@NotNull Patcher paramPatcher, @NotNull File paramFile);

}
