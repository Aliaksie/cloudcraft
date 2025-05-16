package dev.cloudcraft.core.visualizer;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.dsl.ComponentBuilder;
import dev.cloudcraft.core.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompositeDiagramGeneratorTest {
    private final DiagramGenerator generator = new CompositeDiagramGenerator(new ComponentDiagramGenerator(),
            new DeploymentDiagramGenerator());

    @Test
    void generator_should_throw_unsupportedOperationException() {
        // Given
        Component service = ComponentBuilder.builder()
                .name("UserService")
                .cloudProvider(CloudProvider.AWS)
                .deploymentType(DeploymentType.CONTAINER)
                .technologyStack(
                        Framework.SPRING_BOOT,
                        ProgrammingLanguage.JAVA,
                        Database.POSTGRESQL,
                        MessageBroker.KAFKA)
                .build();

        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder()
                .name("User Management System")
                .addComponent(service)
                .build();

        assertThrows(UnsupportedOperationException.class, () -> generator.generate(blueprint, DiagramStyle.C4_CONTAINER));
    }

    @Test
    void generator_should_return() {
        // Given
        Component service = ComponentBuilder.builder()
                .name("UserService")
                .cloudProvider(CloudProvider.AWS)
                .deploymentType(DeploymentType.CONTAINER)
                .technologyStack(
                        Framework.SPRING_BOOT,
                        ProgrammingLanguage.JAVA,
                        Database.POSTGRESQL,
                        MessageBroker.KAFKA)
                .build();

        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder()
                .name("User Management System")
                .addComponent(service)
                .build();

        String svg = generator.generate(blueprint, DiagramStyle.COMPONENT);

        assertNotNull(svg);
        assertTrue(svg.contains("svg"), "Output should contain SVG markup");
    }

}