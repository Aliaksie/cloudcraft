package dev.cloudcraft.core.rules.engine;

import dev.cloudcraft.core.model.EvaluationResult;
import dev.cloudcraft.core.model.RuleDefinition;

import java.util.Optional;

public interface ExpressionEngine<R extends RuleDefinition, C, T extends EvaluationResult> {
    Optional<T> evaluate(R rule, C context);
}
