package dev.cloudcraft.core.recommendation.rule;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.Recommendation;

import java.util.List;

public class UseEventDrivenCommunicationRule implements RecommendationRule{
    @Override
    public List<Recommendation> evaluate(ArchitectureBlueprint blueprint) {
        return List.of();
    }
}
