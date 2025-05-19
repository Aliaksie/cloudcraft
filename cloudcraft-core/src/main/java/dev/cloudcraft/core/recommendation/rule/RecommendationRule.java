package dev.cloudcraft.core.recommendation.rule;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.EvaluationResult;

import java.util.List;

public interface RecommendationRule {

    List<EvaluationResult.Recommendation> evaluate(ArchitectureBlueprint blueprint);

    default String name() {
        return this.getClass().getSimpleName();
    }

}
