package dev.cloudcraft.core.advisor;

import dev.cloudcraft.core.advisor.rule.CloudCompatibilityRule;
import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.EvaluationResult;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultCloudCompatibilityAdvisor implements CloudCompatibilityAdvisor {

    private final List<CloudCompatibilityRule> rules;

    public DefaultCloudCompatibilityAdvisor(List<CloudCompatibilityRule> rules) {
        this.rules = rules;
    }

    public List<EvaluationResult.ValidationResult> analyze(final ArchitectureBlueprint blueprint) {
        return rules.stream()
                .flatMap(rule -> rule.analyze(blueprint).stream())
                .collect(Collectors.toList());
    }

}
