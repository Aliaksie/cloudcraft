package dev.cloudcraft.core.visualizer;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.Dependency;

import java.util.HashMap;
import java.util.Map;

public class DeploymentDiagramGenerator implements PlantUmlDiagramGenerator {

    @Override
    public String generate(final ArchitectureBlueprint blueprint) {
        final Map<String, String> componentAliases = new HashMap<>();
        final StringBuilder uml = new StringBuilder();
        int id = 1;

        uml.append("@startuml\n");
        uml.append("title ").append(blueprint.name()).append(" - Deployment Diagram\n\n");

        // Group by cloud provider or deployment type
        for (final Component component : blueprint.components()) {
            String alias = "C" + id++;
            componentAliases.put(component.name(), alias);

            String nodeLabel = component.name() + "\\n" +
                    component.deploymentType() + "\\n" +
                    component.cloudProvider();
            uml.append("node \"").append(nodeLabel).append("\" as ").append(alias).append("\n");
        }

        uml.append("\n");

        for (final Dependency dep : blueprint.dependencies()) {
            String fromAlias = componentAliases.get(dep.from());
            String toAlias = componentAliases.get(dep.to());
            String arrow = arrowFor(dep.type());
            uml.append(fromAlias).append(" ").append(arrow).append(" ").append(toAlias).append("\n");
        }

        uml.append("@enduml\n");

        return toSVG(uml.toString());
    }

}
