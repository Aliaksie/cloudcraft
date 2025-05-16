package dev.cloudcraft.core.dsl;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.cloudcraft.core.model.CloudProvider;
import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.Database;
import dev.cloudcraft.core.model.DeploymentType;
import dev.cloudcraft.core.model.Framework;
import dev.cloudcraft.core.model.MessageBroker;
import dev.cloudcraft.core.model.ProgrammingLanguage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArchitectureBlueprintSerializationTest {
    @Test
    void testToJsonSerialization() throws JsonProcessingException {
        Component comp = ComponentBuilder.builder()
                .name("OrderService")
                .cloudProvider(CloudProvider.AWS)
                .deploymentType(DeploymentType.CONTAINER)
                .technologyStack(
                        Framework.SPRING_BOOT,
                        ProgrammingLanguage.JAVA,
                        Database.POSTGRESQL,
                        MessageBroker.KAFKA)
                .build();

        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder()
                .name("OrderProcessingApp")
                .addComponent(comp)
                .build();

        String json = blueprint.toJson();
        assertNotNull(json);
        assertTrue(json.contains("OrderService"));
        assertTrue(json.contains("OrderProcessingApp"));
        System.out.println("JSON Output:\n" + json);
    }

    @Test
    void testToYamlSerialization() throws JsonProcessingException {
        Component comp = ComponentBuilder.builder()
                .name("InventoryService")
                .cloudProvider(CloudProvider.AZURE)
                .deploymentType(DeploymentType.VM)
                .technologyStack(
                        Framework.MICRONAUT,
                        ProgrammingLanguage.KOTLIN,
                        Database.MYSQL,
                        MessageBroker.RABBITMQ)
                .build();

        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder()
                .name("InventoryApp")
                .addComponent(comp)
                .build();

        String yaml = blueprint.toYaml();
        assertNotNull(yaml);
        assertTrue(yaml.contains("InventoryService"));
        assertTrue(yaml.contains("InventoryApp"));
        System.out.println("YAML Output:\n" + yaml);
    }
}
