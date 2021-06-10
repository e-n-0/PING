package fr.epita.assistants;

import java.nio.file.Paths;

import fr.epita.assistants.MyIde.Configuration;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;
import fr.epita.assistants.utils.Log;

public class Main {
    public static void main(String[] args) {
        MyIde ide = new MyIde();
        ProjectService projectService = ide.init(new Configuration(Paths.get("."), Paths.get(".")));
        Project project = projectService.load(Paths.get("."));

        // var report =
        // project.getFeature(Mandatory.Features.Git.PUSH).get().execute(project,
        // "oui");
        // System.out.println("report: " + report.isSuccess());

        Node myroot = project.getRootNode();

        Log.log(myroot.getChildren().size() + " - " + myroot.getType().toString());

        NodeService service = projectService.getNodeService();
        // Log.log(myroot.getPath().toString());

        // Node todel = service.create(myroot, "jesuisundossier", Node.Types.FOLDER);
        // Log.log(todel.getPath().toString());
        // Node insiedToDel = service.create(todel, "ouinon", Node.Types.FILE);
        // Log.log(insiedToDel.getPath().toAbsolutePath().toString());

        /*
         * var list = myroot.getChildren(); for (Node n : list) { var list2 =
         * n.getChildren(); Log.log(n.getPath().toString() + " - " +
         * n.getType().toString() + " - " + list2.size()); for (Node n2 : list2)
         * Log.log(n2.getPath().toString()); }
         */

        // service.move(todel, "./test2");

    }
}
