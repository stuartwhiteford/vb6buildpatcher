package vb6buildpatcher.common;

import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 20/02/13
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */
public class PluginConstants {

    public static String DisplayName = "Visual Basic 6 Patcher";
    public static String RunType = "vb6BuildPatcher";
    public static String Description = "Microsoft Visual Basic 6 project file patcher.";

    public static String Key_VersionFormat = "versionFormat";

    @NotNull
    public String getVersionFormatKey()
    {
        return "versionFormat";
    }

    /*
    public static final String FEATURE_TYPE = "JetBrains.AssemblyInfo";

    @NotNull
    public String getAssebmlyVersionFormatKey()
    {
        String tmp2_0 = "assembly-format"; if (tmp2_0 == null) throw new IllegalStateException("@NotNull method vb6buildpatcher/AssemblyInfoConstants.getAssebmlyVersionFormatKey must not return null"); return tmp2_0;
    }

    @NotNull
    public String getAssebmlyFileVersionFormatKey()
    {
        String tmp2_0 = "file-format"; if (tmp2_0 == null) throw new IllegalStateException("@NotNull method vb6buildpatcher/AssemblyInfoConstants.getAssebmlyFileVersionFormatKey must not return null"); return tmp2_0;
    }
    */
}