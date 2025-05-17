package dev.cloudcraft.core.advisor.rule;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UnsupportedTechnologyStackRule implements CloudCompatibilityRule {
    private static final Map<CloudProvider, Set<Database>> SUPPORTED_DATABASES = Map.of(
            CloudProvider.AWS, Set.of(Database.POSTGRESQL, Database.MYSQL, Database.DYNAMODB),
            CloudProvider.AZURE, Set.of(Database.POSTGRESQL, Database.SQLSERVER, Database.COSMOSDB),
            CloudProvider.GCP, Set.of(Database.POSTGRESQL, Database.FIRESTORE, Database.BIGTABLE)
    );

    @Override
    public List<ValidationResult> analyze(ArchitectureBlueprint blueprint) {
        List<ValidationResult> results = new ArrayList<>();

        blueprint.components().forEach(component -> {
            final CloudProvider provider = component.cloudProvider();
            final Database db = component.technologyStack().database();
            if (provider == CloudProvider.NONE || db == Database.NONE) {
                return;
            }
            if (!SUPPORTED_DATABASES.getOrDefault(provider, Set.of()).contains(db)) {
                results.add(new ValidationResult(
                        name(),
                        component.name(),
                        String.format("Database %s is not supported on %s", db, provider),
                        ValidationResult.Severity.WARNING
                ));
            }
        });

        return results;
    }
}
