package vb6buildpatcher.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import jetbrains.buildServer.parameters.ReferencesResolverUtil;
import jetbrains.buildServer.serverSide.BuildFeature;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import vb6buildpatcher.common.*;

/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 20/02/13
 * Time: 13:19
 * To change this template use File | Settings | File Templates.
 */
public class VB6BuildPatcherFeature extends BuildFeature {

    private final PluginDescriptor myDescriptor;

    public VB6BuildPatcherFeature(@NotNull PluginDescriptor descriptor)
    {
        this.myDescriptor = descriptor;
    }

    @NotNull
    public String getType()
    {
        return PluginConstants.RunType;
    }

    @NotNull
    public String getDisplayName()
    {
        return PluginConstants.DisplayName;
    }

    public String getEditParametersUrl()
    {
        return this.myDescriptor.getPluginResourcesPath("editVB6BuildPatcherParameters.jsp");
    }

    @NotNull
    public String describeParameters(@NotNull Map<String, String> parameters)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Version number:");
        sb.append(parameters.get(PluginConstants.Key_VersionFormat));

        return sb.toString();
    }

    public PropertiesProcessor getParametersProcessor()
    {
        return new PropertiesProcessor()
        {
            public Collection<InvalidProperty> process(Map<String, String> properties)
            {
                ArrayList<InvalidProperty> toReturn = new ArrayList<InvalidProperty>();
                if (!properties.containsKey(PluginConstants.Key_VersionFormat) || com.intellij.openapi.util.text.StringUtil.isEmpty(properties.get(PluginConstants.Key_VersionFormat)))
                    toReturn.add(new InvalidProperty(PluginConstants.Key_VersionFormat, "Please enter the version number."));

                return toReturn;
            }
        };
    }

    public Map<String, String> getDefaultParameters()
    {
        return null;
    }

    public boolean isMultipleFeaturesPerBuildTypeAllowed()
    {
        return false;
    }

}