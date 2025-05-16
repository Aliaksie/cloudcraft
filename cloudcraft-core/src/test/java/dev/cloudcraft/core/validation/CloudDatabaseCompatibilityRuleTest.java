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
        List<String> issues = rule.validate(component);

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
        List<String> issues = rule.validate(component);

        assertEquals(1, issues.size());
        assertTrue(issues.get(0).contains("not supported on cloud provider"));
    }
}