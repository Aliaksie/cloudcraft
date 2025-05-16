package dev.cloudcraft.core.validation;

import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.Framework;
import dev.cloudcraft.core.model.ProgrammingLanguage;
import dev.cloudcraft.core.model.TechnologyStack;

import java.util.List;
import java.util.Set;

public class FrameworkCompatibilityRule implements ComponentValidationRule {
    // todo: or hasMap
//    private static final Map<ProgrammingLanguage, Set<Framework>> SUPPORTED_FRAMEWORKS = Map.of(
//            ProgrammingLanguage.JAVA, Set.of(Framework.SPRING_BOOT, Framework.QUARKUS),
//            ProgrammingLanguage.PYTHON, Set.of(Framework.FAST_API),
//            ProgrammingLanguage.JAVASCRIPT, Set.of(Framework.NODE_EXPRESS),
//            ProgrammingLanguage.CSHARP, Set.of(Framework.DOTNET_CORE)
//    );

    @Override
    public List<String> validate(final Component component) {
        final TechnologyStack stack = component.technologyStack();

        // Language vs Framework
        if (!isFrameworkCompatible(stack.language(), stack.framework())) {
            return List.of("Framework " + stack.framework() + " is not compatible with language " + stack.language());
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
