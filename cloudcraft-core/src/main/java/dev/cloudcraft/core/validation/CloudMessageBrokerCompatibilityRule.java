package dev.cloudcraft.core.validation;

import dev.cloudcraft.core.model.CloudProvider;
import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.MessageBroker;

import java.util.List;
import java.util.Set;

public class CloudMessageBrokerCompatibilityRule implements ComponentValidationRule {

//    private static final Map<CloudProvider, Set<MessageBroker>> SUPPORTED_BROKERS = Map.of(
//            CloudProvider.AWS, Set.of(MessageBroker.KAFKA, MessageBroker.SQS),
//            CloudProvider.AZURE, Set.of(MessageBroker.KAFKA, MessageBroker.AZURE_SERVICE_BUS),
//            CloudProvider.GCP, Set.of(MessageBroker.KAFKA, MessageBroker.PUBSUB)
//    );

    @Override
    public List<String> validate(Component component) {
        final CloudProvider cloud = component.cloudProvider();
        final MessageBroker broker = component.technologyStack().messageBroker();

        if (!isMessageBrokerCompatible(cloud, broker)) {
            return List.of("Message broker " + broker + " is not supported on cloud provider " + cloud);
        }

        return List.of();
    }

    private static boolean isMessageBrokerCompatible(final CloudProvider cloud, final MessageBroker messageBroker) {
        return switch (cloud) {
            case AWS -> Set.of(MessageBroker.KAFKA, MessageBroker.SQS).contains(messageBroker);
            case AZURE -> Set.of(MessageBroker.KAFKA, MessageBroker.AZ_SERVICE_BUS).contains(messageBroker);
            case GCP -> Set.of(MessageBroker.KAFKA, MessageBroker.PUBSUB).contains(messageBroker);
            default -> false;
        };
    }
}
