package dev.cloudcraft.core.rules;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.EvaluationResult;
import dev.cloudcraft.core.model.RuleDefinition;
import dev.cloudcraft.core.rules.engine.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenericDynamicRuleEvaluatorTest {
    @Test
    public void testValidationRuleEvaluation() {
        RuleDefinition.ValidationRuleDefinition rule = new RuleDefinition.ValidationRuleDefinition("val-001",
                "Require Cloud Provider",
                RuleDefinition.RuleLevel.COMPONENT,
                "Each component must define a cloud provider.",
                RuleDefinition.Severity.MEDIUM,
                "component.cloudProvider != null");

        Component testComponent = new Component("MyComponent", null, null, null, null, null); // Should fail validation
        ExpressionEngine<RuleDefinition.ValidationRuleDefinition, Component, EvaluationResult.ValidationResult> engine = new ValidationComponentExpressionEngine();
        GenericDynamicRuleEvaluator<RuleDefinition.ValidationRuleDefinition, Component, EvaluationResult.ValidationResult> evaluator =
                new GenericDynamicRuleEvaluator<>(
                        RuleDefinition.RuleLevel.COMPONENT,
                        RuleDefinition.RuleType.VALIDATION,
                        List.of(rule),
                        engine);

        List<EvaluationResult.ValidationResult> results = evaluator.evaluate(testComponent);

        assertEquals(1, results.size());
        assertEquals("val-001", results.get(0).id());
    }

    @Test
    public void testRecommendationRuleEvaluation() {
        RuleDefinition.RecommendationRuleDefinition rule = new RuleDefinition.RecommendationRuleDefinition("rec-001",
                "Use Managed Database Service",
                RuleDefinition.RuleLevel.COMPONENT,
                "Use RDS or Cloud SQL",
                RuleDefinition.Severity.MEDIUM,
                "component.database == 'POSTGRESQL'",
                "Use RDS or Cloud SQL");

        Component testComponent = new Component("DBComponent", null, null, null, null, null);

        ExpressionEngine<RuleDefinition.RecommendationRuleDefinition, Component, EvaluationResult.Recommendation> engine = new RecommendationComponentExpressionEngine();
        GenericDynamicRuleEvaluator<RuleDefinition.RecommendationRuleDefinition, Component, EvaluationResult.Recommendation> evaluator =
                new GenericDynamicRuleEvaluator<>(
                        RuleDefinition.RuleLevel.COMPONENT,
                        RuleDefinition.RuleType.RECOMMENDATION,
                        List.of(rule),
                        engine);

        List<EvaluationResult.Recommendation> recommendations = evaluator.evaluate(testComponent);

        assertEquals(1, recommendations.size());
        assertEquals("rec-001", recommendations.get(0).id());
    }

    @Test
    public void testAdvisorRuleEvaluation() {
        RuleDefinition.AdvisorRuleDefinition rule = new RuleDefinition.AdvisorRuleDefinition("adv-001",
                "Consider Multi-region Deployment",
                RuleDefinition.RuleLevel.BLUEPRINT,
                "Consider deploying in multiple regions",
                RuleDefinition.Severity.MEDIUM,
                "architecture.regionCount < 2",
                "Consider deploying in multiple regions"
        );

        ArchitectureBlueprint blueprint = new ArchitectureBlueprint("Test Blueprint", List.of(), List.of());

        ExpressionEngine<RuleDefinition.AdvisorRuleDefinition, ArchitectureBlueprint, EvaluationResult> engine = new AdvisorBlueprintExpressionEngine();
        GenericDynamicRuleEvaluator<RuleDefinition.AdvisorRuleDefinition, ArchitectureBlueprint, EvaluationResult> evaluator =
                new GenericDynamicRuleEvaluator<>(
                        RuleDefinition.RuleLevel.BLUEPRINT,
                        RuleDefinition.RuleType.ADVISOR,
                        List.of(rule),
                        engine);

        List<EvaluationResult> advices = evaluator.evaluate(blueprint);

        assertEquals(1, advices.size());
        assertEquals("adv-001", advices.get(0).id());
    }
}