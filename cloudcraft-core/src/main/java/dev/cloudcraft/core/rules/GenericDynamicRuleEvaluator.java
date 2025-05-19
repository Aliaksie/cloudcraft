package dev.cloudcraft.core.rules;

import dev.cloudcraft.core.model.EvaluationResult;
import dev.cloudcraft.core.model.RuleDefinition;
import dev.cloudcraft.core.rules.engine.ExpressionEngine;

import java.util.List;
import java.util.Optional;

public class GenericDynamicRuleEvaluator<R extends RuleDefinition, C, T extends EvaluationResult> implements DynamicRuleEvaluator<T, C> {
    private final RuleDefinition.RuleLevel level;
    private final RuleDefinition.RuleType type;
    private final List<R> rules;
    private final ExpressionEngine<R, C, T> engine;

    public GenericDynamicRuleEvaluator(final RuleDefinition.RuleLevel level, final RuleDefinition.RuleType type, final List<R> rules, final ExpressionEngine<R, C, T> engine) {
        this.level = level;
        this.type = type;
        this.rules = rules;
        this.engine = engine;
    }


    @Override
    public List<T> evaluate(final C context) {
        return rules.stream()
                .map(rule -> engine.evaluate(rule, context))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }


    @Override
    public RuleDefinition.RuleType getType() {
        return type;
    }

    @Override
    public RuleDefinition.RuleLevel getLevel() {
        return level;
    }
}
