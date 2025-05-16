package dev.cloudcraft.core.model;

import java.util.List;

public record SystemModel(String id, String name, List<Component> components, List<Connector> connectors) {

    public List<Component> components() {
        return List.copyOf(components);
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public List<Connector> connectors() {
        return List.copyOf(connectors);
    }

    public void addConnector(Connector connector) {
        connectors.add(connector);
    }
}
