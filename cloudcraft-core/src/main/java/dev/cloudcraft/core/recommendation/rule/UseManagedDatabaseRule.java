package dev.cloudcraft.core.recommendation.rule;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.EvaluationResult;

import java.util.List;

public class UseManagedDatabaseRule implements RecommendationRule {
    @Override
    public List<EvaluationResult.Recommendation> evaluate(ArchitectureBlueprint blueprint) {
        return List.of();
    }
}
