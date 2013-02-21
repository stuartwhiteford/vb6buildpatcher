package vb6buildpatcher.agent;

import com.intellij.openapi.diagnostic.Logger;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.util.FileUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 20/02/13
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public abstract class ProjectPatcherBase implements Patcher
{
    private static final Logger LOG = Logger.getInstance(ProjectPatcherBase.class.getName());
    private final String myName;
    private final String myPattern;

    protected ProjectPatcherBase(String name, String pattern)
    {
        this.myName = name;
        this.myPattern = pattern;
    }

    @NotNull
    public String getName()
    {
        return this.myName;
    }

    @NotNull
    public String getPattern()
    {
        return this.myPattern;
    }

    public void patchFile(@NotNull VB6BuildPatcherSettings settings, @NotNull File assemblyInfo) throws IOException {
        if (settings == null)
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of vb6buildpatcher/agent/ProjectPatcherBase.patchFile must not be null");
        if (assemblyInfo == null)
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of vb6buildpatcher/agent/ProjectPatcherBase.patchFile must not be null");
        String rel = assemblyInfo.getPath();
        try
        {
            String message = "Updating assembly version in " + rel;
            settings.getLogger().message(message);
            LOG.info(message);

            for (String encoding : settings.getEncoding()) {
                String code = new String(FileUtil.loadFileText(assemblyInfo, encoding));
                String oldCode = code;
                String version = settings.getVersion();
                settings.getLogger().message("Updating assembly version to " + version);
                code = replaceVersion(code, version);
                if (!oldCode.equals(code)) {
                    FileUtil.writeToFile(assemblyInfo, code.getBytes(encoding));
                    return;
                }
            }
        }
        catch (Exception e)
        {
            settings.getLogger().error(e.toString());
        }
        settings.getLogger().warning("Assembly version attributes were not found in " + rel);
    }

    @NotNull
    protected abstract String replaceVersion(@NotNull String paramString1, @NotNull String paramString2);

}