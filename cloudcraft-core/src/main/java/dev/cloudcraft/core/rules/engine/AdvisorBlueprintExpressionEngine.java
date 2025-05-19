package dev.cloudcraft.core.rules.engine;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.EvaluationResult;
import dev.cloudcraft.core.model.RuleDefinition;

import java.util.Optional;

public class AdvisorBlueprintExpressionEngine implements ExpressionEngine<RuleDefinition.AdvisorRuleDefinition, ArchitectureBlueprint, EvaluationResult> {
    @Override
    public Optional<EvaluationResult> evaluate(final RuleDefinition.AdvisorRuleDefinition rule, final ArchitectureBlueprint context) {
        // todo:!
        boolean conditionMet = true; /* evaluate rule.condition() */
        String componentName = context.name();
        return conditionMet ? Optional.of(new EvaluationResult.ValidationResult(rule.id(), rule.name(), componentName, rule.description(), rule.severity()))
                : Optional.empty();
    }
}
