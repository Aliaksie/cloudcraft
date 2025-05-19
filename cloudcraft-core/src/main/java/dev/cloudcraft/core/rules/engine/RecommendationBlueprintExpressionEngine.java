package dev.cloudcraft.core.rules.engine;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.EvaluationResult;
import dev.cloudcraft.core.model.RuleDefinition;

import java.util.Optional;

public class RecommendationBlueprintExpressionEngine implements ExpressionEngine<RuleDefinition.RecommendationRuleDefinition, ArchitectureBlueprint, EvaluationResult.Recommendation> {
    @Override
    public Optional<EvaluationResult.Recommendation> evaluate(final RuleDefinition.RecommendationRuleDefinition rule, final ArchitectureBlueprint context) {
        // todo: !!
        boolean conditionMet = true; /* evaluate rule.condition() */
        final String componentName = context.name();
        final EvaluationResult.Recommendation.RecommendationType recommendationType = EvaluationResult.Recommendation.RecommendationType.SCALABILITY;
        return conditionMet ? Optional.of(new EvaluationResult.Recommendation(rule.id(), rule.name(), componentName, rule.description(), recommendationType, rule.severity()))
                : Optional.empty();
    }
}
