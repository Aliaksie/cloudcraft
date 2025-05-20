package dev.cloudcraft.cli;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CloudCraftCliTest {
    @Test
    void testAnalyzeCommand_runsSuccessfully() throws IOException {
        // Given
        Path tempBlueprint = Files.createTempFile("blueprint", ".yaml");
        Files.writeString(tempBlueprint, """
                   name: BluePrint
                    components:
                    - id: "1"
                      name: "ValidService"
                      technologyStack:
                        language: "JAVA"
                        framework: "SPRING_BOOT"
                        database: "POSTGRESQL"
                        messageBroker: "KAFKA"
                      deploymentType: "CONTAINER"
                      cloudProvider: "AWS"
                      environment: "PROD"
                    dependencies: []
        """);

        String[] args = {
                "analyze",
                "--file", tempBlueprint.toString(),
                "--format", "text"
        };

        CloudCraftCli cli = new CloudCraftCli();
        int exitCode = new CommandLine(cli).execute(args);

        // Then
        assertEquals(0, exitCode);
    }

    @Test
    void testAnalyzeCommand_withDiagramGeneration() throws IOException {
        // Given
        Path tempBlueprint = Files.createTempFile("blueprint", ".yaml");
        Files.writeString(tempBlueprint, """
                    name: BluePrint
                    components:
                    - id: "1"
                      name: "ValidService"
                      technologyStack:
                        language: "JAVA"
                        framework: "SPRING_BOOT"
                        database: "POSTGRESQL"
                        messageBroker: "KAFKA"
                      deploymentType: "CONTAINER"
                      cloudProvider: "AWS"
                      environment: "PROD"
                    dependencies: []
        """);

        Path outputDiagram = Path.of("architecture.puml");
        Files.deleteIfExists(outputDiagram);

        String[] args = {
                "analyze",
                "--file", tempBlueprint.toString(),
                "--diagram"
        };

        CloudCraftCli cli = new CloudCraftCli();
        int exitCode = new CommandLine(cli).execute(args);

        // Then
        assertEquals(0, exitCode);
        assert(Files.exists(outputDiagram));

        // Cleanup
        Files.deleteIfExists(outputDiagram);
    }
}