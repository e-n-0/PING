package fr.epita.assistants.myide.ricains.entity.features.git;

import java.io.File;

import javax.validation.constraints.NotNull;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features;
import fr.epita.assistants.myide.ricains.entity.features.RicainsExecutionReport;

public class PullFeature implements Feature {

    @Override
    public @NotNull ExecutionReport execute(Project project, Object... params) {

        File gitFile = project.getRootNode().getPath().toFile();
        Git git = null;
        try {
            git = Git.init().setDirectory(gitFile).call();
        } catch (Exception e) {
            return RicainsExecutionReport.create(false);
        }

        var pullRequest = git.pull();
        PullResult pullResult = null;
        try {
            pullResult = pullRequest.call();
        } catch (Exception e) {
            return RicainsExecutionReport.create(false);
        }

        return RicainsExecutionReport.create(pullResult.isSuccessful());
    }

    @Override
    public @NotNull Type type() {
        return Features.Git.PULL;
    }

}
