package fr.epita.assistants.myide.ricains.entity.aspects;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory.Aspects;
import fr.epita.assistants.myide.ricains.entity.features.project.CleanupFeature;
import fr.epita.assistants.myide.ricains.entity.features.project.DistFeature;
import fr.epita.assistants.myide.ricains.entity.features.project.SearchFeature;

public class AnyAspect implements Aspect {

    @Override
    public Type getType() {
        return Aspects.ANY;
    }

    @Override
    public @NotNull List<Feature> getFeatureList() {
        List<Feature> list = new ArrayList<>();
        list.add(new CleanupFeature());
        list.add(new DistFeature());
        list.add(new SearchFeature());
        return list;
    }

}
