package fr.epita.assistants.myide.ricains.entity.aspects;

import java.util.List;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory.Aspects;
import fr.epita.assistants.myide.ricains.entity.features.maven.CleanFeature;
import fr.epita.assistants.myide.ricains.entity.features.project.DistFeature;
import fr.epita.assistants.myide.ricains.entity.features.project.SearchFeature;

public class AnyAspect implements Aspect {

    @Override
    public Type getType() {
        return Aspects.ANY;
    }

    @Override
    public @NotNull List<Feature> getFeatureList() {
        var list = Aspect.super.getFeatureList();
        list.add(new CleanFeature());
        list.add(new DistFeature());
        list.add(new SearchFeature());
        return list;
    }

}
