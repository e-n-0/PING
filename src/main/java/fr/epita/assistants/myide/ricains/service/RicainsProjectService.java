package fr.epita.assistants.myide.ricains.service;

import java.nio.file.Path;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.MyIde.Configuration;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Any;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Git;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;
import fr.epita.assistants.myide.ricains.entity.RicainsProject;
import fr.epita.assistants.myide.ricains.entity.features.FeatureGit;
import fr.epita.assistants.myide.ricains.entity.features.FeatureMaven;
import fr.epita.assistants.myide.ricains.entity.features.FeatureProject;

public class RicainsProjectService implements ProjectService {

    private final Configuration configuration;
    private final RicainsNodeService nodeService;

    public RicainsProjectService(final Configuration configuration) {
        this.configuration = configuration;
        this.nodeService = new RicainsNodeService();
    }

    @Override
    public @NotNull Project load(@NotNull Path root) {
        // Node rootNode = this.nodeService.create(folder, name, type);
        RicainsProject project = new RicainsProject(null);
        return project;
    }

    @Override
    public @NotNull Feature.ExecutionReport execute(@NotNull Project project, @NotNull Feature.Type featureType,
            Object... params) {

        // Any - Project feature
        if (featureType.equals(Any.CLEANUP) || featureType.equals(Any.DIST) || featureType.equals(Any.SEARCH))
            return new FeatureProject().execute(project, params);

        // Git Feature
        else if (featureType.equals(Git.PULL) || featureType.equals(Git.ADD) || featureType.equals(Git.COMMIT)
                || featureType.equals(Git.PUSH))
            return new FeatureGit().execute(project, params);

        // Maven Feature
        else
            return new FeatureMaven().execute(project, params);
    }

    @Override
    public @NotNull NodeService getNodeService() {
        return this.nodeService;
    }

}
