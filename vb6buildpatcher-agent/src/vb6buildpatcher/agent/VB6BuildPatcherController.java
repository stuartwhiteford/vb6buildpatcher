package vb6buildpatcher.agent;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.util.EventDispatcher;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 20/02/13
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
public class VB6BuildPatcherController {

    private final Collection<Patcher> myFilePatchers;

    public VB6BuildPatcherController(@NotNull EventDispatcher<AgentLifeCycleListener> events, @NotNull Collection<Patcher> filePatchers)
    {
        this.myFilePatchers = filePatchers;

        events.addListener(new AgentLifeCycleAdapter()
        {
            @SuppressWarnings("unchecked")
            private final Map<Long, VB6BuildPatcher> myPatchers = new ConcurrentHashMap();

            public void beforeRunnerStart(@NotNull BuildRunnerContext runner)
            {
                if (runner == null)
                    throw new IllegalArgumentException("Argument 0 for @NotNull parameter of vb6buildpatcher/agent/VB6BuildPatcherController.beforeRunnerStart must not be null"); AgentRunningBuild runningBuild = runner.getBuild();
                if (this.myPatchers.containsKey(Long.valueOf(runningBuild.getBuildId())))
                    return;

                VB6BuildPatcherSettings settings = new VB6BuildPatcherSettingsImpl(runningBuild);

                if (settings.isEnabled()) {
                    VB6BuildPatcher patcher = new VB6BuildPatcher(settings, VB6BuildPatcherController.this.myFilePatchers);
                    this.myPatchers.put(Long.valueOf(runningBuild.getBuildId()), patcher);
                    patcher.start(runningBuild.getCheckoutDirectory());
                }
            }

            public void beforeBuildFinish(@NotNull AgentRunningBuild build, @NotNull BuildFinishedStatus buildStatus)
            {
                if (build == null)
                    throw new IllegalArgumentException("Argument 0 for @NotNull parameter of vb6buildpatcher/agent/VB6BuildPatcherController.beforeBuildFinish must not be null");
                if (buildStatus == null)
                    throw new IllegalArgumentException("Argument 1 for @NotNull parameter of vb6buildpatcher/agent/VB6BuildPatcherController.beforeBuildFinish must not be null");

                VB6BuildPatcher remove = this.myPatchers.remove(Long.valueOf(build.getBuildId()));

                if (remove != null)
                    remove.dispose();
            }
        });
    }

}
