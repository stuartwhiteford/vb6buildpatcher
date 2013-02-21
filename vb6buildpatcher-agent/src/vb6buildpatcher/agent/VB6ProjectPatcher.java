package vb6buildpatcher.agent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 20/02/13
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
public class VB6ProjectPatcher extends ProjectPatcherBase {

    public VB6ProjectPatcher()
    {
        super("VB6 patcher", "**/*.vbp");
    }

    @NotNull
    protected String replaceVersion(@NotNull String code, @NotNull String newVersion)
    {
        /*
        MajorVer=1
        MinorVer=3
        RevisionVer=49
        */
        // Split out a three or four-part build number.
        String[] parts = newVersion.split(Pattern.quote("."));

        if (parts.length == 3 || (parts.length == 4 && parts[2].equalsIgnoreCase("0")))
        {
            String major = "MajorVer=" + parts[0];
            String minor = "MinorVer=" + parts[1];
            String revision;
            if (parts.length == 3)
            {
                revision = "RevisionVer=" + parts[2];
            }
            else
            {
                revision = "RevisionVer=" + parts[3];
            }
            Pattern ptMajor = Pattern.compile("^MajorVer=[0-9]*$", 10);
            Pattern ptMinor = Pattern.compile("^MinorVer=[0-9]*$", 10);
            Pattern ptRevision = Pattern.compile("^RevisionVer=[0-9]*$", 10);
            Matcher matcher1 = ptMajor.matcher(code);
            String code1 = matcher1.replaceAll(major);
            Matcher matcher2 = ptMinor.matcher(code1);
            String code2 = matcher2.replaceAll(minor);
            Matcher matcher3 = ptRevision.matcher(code2);
            String code3 = matcher3.replaceAll(revision);
            return code3;
        }
        else
        {
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of vb6buildpatcher/agent/VBProjectPatcher.replaceVersion mst be a three part build number or four part build number with 0 as the build part.");
        }
    }

}