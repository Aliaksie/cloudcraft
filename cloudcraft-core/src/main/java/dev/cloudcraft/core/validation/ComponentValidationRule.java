package dev.cloudcraft.core.validation;

import dev.cloudcraft.core.model.Component;

import java.util.List;

public interface ComponentValidationRule {
    List<String> validate(Component component);

    default String name() {
        return this.getClass().getSimpleName();
    }
}
