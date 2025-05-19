package dev.cloudcraft.core.recommendation.rule;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.DeploymentType;
import dev.cloudcraft.core.model.EvaluationResult;
import dev.cloudcraft.core.model.RuleDefinition;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


import static dev.cloudcraft.core.model.EvaluationResult.Recommendation.RecommendationType.MODERNIZATION;

public class ContainerDeploymentRecommendationRule implements RecommendationRule {
    @Override
    public List<EvaluationResult.Recommendation> evaluate(ArchitectureBlueprint blueprint) {
        return blueprint.components().stream().filter(component -> component.deploymentType() == DeploymentType.VM).map(component -> new EvaluationResult.Recommendation(
                UUID.randomUUID().toString(),
                name(),
                component.name(),
                "Consider moving from VM to container-based deployment for better scalability.",
                MODERNIZATION,
                RuleDefinition.Severity.MEDIUM
        )).collect(Collectors.toList());
    }
}
