package dev.cloudcraft.core.recommendation;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.EvaluationResult;
import dev.cloudcraft.core.recommendation.rule.RecommendationRule;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultRecommendationEngine implements RecommendationEngine {

    private final List<RecommendationRule> rules;

    public DefaultRecommendationEngine(final List<RecommendationRule> rules) {
        this.rules = rules;
    }

    @Override
    public List<EvaluationResult.Recommendation> recommend(ArchitectureBlueprint blueprint) {
        return rules.stream()
                .flatMap(rule -> rule.evaluate(blueprint).stream())
                .collect(Collectors.toList());
    }
}
