package fr.epita.assistants.myide.ricains.entity.features.project;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Any;
import fr.epita.assistants.myide.ricains.entity.features.RicainsExecutionReport;

public class SearchFeature implements Feature {

    @Override
    public @NotNull ExecutionReport execute(Project project, Object... params) {
        return RicainsExecutionReport.create(true);
    }

    @Override
    public @NotNull Type type() {
        return Any.SEARCH;
    }

}
