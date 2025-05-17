package dev.cloudcraft.core.advisor.rule;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.ValidationResult;

import java.util.List;

public interface CloudCompatibilityRule {
    List<ValidationResult> analyze(ArchitectureBlueprint blueprint);

    default String name() {
        return this.getClass().getSimpleName();
    }
}
