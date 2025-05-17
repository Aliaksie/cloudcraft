package dev.cloudcraft.core.validation.rule;

import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.ValidationResult;

import java.util.List;

public interface ComponentValidationRule {
    List<ValidationResult> validate(Component component);

    default String name() {
        return this.getClass().getSimpleName();
    }
}
