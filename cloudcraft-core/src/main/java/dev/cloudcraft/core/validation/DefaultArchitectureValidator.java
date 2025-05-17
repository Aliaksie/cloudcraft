package dev.cloudcraft.core.validation;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.ValidationResult;
import dev.cloudcraft.core.validation.rule.ArchitectureValidationRule;
import dev.cloudcraft.core.validation.rule.CloudDatabaseCompatibilityRule;
import dev.cloudcraft.core.validation.rule.ComponentValidationRule;
import dev.cloudcraft.core.validation.rule.FrameworkCompatibilityRule;

import java.util.ArrayList;
import java.util.List;

public class DefaultArchitectureValidator implements ArchitectureValidator {
    private final List<ComponentValidationRule> componentRules;
    private final List<ArchitectureValidationRule> blueprintRules;

    public DefaultArchitectureValidator(final List<ComponentValidationRule> componentRules, List<ArchitectureValidationRule> blueprintRules) {
        this.componentRules = componentRules;
        this.blueprintRules = blueprintRules;
    }

    public List<ValidationResult> validate(final ArchitectureBlueprint blueprint) {
        final List<ValidationResult> allIssues = new ArrayList<>();
        for (final Component component : blueprint.components()) {
            for (final ComponentValidationRule rule : componentRules) {
                final List<ValidationResult> issues = rule.validate(component);
                allIssues.addAll(issues);
            }
        }

        for (final ArchitectureValidationRule rule : blueprintRules) {
            final List<ValidationResult> issues = rule.validate(blueprint);
            allIssues.addAll(issues);
        }

        return allIssues;
    }
}
