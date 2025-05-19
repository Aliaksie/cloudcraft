package dev.cloudcraft.core.advisor.rule;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.CloudProvider;
import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.EvaluationResult;
import dev.cloudcraft.core.model.MessageBroker;
import dev.cloudcraft.core.model.RuleDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class UnsupportedMessageBrokerRule implements CloudCompatibilityRule {
    private static final Map<CloudProvider, Set<MessageBroker>> SUPPORTED_BROKERS = Map.of(
            CloudProvider.AWS, Set.of(MessageBroker.KAFKA, MessageBroker.SNS, MessageBroker.SQS),
            CloudProvider.AZURE, Set.of(MessageBroker.AZ_SERVICE_BUS, MessageBroker.AZ_EVENT_HUB),
            CloudProvider.GCP, Set.of(MessageBroker.PUBSUB, MessageBroker.KAFKA)
    );


    @Override
    public List<EvaluationResult.ValidationResult> analyze(final ArchitectureBlueprint blueprint) {
        List<EvaluationResult.ValidationResult> results = new ArrayList<>();
        for (final Component component : blueprint.components()) {
            final CloudProvider provider = component.cloudProvider();
            final MessageBroker broker = component.technologyStack().messageBroker();

            if (provider == CloudProvider.NONE || broker == MessageBroker.NONE) {
                continue;
            }

            Set<MessageBroker> supported = SUPPORTED_BROKERS.getOrDefault(provider, Set.of());

            if (!supported.contains(broker)) {
                return List.of(new EvaluationResult.ValidationResult(
                        UUID.randomUUID().toString(),
                        name(),
                        component.name(),
                        "Message broker " + broker + " is not commonly supported on " + provider,
                        RuleDefinition.Severity.MEDIUM
                ));
            }
        }


        return results;
    }
}
