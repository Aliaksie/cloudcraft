package dev.cloudcraft.core.validation;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.ValidationResult;
import dev.cloudcraft.core.validation.rule.CloudDatabaseCompatibilityRule;
import dev.cloudcraft.core.validation.rule.FrameworkCompatibilityRule;

import java.util.List;

public interface ArchitectureValidator {

    List<ValidationResult> validate(ArchitectureBlueprint blueprint);

    static DefaultArchitectureValidator defaultValidator() {
        return new DefaultArchitectureValidator(List.of(
                new FrameworkCompatibilityRule(),
                new CloudDatabaseCompatibilityRule(),
                new CloudDatabaseCompatibilityRule()
                // todo: DeploymentTypeCompatibilityRule, FrameworkVersionRule, SecurityComplianceRule,
                //  ResourceLimitsRule, ServiceDependencyRule, CostOptimizationRule, etc...
        ),
                List.of());
    }
}
