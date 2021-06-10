package fr.epita.assistants.myide.ricains.entity.features;

import java.io.File;

import javax.validation.constraints.NotNull;

import org.eclipse.jgit.api.Git;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;

public class FeatureGit implements Feature {

    public @NotNull ExecutionReport pull(Git git, Object... params) throws Exception {

        var pullRequest = git.pull();
        var pullResult = pullRequest.call();
        return RicainsExecutionReport.create(pullResult.isSuccessful());
    }

    @Override
    public @NotNull ExecutionReport execute(Project project, Object... params) {

        File gitFile = project.getRootNode().getPath().toFile();
        Git git = null;
        try {
            git = Git.init().setDirectory(gitFile).call();
        } catch (Exception e) {
            return RicainsExecutionReport.create(false);
        }

        return RicainsExecutionReport.create(true);
    }

    @Override
    public @NotNull Type type() {
        // TODO Auto-generated method stub
        return null;
    }

}
