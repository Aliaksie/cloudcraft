package dev.cloudcraft.core.validation.rule;

import dev.cloudcraft.core.model.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class FrameworkCompatibilityRule implements ComponentValidationRule {
    // todo: or hasMap
//    private static final Map<ProgrammingLanguage, Set<Framework>> SUPPORTED_FRAMEWORKS = Map.of(
//            ProgrammingLanguage.JAVA, Set.of(Framework.SPRING_BOOT, Framework.QUARKUS),
//            ProgrammingLanguage.PYTHON, Set.of(Framework.FAST_API),
//            ProgrammingLanguage.JAVASCRIPT, Set.of(Framework.NODE_EXPRESS),
//            ProgrammingLanguage.CSHARP, Set.of(Framework.DOTNET_CORE)
//    );

    @Override
    public List<EvaluationResult.ValidationResult> validate(final Component component) {
        final TechnologyStack stack = component.technologyStack();

        // Language vs Framework
        if (!isFrameworkCompatible(stack.language(), stack.framework())) {
            return List.of(new EvaluationResult.ValidationResult(
                    UUID.randomUUID().toString(),
                    name(),
                    component.name(),
                    "Framework " + stack.framework() + " is not compatible with language " + stack.language(),
                    RuleDefinition.Severity.MEDIUM
            ));
        }

        return List.of();
    }

    private static boolean isFrameworkCompatible(final ProgrammingLanguage language, final Framework framework) {
        return switch (language) {
            case JAVA -> Set.of(Framework.SPRING_BOOT, Framework.QUARKUS).contains(framework);
            case PYTHON -> Set.of(Framework.FAST_API).contains(framework);
            case JAVASCRIPT -> Set.of(Framework.NODE_EXPRESS).contains(framework);
            case CSHARP -> Set.of(Framework.DOTNET_CORE).contains(framework);
            default -> false;
        };
    }
}
