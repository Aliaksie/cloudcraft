package dev.cloudcraft.core.rules;


import dev.cloudcraft.core.model.RuleDefinition;
import dev.cloudcraft.core.model.RuleDefinition.RuleType;
import dev.cloudcraft.core.model.RuleSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicRuleRegistry {
    private final Map<RuleType, Map<RuleSource, List<RuleDefinition>>> ruleMap = new EnumMap<>(RuleType.class);
    private final List<DynamicRuleLoader<? extends RuleDefinition>> loaders;

    public DynamicRuleRegistry(final List<DynamicRuleLoader<? extends RuleDefinition>> loaders) {
        this.loaders = loaders;
        load();
    }

    // todo: sync, reset...
    private void load() {
        for (final DynamicRuleLoader<? extends RuleDefinition> loader : loaders) {
            final Map<RuleSource, ? extends List<? extends RuleDefinition>> sourceMap = loader.loadRules();
            for (final Map.Entry<RuleSource, ? extends List<? extends RuleDefinition>> entry : sourceMap.entrySet()) {
                final RuleSource source = entry.getKey();
                final List<? extends RuleDefinition> loadedRules = entry.getValue();
                // todo: group by  level ...
                for (final RuleDefinition rule : loadedRules) {
                    if (rule instanceof RuleDefinition.ValidationRuleDefinition) {
                        ruleMap.computeIfAbsent(RuleType.VALIDATION, __ -> new HashMap<>())
                                .computeIfAbsent(source, __ -> new ArrayList<>())
                                .add(rule);
                    } else if (rule instanceof RuleDefinition.RecommendationRuleDefinition) {
                        ruleMap.computeIfAbsent(RuleType.RECOMMENDATION, __ -> new HashMap<>())
                                .computeIfAbsent(source, __ -> new ArrayList<>())
                                .add(rule);
                    } else if (rule instanceof RuleDefinition.AdvisorRuleDefinition) {
                        ruleMap.computeIfAbsent(RuleType.ADVISOR, __ -> new HashMap<>())
                                .computeIfAbsent(source, __ -> new ArrayList<>())
                                .add(rule);
                    }
                }
            }
        }
    }


    public Map<RuleSource, List<RuleDefinition>> getRulesByType(final RuleType type) {
        return ruleMap.getOrDefault(type, Map.of());
    }

    public List<RuleDefinition> getAllRulesOfType(final RuleType type) {
        return ruleMap.getOrDefault(type, Map.of())
                .values().stream()
                .flatMap(Collection::stream)
                .toList();
    }
}
