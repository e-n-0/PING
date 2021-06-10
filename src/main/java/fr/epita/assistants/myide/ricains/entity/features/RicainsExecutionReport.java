package fr.epita.assistants.myide.ricains.entity.features;

import fr.epita.assistants.myide.domain.entity.Feature;

public class RicainsExecutionReport implements Feature.ExecutionReport {

    private final boolean success;

    public static RicainsExecutionReport create(boolean success) {
        return new RicainsExecutionReport(success);
    }

    private RicainsExecutionReport(final boolean success) {
        this.success = success;
    }

    @Override
    public boolean isSuccess() {
        return this.success;
    }

}
