package dev.cloudcraft.core.model;

public record Component(
        String id,
        String name,
        TechnologyStack technologyStack,
        DeploymentType deploymentType,
        CloudProvider cloudProvider,
        Environment environment
) {}