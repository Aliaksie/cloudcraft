package dev.cloudcraft.core.model;

public record TechnologyStack(
        ProgrammingLanguage language,
        Framework framework,
        Database database,
        MessageBroker messageBroker)  {
}
