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
        List<String> issues = rule.validate(component);

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
        List<String> issues = rule.validate(component);

        assertEquals(1, issues.size());
        assertTrue(issues.get(0).contains("not supported on cloud provider"));
    }
}