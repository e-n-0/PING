package fr.epita.assistants.myide.ricains.service;

import java.nio.file.Path;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.MyIde.Configuration;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;
import fr.epita.assistants.myide.ricains.entity.RicainsNode;
import fr.epita.assistants.myide.ricains.entity.RicainsProject;
import fr.epita.assistants.myide.ricains.entity.features.RicainsExecutionReport;

public class RicainsProjectService implements ProjectService {

    private final Configuration configuration;
    private final RicainsNodeService nodeService;

    public RicainsProjectService(final Configuration configuration) {
        this.configuration = configuration;
        this.nodeService = new RicainsNodeService();
    }

    @Override
    public @NotNull Project load(@NotNull Path root) {
        Node.Types type = Node.Types.FILE;
        if (root.toFile().isDirectory()) {
            type = Node.Types.FOLDER;
        }
        Node rootNode = new RicainsNode(root.toString(), type);
        return new RicainsProject(rootNode);
    }

    @Override
    public @NotNull Feature.ExecutionReport execute(@NotNull Project project, @NotNull Feature.Type featureType,
            Object... params) {

        var optionalFeature = project.getFeature(featureType);
        if (optionalFeature.isPresent())
            return optionalFeature.get().execute(project, params);

        return RicainsExecutionReport.create(false);
    }

    @Override
    public @NotNull NodeService getNodeService() {
        return this.nodeService;
    }

}
