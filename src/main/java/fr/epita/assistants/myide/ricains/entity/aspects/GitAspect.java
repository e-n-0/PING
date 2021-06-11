package fr.epita.assistants.myide.ricains.entity.aspects;

import java.util.ArrayList;
import java.util.List;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Mandatory.Aspects;
import fr.epita.assistants.myide.ricains.entity.features.git.AddFeature;
import fr.epita.assistants.myide.ricains.entity.features.git.CommitFeature;
import fr.epita.assistants.myide.ricains.entity.features.git.PullFeature;
import fr.epita.assistants.myide.ricains.entity.features.git.PushFeature;
import fr.epita.assistants.myide.domain.entity.Feature;

public class GitAspect implements Aspect {

    @Override
    public Type getType() {
        return Aspects.GIT;
    }

    @Override
    public List<Feature> getFeatureList() {
        List<Feature> list = new ArrayList<>();
        list.add(new AddFeature());
        list.add(new CommitFeature());
        list.add(new PullFeature());
        list.add(new PushFeature());
        return list;
    }

}
