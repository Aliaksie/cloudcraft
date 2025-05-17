package dev.cloudcraft.core.recommendation.rule;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.DeploymentType;
import dev.cloudcraft.core.model.Recommendation;

import java.util.List;
import java.util.stream.Collectors;

import static dev.cloudcraft.core.model.Recommendation.RecommendationSeverity.MEDIUM;
import static dev.cloudcraft.core.model.Recommendation.RecommendationType.MODERNIZATION;

public class ContainerDeploymentRecommendationRule implements RecommendationRule {
    @Override
    public List<Recommendation> evaluate(ArchitectureBlueprint blueprint) {
        return blueprint.components().stream().filter(component -> component.deploymentType() == DeploymentType.VM).map(component -> new Recommendation(
                component.name(),
                "Consider moving from VM to container-based deployment for better scalability.",
                MODERNIZATION,
                MEDIUM
        )).collect(Collectors.toList());
    }
}
