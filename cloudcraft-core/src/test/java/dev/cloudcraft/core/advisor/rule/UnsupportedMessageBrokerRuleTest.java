package dev.cloudcraft.core.advisor.rule;


import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.*;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UnsupportedMessageBrokerRuleTest {
    private final UnsupportedMessageBrokerRule rule = new UnsupportedMessageBrokerRule();

    private ArchitectureBlueprint createBlueprint(String name, CloudProvider provider, MessageBroker broker) {
        final Component component = new Component(
                UUID.randomUUID().toString(),
                name,
                new TechnologyStack(ProgrammingLanguage.JAVA, Framework.SPRING_BOOT, Database.POSTGRESQL, broker),
                DeploymentType.CONTAINER,
                provider,
                Environment.PROD
        );
        return ArchitectureBlueprint.builder().addComponent(component).build();
    }

    @Test
    void testValidBrokerForAWS() {
        ArchitectureBlueprint component = createBlueprint("ServiceA", CloudProvider.AWS, MessageBroker.KAFKA);
        List<EvaluationResult.ValidationResult> result = rule.analyze(component);
        assertThat(result).isEmpty();
    }

    @Test
    void testInvalidBrokerForAzure() {
        ArchitectureBlueprint component = createBlueprint("ServiceB", CloudProvider.AZURE, MessageBroker.KAFKA);
        List<EvaluationResult.ValidationResult> result = rule.analyze(component);
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).severity()).isEqualTo(RuleDefinition.Severity.MEDIUM);
        assertThat(result.get(0).message()).contains("not commonly supported");
    }

    @Test
    void testValidBrokerForGCP() {
        ArchitectureBlueprint component = createBlueprint("ServiceC", CloudProvider.GCP, MessageBroker.PUBSUB);
        List<EvaluationResult.ValidationResult> result = rule.analyze(component);
        assertThat(result).isEmpty();
    }

    @Test
    void testNoValidationIfCloudProviderIsNone() {
        ArchitectureBlueprint component = createBlueprint("ServiceD", CloudProvider.NONE, MessageBroker.KAFKA);
        List<EvaluationResult.ValidationResult> result = rule.analyze(component);
        assertThat(result).isEmpty();
    }

    @Test
    void testNoValidationIfBrokerIsNone() {
        ArchitectureBlueprint component = createBlueprint("ServiceE", CloudProvider.AWS, MessageBroker.NONE);
        List<EvaluationResult.ValidationResult> result = rule.analyze(component);
        assertThat(result).isEmpty();
    }
}