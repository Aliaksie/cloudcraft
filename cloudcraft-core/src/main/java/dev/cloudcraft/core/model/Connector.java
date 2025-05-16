package dev.cloudcraft.core.model;

public record Connector(String fromComponentId,
                        String toComponentId,
                        String protocol) {
}
