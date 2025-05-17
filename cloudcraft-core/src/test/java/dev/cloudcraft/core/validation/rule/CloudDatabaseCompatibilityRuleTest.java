package dev.cloudcraft.core.validation.rule;


import dev.cloudcraft.core.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CloudDatabaseCompatibilityRuleTest {

    @Test
    void validate_withSupportedDatabase_shouldReturnNoIssues() {
        Component component = new Component(
                "1",
                "DBValidComponent",
                new TechnologyStack(ProgrammingLanguage.JAVA, Framework.SPRING_BOOT, Database.POSTGRESQL, MessageBroker.KAFKA),
                DeploymentType.CONTAINER,
                CloudProvider.AWS,
                Environment.PROD
        );

        CloudDatabaseCompatibilityRule rule = new CloudDatabaseCompatibilityRule();
        List<ValidationResult> issues = rule.validate(component);

        assertTrue(issues.isEmpty());
    }

    @Test
    void validate_withUnsupportedDatabase_shouldReturnIssue() {
        Component component = new Component(
                "2",
                "DBInvalidComponent",
                new TechnologyStack(ProgrammingLanguage.JAVA, Framework.SPRING_BOOT, Database.SQLSERVER, MessageBroker.KAFKA),
                DeploymentType.CONTAINER,
                CloudProvider.GCP,
                Environment.PROD
        );

        CloudDatabaseCompatibilityRule rule = new CloudDatabaseCompatibilityRule();
        List<ValidationResult> issues = rule.validate(component);

        assertEquals(1, issues.size());
        assertTrue(issues.get(0).message().contains("not supported on cloud provider"));
    }
}