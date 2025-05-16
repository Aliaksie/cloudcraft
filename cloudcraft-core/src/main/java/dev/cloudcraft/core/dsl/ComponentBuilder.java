package dev.cloudcraft.core.dsl;

import dev.cloudcraft.core.model.CloudProvider;
import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.Database;
import dev.cloudcraft.core.model.DeploymentType;
import dev.cloudcraft.core.model.Environment;
import dev.cloudcraft.core.model.Framework;
import dev.cloudcraft.core.model.MessageBroker;
import dev.cloudcraft.core.model.ProgrammingLanguage;
import dev.cloudcraft.core.model.TechnologyStack;

import java.util.UUID;

public class ComponentBuilder {
    private String name;
    private CloudProvider cloudProvider;
    private DeploymentType deploymentType;
    private Framework framework;
    private ProgrammingLanguage language;
    private Database database;
    private MessageBroker messageBroker;

    public static ComponentBuilder builder() {
        return new ComponentBuilder();
    }

    public ComponentBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ComponentBuilder cloudProvider(CloudProvider cloudProvider) {
        this.cloudProvider = cloudProvider;
        return this;
    }

    public ComponentBuilder deploymentType(DeploymentType deploymentType) {
        this.deploymentType = deploymentType;
        return this;
    }

    public ComponentBuilder technologyStack(
            Framework framework,
            ProgrammingLanguage language,
            Database database,
            MessageBroker messageBroker) {
        this.framework = framework;
        this.language = language;
        this.database = database;
        this.messageBroker = messageBroker;
        return this;
    }

    public Component build() {
        return new Component(UUID.randomUUID().toString(), name,
                new TechnologyStack(language, framework, database, messageBroker),
                deploymentType, cloudProvider, Environment.PROD);
    }
}
