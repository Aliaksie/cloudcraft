package dev.cloudcraft.core.model;

public sealed interface RuleDefinition permits RuleDefinition.AdvisorRuleDefinition, RuleDefinition.RecommendationRuleDefinition, RuleDefinition.ValidationRuleDefinition {
    String id();

    String name();

    RuleLevel level();

    Severity severity();

    String description();


    record ValidationRuleDefinition(String id,
                                    String name,
                                    RuleLevel level,
                                    String description,
                                    Severity severity,
                                    String expression) implements RuleDefinition {
    }

    record RecommendationRuleDefinition(String id,
                                        String name,
                                        RuleLevel level,
                                        String description,
                                        Severity severity,
                                        String condition,
                                        String recommendation) implements RuleDefinition {

    }

    record AdvisorRuleDefinition(String id,
                                 String name,
                                 RuleLevel level,
                                 String description,
                                 Severity severity,
                                 String condition,
                                 String advice) implements RuleDefinition {

    }

    enum RuleType {
        VALIDATION,
        RECOMMENDATION,
        ADVISOR
    }

    enum RuleLevel {
        COMPONENT,
        BLUEPRINT
    }

    enum Severity {
        LOW, MEDIUM, HIGH
    }
}


