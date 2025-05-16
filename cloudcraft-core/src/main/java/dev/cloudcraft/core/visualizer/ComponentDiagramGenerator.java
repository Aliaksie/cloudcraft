package dev.cloudcraft.core.visualizer;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.Dependency;

public class ComponentDiagramGenerator implements PlantUmlDiagramGenerator {


    @Override
    public String generate(final ArchitectureBlueprint blueprint) {
        final StringBuilder uml = new StringBuilder();
        uml.append("@startuml\n");
        uml.append("title: ").append(blueprint.name()).append("\n\n");


        for (final Component component : blueprint.components()) {
            String alias = normalize(component.name());
            String stackInfo = extractStackInfo(component.technologyStack());
            uml.append(String.format("component \"%s\\n%s\" as %s\n", component.name(), stackInfo, alias));
        }

        uml.append("\n");

        // Add dependencies with labels
        for (final Dependency dep : blueprint.dependencies()) {
            String from = normalize(dep.from());
            String to = normalize(dep.to());
            String label = dep.type() != null ? dep.type().name() : "depends on";

            uml.append(from).append(" ")
                    .append(arrowFor(dep.type())).append(" ")
                    .append(to).append(" : ")
                    .append(label).append("\n");
        }

        uml.append("@enduml\n");

        return toSVG(uml.toString());
    }

}
