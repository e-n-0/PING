package fr.epita.assistants.myide.ricains.entity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;

public class RicainsProject implements Project {

    private final Set<Aspect> aspects;
    private final Node rootNode;

    public RicainsProject(Node rootNode) {
        this.aspects = new HashSet<>();
        this.rootNode = rootNode;
    }

    @Override
    public @NotNull Node getRootNode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public @NotNull Set<Aspect> getAspects() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public @NotNull Optional<Feature> getFeature(@NotNull Feature.Type featureType) {
        // TODO Auto-generated method stub
        return null;
    }

}
