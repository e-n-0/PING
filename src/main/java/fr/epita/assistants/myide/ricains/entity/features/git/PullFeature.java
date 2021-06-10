package fr.epita.assistants.myide.ricains.entity.features.git;

import java.io.File;

import javax.validation.constraints.NotNull;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.util.FS;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features;
import fr.epita.assistants.myide.ricains.entity.features.RicainsExecutionReport;
import fr.epita.assistants.utils.Log;

public class PullFeature implements Feature {

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

        // Do the pull request
        var pullRequest = git.pull();
        PullResult pullResult = null;
        try {
            pullResult = pullRequest.call();
        } catch (Exception e) {
            Log.err(e);
            return RicainsExecutionReport.create(false);
        }

        return RicainsExecutionReport.create(pullResult.isSuccessful());
    }

    @Override
    public @NotNull Type type() {
        return Features.Git.PULL;
    }

}
