package dev.cloudcraft.core.advisor;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.dsl.ComponentBuilder;
import dev.cloudcraft.core.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultCloudCompatibilityRuleTest {
    @Test
    void analyze_withUnsupportedDatabaseOnCloud_returnsWarning() {
        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder()
                .name("Test Cloud Compatibility")
                .addComponent(ComponentBuilder.builder()
                        .name("LegacyService")
                        .cloudProvider(CloudProvider.AWS)
                        .deploymentType(DeploymentType.CONTAINER)
                        .technologyStack(Framework.SPRING_BOOT, ProgrammingLanguage.JAVA, Database.SQLSERVER, MessageBroker.NONE)
                        .build())
                .build();

        DefaultCloudCompatibilityAdvisor advisor = CloudCompatibilityAdvisor.defaultAdvisor();
        List<EvaluationResult.ValidationResult> results = advisor.analyze(blueprint);

        assertThat(results).hasSize(1);
        EvaluationResult.ValidationResult result = results.get(0);
        assertThat(result.severity()).isEqualTo(RuleDefinition.Severity.MEDIUM);
        assertThat(result.message()).contains("SQLSERVER");
        assertThat(result.componentName()).isEqualTo("LegacyService");
    }

    @Test
    void analyze_withSupportedDatabase_returnsNoIssues() {
        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder()
                .name("Valid Arch")
                .addComponent(ComponentBuilder.builder()
                        .name("AppService")
                        .cloudProvider(CloudProvider.AWS)
                        .deploymentType(DeploymentType.CONTAINER)
                        .technologyStack(Framework.SPRING_BOOT, ProgrammingLanguage.JAVA, Database.POSTGRESQL, MessageBroker.KAFKA)
                        .build())
                .build();

        DefaultCloudCompatibilityAdvisor advisor = CloudCompatibilityAdvisor.defaultAdvisor();
        List<EvaluationResult.ValidationResult> results = advisor.analyze(blueprint);

        assertThat(results).isEmpty();
    }
}