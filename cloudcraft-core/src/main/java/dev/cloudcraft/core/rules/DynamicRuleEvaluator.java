package dev.cloudcraft.core.rules;

import dev.cloudcraft.core.model.RuleDefinition;

import java.util.List;

public interface DynamicRuleEvaluator<T, C> {
    RuleDefinition.RuleType getType();

    RuleDefinition.RuleLevel getLevel();

    List<T> evaluate(C context);
}
