package dev.cloudcraft.core.validation;

import dev.cloudcraft.core.model.*;

import java.util.List;
import java.util.Set;

public class CloudDatabaseCompatibilityRule implements ComponentValidationRule {
    // todo: or hasMap
//    private static final Map<CloudProvider, Set<Database>> SUPPORTED_DATABASES = Map.of(
//            CloudProvider.AWS, Set.of(Database.POSTGRESQL, Database.MYSQL, Database.MONGODB),
//            CloudProvider.AZURE, Set.of(Database.POSTGRESQL, Database.MYSQL, Database.SQLSERVER),
//            CloudProvider.GCP, Set.of(Database.POSTGRESQL, Database.MYSQL)
//    );

    @Override
    public List<String> validate(Component component) {
        final CloudProvider cloud = component.cloudProvider();
        final TechnologyStack stack = component.technologyStack();

        if (!isDatabaseCompatible(cloud, stack.database())) {
            return List.of("Database " + stack.database() + " is not supported on cloud provider " + cloud);
        }

        return List.of();
    }


    private static boolean isDatabaseCompatible(final CloudProvider cloud, final Database database) {
        return switch (cloud) {
            case AWS -> Set.of(Database.POSTGRESQL, Database.MYSQL, Database.MONGODB).contains(database);
            case AZURE -> Set.of(Database.POSTGRESQL, Database.MYSQL, Database.SQLSERVER).contains(database);
            case GCP -> Set.of(Database.POSTGRESQL, Database.MYSQL).contains(database);
            default -> false;
        };
    }
}
