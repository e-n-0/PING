package fr.epita.assistants.myide.ricains.entity.features.maven;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Maven;
import fr.epita.assistants.myide.ricains.entity.features.RicainsExecutionReport;

public class TestFeature implements Feature {

    @Override
    public @NotNull ExecutionReport execute(Project project, Object... params) {
        String path = project.getRootNode().getPath().toString();
        String cmdParams = "mvn ";
        if (params != null) {
            cmdParams += "-Dtest=";
            for (Object prout : params) {
                cmdParams += prout + ",";

            }
            cmdParams = cmdParams.substring(0, cmdParams.length() - 1);

        }
        cmdParams += " test " + path;
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
    }

    @Override
    public @NotNull Type type() {
        return Maven.TEST;
    }

}
