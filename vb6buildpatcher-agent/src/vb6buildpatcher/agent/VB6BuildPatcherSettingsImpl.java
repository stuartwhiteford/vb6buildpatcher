package vb6buildpatcher.agent;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;

import jetbrains.buildServer.agent.*;
import vb6buildpatcher.common.*;
import jetbrains.buildServer.util.StringUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 20/02/13
 * Time: 13:41
 * To change this template use File | Settings | File Templates.
 */
public class VB6BuildPatcherSettingsImpl implements VB6BuildPatcherSettings
{
    private final PluginConstants myConsts = new PluginConstants();
    private final AgentRunningBuild myBuild;

    public VB6BuildPatcherSettingsImpl(@NotNull AgentRunningBuild build)
    {
        this.myBuild = build;
    }

    public boolean isEnabled() {
        return !this.myBuild.getBuildFeaturesOfType("vb6BuildPatcher").isEmpty();
    }

    @NotNull
    public File getCheckoutDirectory()
    {
        return this.myBuild.getCheckoutDirectory();
    }

    @NotNull
    public Collection<String> getEncoding()
    {
        Collection<String> encodings = new ArrayList<String>(1);
        encodings.add(Charset.defaultCharset().name());
        return encodings;
    }

    @NotNull
    public BuildProgressLogger getLogger()
    {
        return this.myBuild.getBuildLogger();
    }

    public int getScanDeep() {
        try {
            String deep = this.myBuild.getSharedConfigParameters().get("system.teamcity.vb6buildPatcher.scanDepth");
            if (!StringUtil.isEmptyOrSpaces(deep)) {
                int v = Integer.parseInt(deep);
                if (v > 0) return v;
            }
        }
        catch (Exception e)
        {
            this.myBuild.getBuildLogger().warning("Failed to parse 'system.teamcity.vb6buildPatcher.scanDepth'. Default value will be used.");
        }
        return 9;
    }

    @NotNull
    public File getFilesCachePath()
    {
        return this.myBuild.getAgentConfiguration().getCacheDirectory("vb6build-patcher");
    }

    @NotNull
    private String getParameter(@NotNull String key, @NotNull String def)
    {
        String parameter = def;
        try
        {
            AgentBuildFeature[] features = new AgentBuildFeature[1];
            this.myBuild.getBuildFeaturesOfType("vb6BuildPatcher").toArray(features);
            if (features.length > 0)
            {
                AgentBuildFeature feature = features[0];
                parameter = feature.getParameters().get(key);
                if (isNullOrWhitespace(parameter))
                {
                    parameter = def;
                }
            }
        }
        catch (Exception e)
        {
            this.myBuild.getBuildLogger().error(e.toString());
        }
        return parameter;
    }

    @NotNull
    public String getVersion()
    {
        return getParameter(this.myConsts.getVersionFormatKey(), this.myBuild.getBuildNumber());
    }

    private boolean isNullOrWhitespace(String s) {
        return s == null || isWhitespace(s);

    }
    private boolean isWhitespace(String s) {
        int length = s.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(s.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}