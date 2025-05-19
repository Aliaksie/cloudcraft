package dev.cloudcraft.core.recommendation;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.EvaluationResult;
import dev.cloudcraft.core.recommendation.rule.ContainerDeploymentRecommendationRule;

import java.util.List;

public interface RecommendationEngine {

    List<EvaluationResult.Recommendation> recommend(ArchitectureBlueprint blueprint);

    static DefaultRecommendationEngine defaultAdvisor() {
        return new DefaultRecommendationEngine(List.of(
                new ContainerDeploymentRecommendationRule()
        ));
    }
}
