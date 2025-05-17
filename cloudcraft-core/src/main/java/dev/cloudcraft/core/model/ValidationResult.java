package dev.cloudcraft.core.model;

public record ValidationResult(String ruleName,
                               String componentName,
                               String message,
                               Severity severity) {
    public enum Severity {
        INFO, WARNING, ERROR
    }
}
