package dev.cloudcraft.core.recommendation;

import dev.cloudcraft.core.model.Component;

import java.util.List;

public interface ArchitectureRecommendationEngine {

    List<String> recommend(List<Component> components);

}
