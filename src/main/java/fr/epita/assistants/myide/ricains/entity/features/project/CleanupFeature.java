package fr.epita.assistants.myide.ricains.entity.features.project;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Any;
import fr.epita.assistants.myide.ricains.entity.features.RicainsExecutionReport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CleanupFeature implements Feature {

    @Override
    public @NotNull ExecutionReport execute(Project project, Object... params) {
        File trashFile = new File(project.getRootNode().getPath().toString() + ".myideignore");
        try (BufferedReader br = new BufferedReader(new FileReader(trashFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                File delete = new File(line);
                if (delete.exists()) {
                    delete.delete();
                }
                else {
                    continue;
                }
            }
        } catch (IOException e) {
            return RicainsExecutionReport.create(false);
            //e.printStackTrace();
        }
        return RicainsExecutionReport.create(true);
    }

    @Override
    public @NotNull Type type() {
        return Any.CLEANUP;
    }

}
