package dev.cloudcraft.core.validation.rule;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.dsl.ComponentBuilder;
import dev.cloudcraft.core.model.CloudProvider;
import dev.cloudcraft.core.model.Database;
import dev.cloudcraft.core.model.DeploymentType;
import dev.cloudcraft.core.model.Framework;
import dev.cloudcraft.core.model.MessageBroker;
import dev.cloudcraft.core.model.ProgrammingLanguage;
import dev.cloudcraft.core.model.ValidationResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MissingDeploymentTypeRuleTest {
    @Test
    void validate_allComponentsHaveDeploymentType_ok() {
        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder()
                .name("ValidApp")
                .addComponent(ComponentBuilder.builder()
                        .name("ServiceA")
                        .deploymentType(DeploymentType.CONTAINER)
                        .cloudProvider(CloudProvider.AWS)
                        .technologyStack(Framework.SPRING_BOOT, ProgrammingLanguage.JAVA, Database.POSTGRESQL, MessageBroker.KAFKA)
                        .build())
                .build();

        MissingDeploymentTypeRule rule = new MissingDeploymentTypeRule();
        List<ValidationResult> result = rule.validate(blueprint);

        assertThat(result).isEmpty();
    }

    @Test
    void validate_missingDeploymentType_returnsError() {
        ArchitectureBlueprint blueprint = ArchitectureBlueprint.builder()
                .name("InvalidApp")
                .addComponent(ComponentBuilder.builder()
                        .name("ServiceB")
                        .cloudProvider(CloudProvider.AWS)
                        .technologyStack(Framework.SPRING_BOOT, ProgrammingLanguage.JAVA, Database.POSTGRESQL, MessageBroker.KAFKA)
                        .build()) // No deployment type
                .build();

        MissingDeploymentTypeRule rule = new MissingDeploymentTypeRule();
        List<ValidationResult> result = rule.validate(blueprint);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).message()).contains("ServiceB");
    }
}