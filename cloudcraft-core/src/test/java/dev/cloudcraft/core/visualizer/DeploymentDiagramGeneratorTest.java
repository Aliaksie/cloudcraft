package dev.cloudcraft.core.visualizer;

import dev.cloudcraft.core.model.*;
import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.dsl.ComponentBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeploymentDiagramGeneratorTest {

    private final DiagramGenerator generator = new DeploymentDiagramGenerator();

    @Test
    void generator_should_throw_illegalArgumentException() {
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

        assertThrows(IllegalArgumentException.class, () -> generator.generate(blueprint, DiagramStyle.DEPLOYMENT));
    }

    @Test
    void generateDeploymentDiagram_basicExample_success() {
        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder()
                .name("MyApp Deployment")
                .addComponent(ComponentBuilder.builder()
                        .name("UserService")
                        .cloudProvider(CloudProvider.AWS)
                        .deploymentType(DeploymentType.CONTAINER)
                        .technologyStack(Framework.SPRING_BOOT, ProgrammingLanguage.JAVA, Database.POSTGRESQL, MessageBroker.KAFKA)
                        .build())
                .addComponent(ComponentBuilder.builder()
                        .name("OrderService")
                        .cloudProvider(CloudProvider.AZURE)
                        .deploymentType(DeploymentType.CONTAINER)
                        .technologyStack(Framework.QUARKUS, ProgrammingLanguage.JAVA, Database.MONGODB, MessageBroker.KAFKA)
                        .build())
                .addDependency("UserService", "OrderService")
                .build();

        String svg = generator.generate(blueprint);

        assertNotNull(svg);
        assertTrue(svg.contains("svg"), "Output should contain SVG markup");
    }
}