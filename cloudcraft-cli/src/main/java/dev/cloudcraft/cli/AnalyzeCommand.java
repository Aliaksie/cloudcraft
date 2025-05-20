package dev.cloudcraft.cli;

import dev.cloudcraft.core.advisor.CloudCompatibilityAdvisor;
import dev.cloudcraft.core.dsl.ArchitectureBlueprint;
import dev.cloudcraft.core.model.DiagramStyle;
import dev.cloudcraft.core.model.EvaluationResult;
import dev.cloudcraft.core.recommendation.RecommendationEngine;
import dev.cloudcraft.core.validation.ArchitectureValidator;

import dev.cloudcraft.core.visualizer.DiagramGenerator;
import picocli.CommandLine;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "analyze", description = "Analyze a cloud blueprint YAML file")
public class AnalyzeCommand implements Callable<Integer> {

    // more --filter, --source, ..

    @CommandLine.Option(names = {"-f", "--file"}, description = "Path to YAML blueprint file", required = true)
    private File file;

    @CommandLine.Option(names = {"--diagram"}, description = "Generate PlantUML diagram")
    private boolean generateDiagram;

    @CommandLine.Option(names = {"--format"}, description = "Output format: text or json", defaultValue = "text")
    private String format;

    @Override
    public Integer call() {
        // 1. Parse YAML to Blueprint, more format ?
        try (final InputStream input = Files.newInputStream(file.toPath())) {
            final ArchitectureBlueprint blueprint = ArchitectureBlueprint.fromYaml(input);

            // 2. Run static + dynamic rules, cmd option for each ?
            final ArchitectureValidator architectureValidator = ArchitectureValidator.defaultValidator();
            final List<EvaluationResult.ValidationResult> validations = architectureValidator.validate(blueprint);

            final RecommendationEngine recommendationEngine = RecommendationEngine.defaultAdvisor();
            final List<EvaluationResult.Recommendation> recommendations = recommendationEngine.recommend(blueprint);

            final CloudCompatibilityAdvisor cloudCompatibilityAdvisor = CloudCompatibilityAdvisor.defaultAdvisor();
            final List<EvaluationResult.ValidationResult> advice = cloudCompatibilityAdvisor.analyze(blueprint);

            // 3. Output results... more formats ?
            final PrettyCliPrinter printer = new PrettyCliPrinter();

            printer.printValidations(validations);
            printer.printRecommendations(recommendations);
            printer.printAdvice(advice);

            // 4. Generate diagram, cmd diagram style
            if (generateDiagram) {
                final DiagramGenerator generator = DiagramGenerator.defaultGenerator();
                String plantUml = generator.generate(blueprint, DiagramStyle.COMPONENT);
                // format ... ? or just string ...
                Path out = Path.of("architecture.svg");
                Files.writeString(out, plantUml);
                System.out.println("Generated diagram: " + out.toAbsolutePath());
            }
        } catch (final Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 1;
        }
        return 0;
    }
}
