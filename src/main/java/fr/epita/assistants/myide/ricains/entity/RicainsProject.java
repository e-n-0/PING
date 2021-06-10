package fr.epita.assistants.myide.ricains.entity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Any;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Git;
import fr.epita.assistants.myide.domain.entity.Mandatory.Features.Maven;
import fr.epita.assistants.myide.ricains.entity.features.git.AddFeature;
import fr.epita.assistants.myide.ricains.entity.features.git.CommitFeature;
import fr.epita.assistants.myide.ricains.entity.features.git.PullFeature;
import fr.epita.assistants.myide.ricains.entity.features.git.PushFeature;
import fr.epita.assistants.myide.ricains.entity.features.maven.CleanFeature;
import fr.epita.assistants.myide.ricains.entity.features.maven.CompileFeature;
import fr.epita.assistants.myide.ricains.entity.features.maven.ExecFeature;
import fr.epita.assistants.myide.ricains.entity.features.maven.InstallFeature;
import fr.epita.assistants.myide.ricains.entity.features.maven.PackageFeature;
import fr.epita.assistants.myide.ricains.entity.features.maven.TestFeature;
import fr.epita.assistants.myide.ricains.entity.features.maven.TreeFeature;
import fr.epita.assistants.myide.ricains.entity.features.project.CleanupFeature;
import fr.epita.assistants.myide.ricains.entity.features.project.DistFeature;
import fr.epita.assistants.myide.ricains.entity.features.project.SearchFeature;

public class RicainsProject implements Project {

    private final Set<Aspect> aspects;
    private final Node rootNode;

    public RicainsProject(Node rootNode) {
        this.aspects = new HashSet<>();
        this.rootNode = rootNode;
    }

    @Override
    public @NotNull Node getRootNode() {
        return this.rootNode;
    }

    @Override
    public @NotNull Set<Aspect> getAspects() {
        return this.aspects;
    }

    @Override
    public @NotNull Optional<Feature> getFeature(@NotNull Feature.Type featureType) {

        // Any - Project feature
        if (featureType.equals(Any.CLEANUP))
            return Optional.of(new CleanupFeature());
        else if (featureType.equals(Any.DIST))
            return Optional.of(new DistFeature());
        else if (featureType.equals(Any.SEARCH))
            return Optional.of(new SearchFeature());

        // Git Feature
        else if (featureType.equals(Git.PULL))
            return Optional.of(new PullFeature());
        else if (featureType.equals(Git.ADD))
            return Optional.of(new AddFeature());
        else if (featureType.equals(Git.COMMIT))
            return Optional.of(new CommitFeature());
        else if (featureType.equals(Git.PUSH))
            return Optional.of(new PushFeature());

        // Maven Feature
        else if (featureType.equals(Maven.COMPILE))
            return Optional.of(new CompileFeature());
        else if (featureType.equals(Maven.CLEAN))
            return Optional.of(new CleanFeature());
        else if (featureType.equals(Maven.TEST))
            return Optional.of(new TestFeature());
        else if (featureType.equals(Maven.PACKAGE))
            return Optional.of(new PackageFeature());
        else if (featureType.equals(Maven.INSTALL))
            return Optional.of(new InstallFeature());
        else if (featureType.equals(Maven.EXEC))
            return Optional.of(new ExecFeature());
        else if (featureType.equals(Maven.TREE))
            return Optional.of(new TreeFeature());

        // Default - Unknown feature
        else
            return Optional.empty();
    }

}
