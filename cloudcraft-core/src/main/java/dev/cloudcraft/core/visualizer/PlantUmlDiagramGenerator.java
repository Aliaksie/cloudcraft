package dev.cloudcraft.core.visualizer;

import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.DependencyType;
import dev.cloudcraft.core.model.DiagramStyle;
import dev.cloudcraft.core.model.TechnologyStack;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public interface PlantUmlDiagramGenerator extends DiagramGenerator {

    default String generate(ArchitectureBlueprint blueprint, DiagramStyle style) {
        throw new IllegalArgumentException();
    }

    default String toSVG(final String uml) {
        try {
            final SourceStringReader reader = new SourceStringReader(uml);
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            reader.outputImage(os, 0, new FileFormatOption(FileFormat.SVG));
            return os.toString(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate diagram", e);
        }
    }

    // todo:!!
    default String arrowFor(final DependencyType type) {
        if (type == null) return "-->";
        return switch (type) {
            case ASYNCHRONOUS, MESSAGE_BROKER -> "..>";
            case DATABASE -> "--|>";
            case DEPLOY_TIME -> "==>";
            case OPTIONAL -> "-->";
            case RUNTIME, SYNCHRONOUS -> "-->"; // default
        };
    }

    default String normalize(final String name) {
        return name.replaceAll("[^a-zA-Z0-9]", "_");
    }

    default String extractStackInfo(final TechnologyStack stack) {
        return String.format("%s, %s, %s, %s",
                safe(stack.framework()),
                safe(stack.language()),
                safe(stack.database()),
                safe(stack.messageBroker()));
    }

    default String safe(final Object obj) {
        return obj != null ? obj.toString() : "N/A";
    }

}
