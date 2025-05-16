package dev.cloudcraft.core.visualizer;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.dsl.ComponentBuilder;
import dev.cloudcraft.core.model.*;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ComponentDiagramGeneratorTest {

    private final DiagramGenerator generator = new ComponentDiagramGenerator();

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

        assertThrows(IllegalArgumentException.class, () -> generator.generate(blueprint, DiagramStyle.COMPONENT));
    }

    @Test
    void testGenerateSVGFromBlueprint() {
        // Arrange
        Component component = new Component(
                UUID.randomUUID().toString(),
                "OrderService",
                new TechnologyStack(ProgrammingLanguage.JAVA, Framework.SPRING_BOOT, Database.POSTGRESQL, MessageBroker.KAFKA),
                DeploymentType.CONTAINER,
                CloudProvider.AWS,
                Environment.PROD
        );

        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder()
                .name("E-Commerce System")
                .addComponent(component)
                .build();

        // Act
        String plantUml = generator.generate(blueprint);

        // Assert
        assertNotNull(plantUml);
        assertTrue(plantUml.contains("svg"));
        assertTrue(plantUml.contains("OrderService"));
        assertTrue(plantUml.contains("SPRING_BOOT"));

        System.out.println("PlanUML Output:\n" + plantUml);
    }

    @Test
    void testGenerateSvg_singleComponent() {
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

        // When
        String svg = generator.generate(blueprint);

        // Then
        assertNotNull(svg);
        assertTrue(svg.contains("svg"), "Output should contain SVG markup");
        assertTrue(svg.contains("UserService"), "Component name should appear");
        assertTrue(svg.contains("SPRING_BOOT"), "Framework should appear");
        assertTrue(svg.contains("JAVA"), "Language should appear");
    }


    @Test
    void testGenerateDiagramWithStack() {
        Component userService = ComponentBuilder.builder()
                .name("UserService")
                .cloudProvider(CloudProvider.AWS)
                .deploymentType(DeploymentType.CONTAINER)
                .technologyStack(Framework.SPRING_BOOT, ProgrammingLanguage.JAVA, Database.POSTGRESQL, MessageBroker.KAFKA)
                .build();

        Component orderService = ComponentBuilder.builder()
                .name("OrderService")
                .cloudProvider(CloudProvider.AZURE)
                .deploymentType(DeploymentType.SERVERLESS)
                .technologyStack(Framework.QUARKUS, ProgrammingLanguage.JAVA, Database.MYSQL, MessageBroker.RABBITMQ)
                .build();

        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder()
                .name("Test Arch")
                .addComponent(userService)
                .addComponent(orderService)
                .addDependency("UserService", "OrderService", DependencyType.SYNCHRONOUS)
                .build();

        String svg = generator.generate(blueprint);

        assertNotNull(svg);
        assertTrue(svg.contains("svg"), "Output should contain SVG markup");
    }
}