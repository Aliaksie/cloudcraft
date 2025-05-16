package dev.cloudcraft.core.recommendation;

import dev.cloudcraft.core.model.CloudProvider;
import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.Database;
import dev.cloudcraft.core.model.DeploymentType;
import dev.cloudcraft.core.model.Framework;
import dev.cloudcraft.core.model.MessageBroker;
import dev.cloudcraft.core.model.ProgrammingLanguage;
import dev.cloudcraft.core.model.TechnologyStack;

import java.util.ArrayList;
import java.util.List;

public class DefaultArchitectureRecommendationEngine implements ArchitectureRecommendationEngine {

    @Override
    public List<String> recommend(final List<Component> components) {
        final List<String> recommendations = new ArrayList<>();

        for (final Component component : components) {
            final String name = component.name();
            final DeploymentType deploymentType = component.deploymentType();
            final CloudProvider cloudProvider = component.cloudProvider();
            final TechnologyStack stack = component.technologyStack();

            // Recommend containerization
            if (deploymentType == DeploymentType.VM) {
                recommendations.add("[" + name + "] Consider using containers instead of VMs for portability and scalability.");
            }

            // Recommend managed DB
            if (stack.database() == Database.MYSQL || stack.database() == Database.POSTGRESQL) {
                recommendations.add("[" + name + "] Use managed database services like RDS (AWS), Cloud SQL (GCP), or Azure Database.");
            }

            // Recommend Quarkus for serverless Java
            if (stack.language() == ProgrammingLanguage.JAVA
                    && stack.framework() == Framework.SPRING_BOOT
                    && deploymentType == DeploymentType.SERVERLESS) {
                recommendations.add("[" + name + "] Consider using Quarkus instead of Spring Boot for faster cold starts on serverless.");
            }

            // Message broker optimization
            if (stack.messageBroker() == MessageBroker.RABBITMQ && cloudProvider == CloudProvider.AZURE) {
                recommendations.add("[" + name + "] Consider using Azure Service Bus as a native alternative to RabbitMQ on Azure.");
            }
        }

        return recommendations;
    }
}
