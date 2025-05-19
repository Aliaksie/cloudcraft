package dev.cloudcraft.core.validation.rule;

import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.EvaluationResult;

import java.util.List;

public interface ComponentValidationRule {
    List<EvaluationResult.ValidationResult> validate(Component component);

    default String name() {
        return this.getClass().getSimpleName();
    }
}
