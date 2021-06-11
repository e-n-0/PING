package fr.epita.assistants.myide.ricains.entity;

import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.*;
import fr.epita.assistants.myide.domain.entity.Feature.Type;
import fr.epita.assistants.myide.domain.entity.Node.Types;
import fr.epita.assistants.myide.ricains.entity.aspects.AnyAspect;
import fr.epita.assistants.myide.ricains.entity.aspects.GitAspect;
import fr.epita.assistants.myide.ricains.entity.aspects.MavenAspect;

public class RicainsProject implements Project {

    private final Node rootNode;

    public RicainsProject(Node rootNode) {
        this.rootNode = rootNode;
    }

    @Override
    public @NotNull Node getRootNode() {
        return this.rootNode;
    }

    @Override
    public @NotNull Set<Aspect> getAspects() {
        final Set<Aspect> aspects = new HashSet<>();

        aspects.add(new AnyAspect());

        if (this.rootNode.getType().equals(Types.FILE))
            return aspects;

        // Detect pom.xml file (MAVEN project)
        Path parentPath = this.rootNode.getPath().toAbsolutePath().getParent();
        Path pomPath = Path.of(parentPath.toString(), "pom.xml");
        if (pomPath.toFile().exists())
            aspects.add(new MavenAspect());

        // Detect valid git project folder
        File gitFile = this.getRootNode().getPath().toFile();
        try {
            org.eclipse.jgit.api.Git.init().setDirectory(gitFile).call();
            aspects.add(new GitAspect());
        } catch (Exception e) {
            // Not a valid git project
        }

        return aspects;
    }

    @Override
    public @NotNull Optional<Feature> getFeature(@NotNull Feature.Type featureType) {

        var aspects = getAspects();
        for (Aspect aspect : aspects) {
            for (Feature feature : aspect.getFeatureList()) {
                Type type = feature.type();
                if (type.equals(featureType))
                    return Optional.of(feature);
            }
        }

        return Optional.empty();
    }

}
