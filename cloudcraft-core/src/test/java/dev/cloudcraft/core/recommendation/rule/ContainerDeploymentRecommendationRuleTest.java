package dev.cloudcraft.core.recommendation.rule;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.dsl.ComponentBuilder;
import dev.cloudcraft.core.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ContainerDeploymentRecommendationRuleTest {

    @Test
    void analyze_withVMDeployment_recommendsContainerization() {
        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder()
                .name("LegacyApp")
                .addComponent(ComponentBuilder.builder()
                        .name("LegacyService")
                        .cloudProvider(CloudProvider.AZURE)
                        .deploymentType(DeploymentType.VM)
                        .technologyStack(Framework.JSF, ProgrammingLanguage.JAVA, Database.POSTGRESQL, MessageBroker.NONE)
                        .build())
                .build();

        RecommendationRule recommendationRule = new ContainerDeploymentRecommendationRule();
        List<EvaluationResult.Recommendation> recommendations = recommendationRule.evaluate(blueprint);

        assertThat(recommendations).hasSize(1);
        EvaluationResult.Recommendation rec = recommendations.get(0);
        assertThat(rec.componentName()).isEqualTo("LegacyService");
        assertThat(rec.message()).contains("Consider moving from VM to container-based deployment");
        assertThat(rec.type()).isEqualTo(EvaluationResult.Recommendation.RecommendationType.MODERNIZATION);
    }

}