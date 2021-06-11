package fr.epita.assistants.myide.ricains.entity.aspects;

import java.util.ArrayList;
import java.util.List;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory.Aspects;
import fr.epita.assistants.myide.ricains.entity.features.maven.CleanFeature;
import fr.epita.assistants.myide.ricains.entity.features.maven.CompileFeature;
import fr.epita.assistants.myide.ricains.entity.features.maven.ExecFeature;
import fr.epita.assistants.myide.ricains.entity.features.maven.InstallFeature;
import fr.epita.assistants.myide.ricains.entity.features.maven.PackageFeature;
import fr.epita.assistants.myide.ricains.entity.features.maven.TestFeature;
import fr.epita.assistants.myide.ricains.entity.features.maven.TreeFeature;

public class MavenAspect implements Aspect {

    @Override
    public Type getType() {
        return Aspects.MAVEN;
    }

    @Override
    public List<Feature> getFeatureList() {
        List<Feature> list = new ArrayList<>();
        list.add(new CleanFeature());
        list.add(new CompileFeature());
        list.add(new ExecFeature());
        list.add(new InstallFeature());
        list.add(new PackageFeature());
        list.add(new TestFeature());
        list.add(new TreeFeature());
        return list;
    }

}
