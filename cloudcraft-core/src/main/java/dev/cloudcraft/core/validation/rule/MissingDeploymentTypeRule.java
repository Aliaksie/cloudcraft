package dev.cloudcraft.core.validation.rule;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.ValidationResult;

import java.util.List;
import java.util.stream.Collectors;

public class MissingDeploymentTypeRule implements ArchitectureValidationRule {

    @Override
    public List<ValidationResult> validate(final ArchitectureBlueprint blueprint) {
        final List<Component> invalidComponents = blueprint.components().stream()
                .filter(c -> c.deploymentType() == null)
                .toList();

        if (invalidComponents.isEmpty()) {
            return List.of();
        }

        final String message = "Missing deployment type for components: " +
                invalidComponents.stream()
                        .map(Component::name)
                        .collect(Collectors.joining(", "));
        return List.of(new ValidationResult(name(), blueprint.name(), message, ValidationResult.Severity.WARNING));
    }
}
