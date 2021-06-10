package fr.epita.assistants.myide.ricains.entity.features.git;

import java.io.File;

import javax.validation.constraints.NotNull;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.dircache.DirCache;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features;
import fr.epita.assistants.myide.ricains.entity.features.RicainsExecutionReport;
import fr.epita.assistants.utils.Log;

public class AddFeature implements Feature {

    @Override
    public @NotNull ExecutionReport execute(Project project, Object... params) {

        // Check if a git repo is existing in the project folder
        File gitFile = project.getRootNode().getPath().toFile();
        Git git = null;
        try {
            git = Git.init().setDirectory(gitFile).call();
        } catch (Exception e) {
            Log.err(e);
            return RicainsExecutionReport.create(false);
        }

        // Do the add command
        AddCommand addCommand = git.add();

        // Add all files in params
        for (Object filepattern : params)
            addCommand.addFilepattern((String) filepattern);

        DirCache dirCache = null;
        try {
            dirCache = addCommand.call();
        } catch (Exception e) {
            Log.err(e);
            return RicainsExecutionReport.create(false);
        }

        return RicainsExecutionReport.create(dirCache != null);
    }

    @Override
    public @NotNull Type type() {
        return Features.Git.ADD;
    }

}
