package dev.cloudcraft.core.advisor;

import dev.cloudcraft.core.advisor.rule.UnsupportedMessageBrokerRule;
import dev.cloudcraft.core.advisor.rule.UnsupportedTechnologyStackRule;
import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.EvaluationResult;

import java.util.List;

public interface CloudCompatibilityAdvisor {

    List<EvaluationResult.ValidationResult> analyze(final ArchitectureBlueprint blueprint);

    static DefaultCloudCompatibilityAdvisor defaultAdvisor() {
        return new DefaultCloudCompatibilityAdvisor(List.of(
                new UnsupportedTechnologyStackRule(),
                new UnsupportedMessageBrokerRule()
        ));
    }
}
