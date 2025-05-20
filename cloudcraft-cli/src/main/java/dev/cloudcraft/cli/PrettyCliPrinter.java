package dev.cloudcraft.cli;

import dev.cloudcraft.core.model.EvaluationResult;

import java.util.List;

public class PrettyCliPrinter {

    public void printValidations(final List<EvaluationResult.ValidationResult> results) {
        System.out.println("🔍 Validation Results:");
        results.forEach(r -> System.out.printf(" - [%s] %s%n", r.severity(), r.message()));
    }

    public void printRecommendations(final List<EvaluationResult.Recommendation> results) {
        System.out.println("💡 Recommendations:");
        results.forEach(r -> System.out.printf(" - %s%n", r.message()));
    }

    public void printAdvice(final List<EvaluationResult.ValidationResult> results) {
        System.out.println("🧠 Advisor Tips:");
        results.forEach(r -> System.out.printf(" - %s%n", r.message()));
    }
}
