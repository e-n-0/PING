package fr.epita.assistants.myide.ricains.entity.features.git;

import java.io.File;

import javax.validation.constraints.NotNull;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.util.FS;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features;
import fr.epita.assistants.myide.ricains.entity.features.RicainsExecutionReport;

public class PushFeature implements Feature {

    @Override
    public @NotNull ExecutionReport execute(Project project, Object... params) {
        // Check if a git repo is existing in the project folder
        File gitFile = project.getRootNode().getPath().toFile();
        if (!RepositoryCache.FileKey.isGitRepository(gitFile, FS.DETECTED))
            return RicainsExecutionReport.create(false);

        Git git = null;
        try {
            git = Git.init().setDirectory(gitFile).call();
        } catch (Exception e) {
            return RicainsExecutionReport.create(false);
        }

        // Do the push command
        PushCommand pushCommand = git.push();
        Iterable<PushResult> pushResult = null;
        try {
            pushResult = pushCommand.call();
        } catch (Exception e) {
            return RicainsExecutionReport.create(false);
        }

        return RicainsExecutionReport.create(pushResult != null);
    }

    @Override
    public @NotNull Type type() {
        return Features.Git.PUSH;
    }

}
