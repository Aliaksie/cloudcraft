package dev.cloudcraft.core.validation;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultArchitectureValidatorTest {

    @Test
    void defaultValidator_withValidComponent_shouldReturnNoIssues() {
        Component validComponent = new Component(
                "1",
                "ValidService",
                new TechnologyStack(ProgrammingLanguage.JAVA, Framework.SPRING_BOOT, Database.POSTGRESQL, MessageBroker.KAFKA),
                DeploymentType.CONTAINER,
                CloudProvider.AWS,
                Environment.PROD
        );
        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder().addComponent(validComponent).build();


        DefaultArchitectureValidator validator = ArchitectureValidator.defaultValidator();
        List<ValidationResult> issues = validator.validate(blueprint);

        assertTrue(issues.isEmpty());
    }

    @Test
    void defaultValidator_withInvalidComponent_shouldReturnIssues() {
        Component invalidComponent = new Component(
                "2",
                "InvalidService",
                new TechnologyStack(ProgrammingLanguage.PYTHON, Framework.SPRING_BOOT, Database.MONGODB, MessageBroker.NATS),
                DeploymentType.CONTAINER,
                CloudProvider.AZURE,
                Environment.DEV
        );
        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder().addComponent(invalidComponent).build();

        DefaultArchitectureValidator validator = ArchitectureValidator.defaultValidator();
        List<ValidationResult> issues = validator.validate(blueprint);

        assertFalse(issues.isEmpty());
        assertTrue(issues.get(0).message().contains("not compatible"));
    }
}