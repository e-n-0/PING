package fr.epita.assistants.myide.ricains.entity;

import fr.epita.assistants.myide.domain.entity.Aspect;

public class RicainsAspect implements Aspect {

    private final Type type;

    public RicainsAspect(final Type type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return this.type;
    }

}
