package dev.cloudcraft.core.rules;

import dev.cloudcraft.core.model.RuleSource;

import java.util.List;
import java.util.Map;

public interface DynamicRuleLoader<T> {
    Map<RuleSource, List<T>> loadRules();
}
