package dev.cloudcraft.core.validation.rule;

import dev.cloudcraft.core.model.*;
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

        List<EvaluationResult.ValidationResult> issues = rule.validate(component);
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

        List<EvaluationResult.ValidationResult> issues = rule.validate(component);
        assertFalse(issues.isEmpty());
        assertEquals(1, issues.size());
        assertTrue(issues.get(0).message().contains("not compatible"));
    }
}