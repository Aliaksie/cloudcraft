package dev.cloudcraft.core.rules;

import dev.cloudcraft.core.model.RuleDefinition;
import dev.cloudcraft.core.model.RuleSource;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class YamlRuleLoaderTest {
    @Test
    void shouldLoadValidationRulesFromYaml() {
        String path = "src/main/resources/validation-rules.yaml";
        var loader = new YamlRuleLoader<>(
                path,
                RuleDefinition.ValidationRuleDefinition[].class);


        var result = loader.loadRules();

        assertNotNull(result);
        List<RuleDefinition.ValidationRuleDefinition> expected = result.get(RuleSource.yaml(path));
        assertThat(expected).isNotEmpty();
        assertEquals("rule-001", expected.get(0).id());
    }

}