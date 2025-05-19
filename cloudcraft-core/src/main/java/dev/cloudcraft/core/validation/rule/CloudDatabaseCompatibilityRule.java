package dev.cloudcraft.core.validation.rule;

import dev.cloudcraft.core.model.CloudProvider;
import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.Database;
import dev.cloudcraft.core.model.EvaluationResult;
import dev.cloudcraft.core.model.RuleDefinition;
import dev.cloudcraft.core.model.TechnologyStack;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CloudDatabaseCompatibilityRule implements ComponentValidationRule {
    // todo: or hasMap
//    private static final Map<CloudProvider, Set<Database>> SUPPORTED_DATABASES = Map.of(
//            CloudProvider.AWS, Set.of(Database.POSTGRESQL, Database.MYSQL, Database.MONGODB),
//            CloudProvider.AZURE, Set.of(Database.POSTGRESQL, Database.MYSQL, Database.SQLSERVER),
//            CloudProvider.GCP, Set.of(Database.POSTGRESQL, Database.MYSQL)
//    );

    @Override
    public List<EvaluationResult.ValidationResult> validate(final Component component) {
        final CloudProvider cloud = component.cloudProvider();
        final TechnologyStack stack = component.technologyStack();

        if (!isDatabaseCompatible(cloud, stack.database())) {
            return List.of(new EvaluationResult.ValidationResult(
                    UUID.randomUUID().toString(),
                    name(),
                    component.name(),
                    "Database " + stack.database() + " is not supported on cloud provider " + cloud,
                    RuleDefinition.Severity.MEDIUM

            ));
        }

        return List.of();
    }


    private static boolean isDatabaseCompatible(final CloudProvider cloud, final Database database) {
        return switch (cloud) {
            case AWS ->
                    Set.of(Database.POSTGRESQL, Database.MYSQL, Database.MONGODB, Database.DYNAMODB).contains(database);
            case AZURE ->
                    Set.of(Database.POSTGRESQL, Database.MYSQL, Database.SQLSERVER, Database.COSMOSDB).contains(database);
            case GCP -> Set.of(Database.POSTGRESQL, Database.MYSQL).contains(database);
            default -> false;
        };
    }
}
