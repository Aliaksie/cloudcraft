package dev.cloudcraft.core.recommendation;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultRecommendationEngineTest {
    private final RecommendationEngine engine = RecommendationEngine.defaultAdvisor();

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
        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder().addComponent(vmComponent).build();

        List<Recommendation> recommendations = engine.recommend(blueprint);

        assertThat(recommendations).hasSize(1);
        Recommendation rec = recommendations.get(0);
        assertThat(rec.componentName()).isEqualTo("OrderService");
        assertThat(rec.suggestion()).contains("Consider moving from VM to container-based deployment");
        assertThat(rec.type()).isEqualTo(Recommendation.RecommendationType.MODERNIZATION);
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
        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder().addComponent(dbComponent).build();

        List<Recommendation> recommendations = engine.recommend(blueprint);

        assertThat(recommendations).isEmpty();
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

        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder().addComponent(serverlessComponent).build();
        List<Recommendation> recommendations = engine.recommend(blueprint);

        assertThat(recommendations).isEmpty();
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
        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder().addComponent(azureComponent).build();

        List<Recommendation> recommendations = engine.recommend(blueprint);

        assertThat(recommendations).isEmpty();
    }
}