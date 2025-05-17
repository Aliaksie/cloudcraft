package dev.cloudcraft.core.recommendation.rule;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.Recommendation;

import java.util.List;

public interface RecommendationRule {

    List<Recommendation> evaluate(ArchitectureBlueprint blueprint);

    default String name() {
        return this.getClass().getSimpleName();
    }

}
