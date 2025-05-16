package dev.cloudcraft.core.validation;

import dev.cloudcraft.core.model.Component;

import java.util.ArrayList;
import java.util.List;

public class CompositeComponentValidator {
    private final List<ComponentValidationRule> rules;

    public CompositeComponentValidator(List<ComponentValidationRule> rules) {
        this.rules = rules;
    }

    public List<String> validate(final Component component) {
        final List<String> allIssues = new ArrayList<>();
        for (final ComponentValidationRule rule : rules) {
            final List<String> issues = rule.validate(component);
            allIssues.addAll(issues);
        }
        return allIssues;
    }

    public static CompositeComponentValidator defaultValidator() {
        return new CompositeComponentValidator(List.of(
                new FrameworkCompatibilityRule(),
                new CloudDatabaseCompatibilityRule(),
                new CloudDatabaseCompatibilityRule()
                // todo: DeploymentTypeCompatibilityRule, FrameworkVersionRule, SecurityComplianceRule,
                //  ResourceLimitsRule, ServiceDependencyRule, CostOptimizationRule, etc...
        ));
    }
}
