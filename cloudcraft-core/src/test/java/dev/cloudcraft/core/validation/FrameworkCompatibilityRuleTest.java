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

class FrameworkCompatibilityRuleTest {
    private final FrameworkCompatibilityRule rule = new FrameworkCompatibilityRule();

    @Test
    void validCombination_shouldReturnNoIssues() {
        Component component = new Component(
                "1",
                "TestService",
                new TechnologyStack(ProgrammingLanguage.JAVA, Framework.SPRING_BOOT, Database.POSTGRESQL, MessageBroker.KAFKA),
                DeploymentType.CONTAINER,
                CloudProvider.AWS,
                Environment.DEV
        );

        List<String> issues = rule.validate(component);
        assertTrue(issues.isEmpty());
    }

    @Test
    void invalidCombination_shouldReturnIssue() {
        Component component = new Component(
                "2",
                "BadService",
                new TechnologyStack(ProgrammingLanguage.PYTHON, Framework.SPRING_BOOT, Database.POSTGRESQL, MessageBroker.KAFKA),
                DeploymentType.CONTAINER,
                CloudProvider.AWS,
                Environment.TEST
        );

        List<String> issues = rule.validate(component);
        assertFalse(issues.isEmpty());
        assertEquals(1, issues.size());
        assertTrue(issues.get(0).contains("not compatible"));
    }
}