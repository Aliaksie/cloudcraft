package dev.cloudcraft.core.model;

// todo!
public sealed interface EvaluationResult permits EvaluationResult.Recommendation, EvaluationResult.ValidationResult {
    String id();

    String ruleName();

    String componentName();

    String message();

    RuleDefinition.Severity severity();


    record ValidationResult(String id,
                            String ruleName,
                            String componentName,
                            String message,
                            RuleDefinition.Severity severity) implements EvaluationResult {
    }


    record Recommendation(
            String id,
            String ruleName,
            String componentName,
            String message,
            RecommendationType type,
            RuleDefinition.Severity severity) implements EvaluationResult {

        public enum RecommendationType {
            PERFORMANCE, COST_OPTIMIZATION, MODERNIZATION, SCALABILITY
        }

    }
}
