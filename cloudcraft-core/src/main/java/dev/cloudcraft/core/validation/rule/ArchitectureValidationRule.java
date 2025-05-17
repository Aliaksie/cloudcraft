package dev.cloudcraft.core.validation.rule;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.ValidationResult;

import java.util.List;

public interface ArchitectureValidationRule {
    List<ValidationResult> validate(ArchitectureBlueprint blueprint);

    default String name() {
        return this.getClass().getSimpleName();
    }
}
