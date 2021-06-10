package fr.epita.assistants.myide.ricains.entity.features.git;

import java.io.File;

import javax.validation.constraints.NotNull;

import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.FS;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features;
import fr.epita.assistants.myide.ricains.entity.features.RicainsExecutionReport;

public class CommitFeature implements Feature {

    @Override
    public @NotNull ExecutionReport execute(Project project, Object... params) {
        // We will only use one param
        if (params.length < 1)
            return RicainsExecutionReport.create(false);

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

        // Do the commit command
        String commitMessage = (String) params[0];
        CommitCommand commitCommand = git.commit().setMessage(commitMessage);
        RevCommit revCommit = null;
        try {
            revCommit = commitCommand.call();
        } catch (Exception e) {
            return RicainsExecutionReport.create(false);
        }

        return RicainsExecutionReport.create(revCommit != null);
    }

    @Override
    public @NotNull Type type() {
        return Features.Git.COMMIT;
    }

}
