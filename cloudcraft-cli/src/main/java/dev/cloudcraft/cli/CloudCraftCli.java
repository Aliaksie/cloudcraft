package dev.cloudcraft.cli;

import picocli.CommandLine;

@CommandLine.Command(
        name = "cloudcraft",
        subcommands = { AnalyzeCommand.class },
        mixinStandardHelpOptions = true,
        version = "cloudcraft 0.1",
        description = "Cloud architecture analysis CLI"
)
public class CloudCraftCli implements Runnable {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CloudCraftCli())
                .addSubcommand("analyze", new AnalyzeCommand())
                .execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Use `cloudcraft analyze --file your-blueprint.yaml`");
    }
}
