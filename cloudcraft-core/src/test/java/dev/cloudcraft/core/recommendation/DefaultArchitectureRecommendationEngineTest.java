package dev.cloudcraft.core.recommendation;

import dev.cloudcraft.core.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultArchitectureRecommendationEngineTest {
    private final ArchitectureRecommendationEngine engine = new DefaultArchitectureRecommendationEngine();

    @Test
    void shouldRecommendContainerizationForVM() {
        Component vmComponent = new Component(
                "1",
                "OrderService",
                new TechnologyStack(ProgrammingLanguage.JAVA, Framework.SPRING_BOOT, Database.POSTGRESQL, MessageBroker.KAFKA),
                DeploymentType.VM,
                CloudProvider.AWS,
                Environment.PROD

        );

        List<String> recommendations = engine.recommend(List.of(vmComponent));

        assertTrue(recommendations.stream().anyMatch(r -> r.contains("containers instead of VMs")));
    }

    @Test
    void shouldRecommendManagedDatabase() {
        Component dbComponent = new Component(
                "1",
                "InventoryService",
                new TechnologyStack(ProgrammingLanguage.JAVA, Framework.QUARKUS, Database.MYSQL, MessageBroker.NONE),
                DeploymentType.CONTAINER,
                CloudProvider.GCP,
                Environment.PROD
        );

        List<String> recommendations = engine.recommend(List.of(dbComponent));

        assertTrue(recommendations.stream().anyMatch(r -> r.contains("managed database services")));
    }

    @Test
    void shouldRecommendQuarkusForServerlessJava() {
        Component serverlessComponent = new Component(
                "1",
                "AuthService",
                new TechnologyStack(ProgrammingLanguage.JAVA, Framework.SPRING_BOOT, Database.NONE, MessageBroker.NONE),
                DeploymentType.SERVERLESS,
                CloudProvider.AWS,
                Environment.PROD
        );

        List<String> recommendations = engine.recommend(List.of(serverlessComponent));

        assertTrue(recommendations.stream().anyMatch(r -> r.contains("Quarkus instead of Spring Boot")));
    }

    @Test
    void shouldRecommendAzureServiceBusOverRabbitMQ() {
        Component azureComponent = new Component(
                "1",
                "BillingService",
                new TechnologyStack(ProgrammingLanguage.JAVA, Framework.SPRING_BOOT, Database.NONE, MessageBroker.RABBITMQ),
                DeploymentType.CONTAINER,
                CloudProvider.AZURE,
                Environment.PROD
        );

        List<String> recommendations = engine.recommend(List.of(azureComponent));

        assertTrue(recommendations.stream().anyMatch(r -> r.contains("Azure Service Bus")));
    }
}