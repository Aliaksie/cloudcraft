package dev.cloudcraft.core.rules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.cloudcraft.core.model.RuleDefinition;
import dev.cloudcraft.core.model.RuleSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class YamlRuleLoader<T> implements DynamicRuleLoader<T> {
    private final String filePath;
    private final Class<T[]> type;
    private final ObjectMapper mapper;

    public YamlRuleLoader(final String filePath, final Class<T[]> type) {
        this.filePath = filePath;
        this.type = type;
        mapper = new ObjectMapper(new YAMLFactory());
    }


    @Override
    public Map<RuleSource, List<T>> loadRules() {
        try (final InputStream input = Files.newInputStream(Paths.get(filePath))) {
            T[] rules = mapper.readValue(input, type);
            return Map.of(RuleSource.yaml(filePath), List.of(rules));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load rules from YAML", e);
        }
    }

    static List<DynamicRuleLoader<? extends RuleDefinition>> defaultYamlLoader() {
        return List.of(
                new YamlRuleLoader<>("src/main/resources/validation-rules.yaml", RuleDefinition.ValidationRuleDefinition[].class),
                new YamlRuleLoader<>("src/main/resources/recommendation-rules.yaml", RuleDefinition.RecommendationRuleDefinition[].class),
                new YamlRuleLoader<>("src/main/resources/advisor-rules.yaml", RuleDefinition.AdvisorRuleDefinition[].class)
        );
    }
}
