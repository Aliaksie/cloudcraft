package dev.cloudcraft.core.rules.engine;

import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.EvaluationResult;
import dev.cloudcraft.core.model.RuleDefinition;

import java.util.Optional;

public class ValidationComponentExpressionEngine implements ExpressionEngine<RuleDefinition.ValidationRuleDefinition, Component, EvaluationResult.ValidationResult> {
    @Override
    public Optional<EvaluationResult.ValidationResult> evaluate(final RuleDefinition.ValidationRuleDefinition rule, final Component context) {
        // todo: !!
        boolean isValid = false; /* evaluate rule.expression() using SpEL/MVEL/etc */
        final String componentName = context.name();
        return isValid ? Optional.empty() :
                Optional.of(new EvaluationResult.ValidationResult(rule.id(), rule.name(), componentName, rule.description(), rule.severity()));
    }
}