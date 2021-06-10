package fr.epita.assistants.myide.ricains.entity.features.maven;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Maven;

public class CleanFeature implements Feature {

    @Override
    public @NotNull ExecutionReport execute(Project project, Object... params) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public @NotNull Type type() {
        return Maven.CLEAN;
    }

}
