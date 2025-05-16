package dev.cloudcraft.core.visualizer;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.DiagramStyle;

public class CompositeDiagramGenerator implements DiagramGenerator {

    private final DiagramGenerator component;
    private final DiagramGenerator deployment;

    public CompositeDiagramGenerator(final DiagramGenerator component, final DiagramGenerator deployment) {
        this.component = component;
        this.deployment = deployment;
    }

    @Override
    public String generate(final ArchitectureBlueprint blueprint, final DiagramStyle style) {
        return switch (style) {
            case COMPONENT -> component.generate(blueprint);
            case DEPLOYMENT -> deployment.generate(blueprint);
            // Add support for C4, etc. here
            default -> throw new UnsupportedOperationException("Diagram style not yet supported: " + style);
        };
    }

    @Override
    public String generate(final ArchitectureBlueprint blueprint) {
        throw new IllegalArgumentException();
    }
}
