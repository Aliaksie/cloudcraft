package dev.cloudcraft.core.advisor.rule;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.EvaluationResult;

import java.util.List;

public interface CloudCompatibilityRule {
    List<EvaluationResult.ValidationResult> analyze(ArchitectureBlueprint blueprint);

    default String name() {
        return this.getClass().getSimpleName();
    }
}
