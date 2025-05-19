package dev.cloudcraft.core.validation.rule;

import dev.cloudcraft.core.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CloudMessageBrokerCompatibilityRuleTest {
    @Test
    void validate_withSupportedBroker_shouldReturnNoIssues() {
        Component component = new Component(
                "3",
                "BrokerValidComponent",
                new TechnologyStack(ProgrammingLanguage.JAVA, Framework.SPRING_BOOT, Database.POSTGRESQL, MessageBroker.KAFKA),
                DeploymentType.CONTAINER,
                CloudProvider.AWS,
                Environment.DEV
        );

        CloudMessageBrokerCompatibilityRule rule = new CloudMessageBrokerCompatibilityRule();
        List<EvaluationResult.ValidationResult> issues = rule.validate(component);

        assertTrue(issues.isEmpty());
    }

    @Test
    void validate_withUnsupportedBroker_shouldReturnIssue() {
        Component component = new Component(
                "4",
                "BrokerInvalidComponent",
                new TechnologyStack(ProgrammingLanguage.JAVA, Framework.SPRING_BOOT, Database.POSTGRESQL, MessageBroker.AZ_SERVICE_BUS),
                DeploymentType.CONTAINER,
                CloudProvider.GCP,
                Environment.DEV
        );

        CloudMessageBrokerCompatibilityRule rule = new CloudMessageBrokerCompatibilityRule();
        List<EvaluationResult.ValidationResult> issues = rule.validate(component);

        assertEquals(1, issues.size());
        assertTrue(issues.get(0).message().contains("not supported on cloud provider"));
    }
}