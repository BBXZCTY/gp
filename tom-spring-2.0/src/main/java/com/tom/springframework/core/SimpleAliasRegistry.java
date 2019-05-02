package com.tom.springframework.core;

import com.tom.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleAliasRegistry implements AliasRegistry {

    private final Map<String, String> aliasMap = new ConcurrentHashMap<>(16);

    @Override
    public void registerAlias(String name, String alias) {
        Assert.hasText(name, "'name' must not be empty");
        Assert.hasText(alias, "'alias' must not be empty");
        if (alias.equals(name)) {
            this.aliasMap.remove(alias);
        }
        else {
            String registeredName = this.aliasMap.get(alias);
            if (registeredName != null) {
                if (registeredName.equals(name)) {
                    // An existing alias - no need to re-register
                    return;
                }
                if (!allowAliasOverriding()) {
                    throw new IllegalStateException("Cannot register alias '" + alias + "' for name '" +
                            name + "': It is already registered for name '" + registeredName + "'.");
                }
            }
            checkForAliasCircle(name, alias);
            this.aliasMap.put(alias, name);
        }
    }

    protected boolean allowAliasOverriding() {
        return true;
    }

    protected void checkForAliasCircle(String name, String alias) {
        if (hasAlias(alias, name)) {
            throw new IllegalStateException("Cannot register alias '" + alias +
                    "' for name '" + name + "': Circular reference - '" +
                    name + "' is a direct or indirect alias for '" + alias + "' already");
        }
    }

    public boolean hasAlias(String name, String alias) {
        for (Map.Entry<String, String> entry : this.aliasMap.entrySet()) {
            String registeredName = entry.getValue();
            if (registeredName.equals(name)) {
                String registeredAlias = entry.getKey();
                return (registeredAlias.equals(alias) || hasAlias(registeredAlias, alias));
            }
        }
        return false;
    }

    public String canonicalName(String name) {
        String canonicalName = name;
        // Handle aliasing...
        String resolvedName;
        do {
            resolvedName = this.aliasMap.get(canonicalName);
            if (resolvedName != null) {
                canonicalName = resolvedName;
            }
        }
        while (resolvedName != null);
        return canonicalName;
    }

}
