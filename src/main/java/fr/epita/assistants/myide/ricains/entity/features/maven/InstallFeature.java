package fr.epita.assistants.myide.ricains.entity.features.maven;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Maven;
import fr.epita.assistants.myide.ricains.entity.features.RicainsExecutionReport;

import java.lang.Process;
import java.nio.file.Path;

public class InstallFeature implements Feature {

    @Override
    public @NotNull ExecutionReport execute(Project project, Object... params) {
        String path = project.getRootNode().getPath().toString();
        String cmdParams = "mvn install" + path;
        for (Object prout : params)
            cmdParams += " " + prout;
        Process p;
        try {
            p = Runtime.getRuntime().exec(cmdParams);
            p.waitFor();
            RicainsExecutionReport ret = RicainsExecutionReport.create((p.exitValue() == 0));
            return ret;
        } catch (Exception e) {
            RicainsExecutionReport ret = RicainsExecutionReport.create(false);
            return ret;
        }

    }

    @Override
    public @NotNull Type type() {
        return Maven.INSTALL;
    }

}
