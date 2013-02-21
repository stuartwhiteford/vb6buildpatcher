package vb6buildpatcher.agent;

import java.io.File;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.AntPathMatcher;

/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 20/02/13
 * Time: 14:07
 * To change this template use File | Settings | File Templates.
 */

public class SimpleMatcher
{
    private final AntPathMatcher myMatcher = new AntPathMatcher();
    private final MatchingCallback myCallback;
    private final Collection<Patcher> myPatchers;

    public SimpleMatcher(Collection<Patcher> patchers, MatchingCallback callback)
    {
        this.myPatchers = patchers;
        this.myCallback = callback;
    }

    public int matcher(@NotNull File fullPath, int deep) {
        if (fullPath == null)
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of vb6buildpatcher/agent/SimpleMatcher.matcher must not be null"); boolean isMatched = false;
        if (fullPath.isFile()) {
            for (Patcher patcher : this.myPatchers) {
                if (this.myMatcher.match(patcher.getPattern(), fullPath.getPath().replace('\\', '/'))) {
                    this.myCallback.onFileFound(patcher, fullPath);
                    isMatched = true;
                }
            }
            return isMatched ? 1 : 0;
        }
        if (deep < 0) return 0;

        File[] files = fullPath.listFiles();
        if (files == null) return 0;

        int count = 0;
        for (File file : files) {
            count += matcher(file, deep - 1);
        }
        return count;
    }
}
