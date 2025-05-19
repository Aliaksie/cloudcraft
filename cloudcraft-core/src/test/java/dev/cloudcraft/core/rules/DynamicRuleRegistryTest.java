package dev.cloudcraft.core.rules;

import dev.cloudcraft.core.model.RuleDefinition;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DynamicRuleRegistryTest {
    @Test
    void shouldLoadAndGroupRulesByTypeAndSource() {

        var registry = new DynamicRuleRegistry(YamlRuleLoader.defaultYamlLoader());

        var validationRules = registry.getRulesByType(RuleDefinition.RuleType.VALIDATION);

        assertFalse(validationRules.isEmpty());

        // Verify rule source
        validationRules.forEach((source, rules) -> {
            assertEquals("yaml", source.id());
            assertNotNull(rules);
            assertFalse(rules.isEmpty());
        });

        // Flat version
        var all = registry.getAllRulesOfType(RuleDefinition.RuleType.VALIDATION);
        assertEquals(2, all.size());
    }
}