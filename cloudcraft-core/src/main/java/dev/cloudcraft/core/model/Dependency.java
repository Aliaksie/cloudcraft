package dev.cloudcraft.core.model;

public record Dependency(String from, String to, DependencyType type) {
}
