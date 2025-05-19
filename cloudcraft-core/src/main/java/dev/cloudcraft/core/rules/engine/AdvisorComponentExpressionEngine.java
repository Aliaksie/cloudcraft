package dev.cloudcraft.core.rules.engine;

import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.EvaluationResult;
import dev.cloudcraft.core.model.RuleDefinition;

import java.util.Optional;

public class AdvisorComponentExpressionEngine implements ExpressionEngine<RuleDefinition.AdvisorRuleDefinition, Component, EvaluationResult> {
    @Override
    public Optional<EvaluationResult> evaluate(final RuleDefinition.AdvisorRuleDefinition rule, final Component context) {
        // todo:!
        boolean conditionMet = true; /* evaluate rule.condition() */
        String componentName = context.name();
        return conditionMet ? Optional.of(new EvaluationResult.ValidationResult(rule.id(), rule.name(), componentName, rule.description(), rule.severity()))
                : Optional.empty();
    }
}
