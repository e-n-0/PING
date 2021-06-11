package fr.epita.assistants;

import java.nio.file.Path;
import java.nio.file.Paths;

import fr.epita.assistants.MyIde.Configuration;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.ProjectService;
import fr.epita.assistants.utils.Log;

public class Main {
    public static void main(String[] args) {
        MyIde ide = new MyIde();
        ProjectService projectService = ide.init(new Configuration(Paths.get("."), Paths.get(".")));
        Path path = Paths.get(".");
        Log.log(path.toAbsolutePath());
        Project project = projectService.load(path);

        var report = project.getFeature(Mandatory.Features.Any.DIST).get().execute(project);
        System.out.println("report: " + report.isSuccess());
    }
}
