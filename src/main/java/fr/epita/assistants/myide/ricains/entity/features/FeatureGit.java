package fr.epita.assistants.myide.ricains.entity.features;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;

public class FeatureGit implements Feature {

    @Override
    public @NotNull ExecutionReport execute(Project project, Object... params) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public @NotNull Type type() {
        // TODO Auto-generated method stub
        return null;
    }

}
