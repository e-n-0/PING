package fr.epita.assistants.myide.ricains.entity.features.project;

import javax.validation.constraints.NotNull;

import org.assertj.core.util.Files;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Any;
import fr.epita.assistants.myide.ricains.entity.features.RicainsExecutionReport;
import fr.epita.assistants.utils.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CleanupFeature implements Feature {

    private static void deleteNode(Node node) {
        File file = node.getPath().toFile();
        if (file.exists())
            file.delete();
    }

    private static void removeRecusiveFiles(final Node node, final List<String> filenameToDelete) {
        final String fileName = node.getPath().getFileName().toString();
        if (filenameToDelete.contains(fileName))
            deleteNode(node);
        else
            for (Node n : node.getChildren())
                removeRecusiveFiles(n, filenameToDelete);
    }

    @Override
    public @NotNull ExecutionReport execute(Project project, Object... params) {
        List<String> filenameToDelete = new ArrayList<>();
        Path ignoreFilePath = Path.of(project.getRootNode().getPath().toString(), ".myideignore");

        // Get all filename of the .myideignore to delete
        try (var br = new BufferedReader(new FileReader(ignoreFilePath.toFile()))) {
            String filename;

            while ((filename = br.readLine()) != null) {
                Log.log(filename);
                filenameToDelete.add(filename);
            }
        } catch (IOException e) {
            Log.err(e);
            return RicainsExecutionReport.create(false);
        }

        // Search in all files of the project (root node) and delete files

        return RicainsExecutionReport.create(true);
    }

    @Override
    public @NotNull Type type() {
        return Any.CLEANUP;
    }

}
