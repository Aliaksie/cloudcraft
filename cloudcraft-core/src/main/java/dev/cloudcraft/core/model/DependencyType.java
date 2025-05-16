package dev.cloudcraft.core.model;

public enum DependencyType {
    ASYNCHRONOUS,
    MESSAGE_BROKER,
    DATABASE,
    DEPLOY_TIME,
    OPTIONAL,
    RUNTIME,
    SYNCHRONOUS
}
