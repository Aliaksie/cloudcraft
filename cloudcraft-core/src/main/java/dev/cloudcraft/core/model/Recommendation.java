package dev.cloudcraft.core.model;

public record Recommendation(
        String componentName,
        String suggestion,
        RecommendationType type,
        RecommendationSeverity severity) {

    public enum RecommendationType {
        PERFORMANCE, COST_OPTIMIZATION, MODERNIZATION, SCALABILITY
    }

    public enum RecommendationSeverity {
        LOW, MEDIUM, HIGH
    }
}
