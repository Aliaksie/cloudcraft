package dev.cloudcraft.core.visualizer;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.DiagramStyle;

public interface DiagramGenerator {

    String generate(ArchitectureBlueprint blueprint, DiagramStyle style);

    String generate(ArchitectureBlueprint blueprint);

    static DiagramGenerator defaultGenerator() {
        return new CompositeDiagramGenerator(new ComponentDiagramGenerator(), new DeploymentDiagramGenerator());
    }

}
