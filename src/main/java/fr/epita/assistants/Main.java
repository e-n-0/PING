package fr.epita.assistants;

import java.nio.file.Paths;

import fr.epita.assistants.MyIde.Configuration;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.ProjectService;

public class Main {
    public static void main(String[] args) {
        MyIde ide = new MyIde();
        ProjectService projectService = ide.init(new Configuration(Paths.get("."), Paths.get(".")));
        Project project = projectService.load(Paths.get("pom.xml"));

        var report = project.getFeature(Mandatory.Features.Any.CLEANUP).get().execute(project);
        System.out.println("report: " + report.isSuccess());
    }
}
