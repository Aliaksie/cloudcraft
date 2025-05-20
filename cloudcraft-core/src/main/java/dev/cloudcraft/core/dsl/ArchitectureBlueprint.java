package dev.cloudcraft.core.dsl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.cloudcraft.core.model.Component;
import dev.cloudcraft.core.model.Dependency;
import dev.cloudcraft.core.model.DependencyType;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public record ArchitectureBlueprint(String name, List<Component> components, List<Dependency> dependencies) {
    private static final ObjectMapper MAPPER_JSON = new ObjectMapper();
    private static final ObjectMapper MAPPER_YAML = new ObjectMapper(new YAMLFactory());

    public String toJson() throws JsonProcessingException {
        return MAPPER_JSON.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

    public String toYaml() throws JsonProcessingException {
        return MAPPER_YAML.writeValueAsString(this);
    }

    public static ArchitectureBlueprint fromYaml(final InputStream yaml) throws IOException {
        return MAPPER_YAML.readValue(yaml, ArchitectureBlueprint.class);
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private final List<Component> components = new ArrayList<>();
        private final List<Dependency> dependencies = new ArrayList<>();

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder addComponent(Component component) {
            this.components.add(component);
            return this;
        }

        public Builder addDependency(String from, String to, DependencyType type) {
            this.dependencies.add(new Dependency(from, to, type));
            return this;
        }

        public Builder addDependency(String from, String to) {
            this.dependencies.add(new Dependency(from, to, null));
            return this;
        }

        public ArchitectureBlueprint build() {
            return new ArchitectureBlueprint(name, components, dependencies);
        }
    }
}
