package dev.cloudcraft.core.model;

public record RuleSource(String id, String origin) {
    public static RuleSource yaml(String path) {
        return new RuleSource("yaml", path);
    }

    public static RuleSource db(String table) {
        return new RuleSource("db", table);
    }
}
