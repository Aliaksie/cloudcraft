package dev.cloudcraft.cli;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CloudCraftCliTest {
    @Test
    void testAnalyzeCommand_runsSuccessfully() {
        String[] args = {
                "analyze",
                "--file", "src/test/resources/blueprint.yaml",
                "--format", "text"
        };

        CloudCraftCli cli = new CloudCraftCli();
        int exitCode = new CommandLine(cli).execute(args);

        // Then
        assertEquals(0, exitCode);
    }

    @Test
    void testAnalyzeCommand_withDiagramGeneration() throws IOException {
        Path outputDiagram = Path.of("architecture.svg");
        Files.deleteIfExists(outputDiagram);

        String[] args = {
                "analyze",
                "--file", "src/test/resources/blueprint.yaml",
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