package dev.cloudcraft.core.validation;

import dev.cloudcraft.core.model.CloudProvider;
import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.Database;
import dev.cloudcraft.core.model.DeploymentType;
import dev.cloudcraft.core.model.Environment;
import dev.cloudcraft.core.model.Framework;
import dev.cloudcraft.core.model.MessageBroker;
import dev.cloudcraft.core.model.ProgrammingLanguage;
import dev.cloudcraft.core.model.TechnologyStack;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompositeComponentValidatorTest {
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

        CompositeComponentValidator validator = CompositeComponentValidator.defaultValidator();
        List<String> issues = validator.validate(validComponent);

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

        CompositeComponentValidator validator = CompositeComponentValidator.defaultValidator();
        List<String> issues = validator.validate(invalidComponent);

        assertFalse(issues.isEmpty());
        assertTrue(issues.get(0).contains("not compatible"));
    }
}