package fr.epita.assistants.myide.ricains.entity.features.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Any;
import fr.epita.assistants.myide.domain.entity.Node.Types;
import fr.epita.assistants.myide.ricains.entity.features.RicainsExecutionReport;
import fr.epita.assistants.utils.Log;

public class DistFeature implements Feature {

    public static void addToZip(final String projectName, File directoryToZip, File file, ZipOutputStream zos)
            throws Exception {

        FileInputStream fis = new FileInputStream(file);

        // we want the zipEntry's path to be a relative path that is relative
        // to the directory being zipped, so chop off the rest of the path
        String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,
                file.getCanonicalPath().length());

        ZipEntry zipEntry = new ZipEntry(projectName + "/" + zipFilePath);
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }

    private static void addRecursiveFiles(Node rootNode, Node node, ZipOutputStream zos, final String projectName)
            throws Exception {
        for (Node n : node.getChildren()) {
            if (n.getType().equals(Types.FILE))
                addToZip(projectName, rootNode.getPath().toFile(), n.getPath().toFile(), zos);

            addRecursiveFiles(rootNode, n, zos, projectName);
        }
    }

    @Override
    public @NotNull ExecutionReport execute(Project project, Object... params) {

        // Execute Cleanup first
        var resultCleanup = project.getFeature(Any.CLEANUP).get().execute(project);
        if (!resultCleanup.isSuccess())
            return resultCleanup;

        try {
            final String projectName = project.getRootNode().getPath().toFile().getCanonicalFile().toPath()
                    .getFileName().toString();
            final String upperPath = project.getRootNode().getPath().toFile().getCanonicalFile().toPath().getParent()
                    .toString();
            final String finalPathResult = Path.of(upperPath, projectName).toString();
            Log.log(projectName);
            Log.log(upperPath);
            Log.log(finalPathResult);
            FileOutputStream fos = new FileOutputStream(finalPathResult + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            addRecursiveFiles(project.getRootNode(), project.getRootNode(), zos, projectName);

            zos.close();
            fos.close();
        } catch (Exception e) {
            Log.err(e.getStackTrace());
            System.out.println(e);
            return RicainsExecutionReport.create(false);
        }

        return RicainsExecutionReport.create(true);
    }

    @Override
    public @NotNull Type type() {
        return Any.DIST;
    }

}
