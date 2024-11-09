import algorithm.ConnectingAlgorithm;
import algorithm.optimizedAlgorithm.OptimizedConnectingAlgorithm;
import algorithm.optimizedAlgorithm.evaluator.DefaultMatchEvaluator;
import algorithm.optimizedAlgorithm.evaluator.MatchEvaluator;
import input.ArgumentsValidator;
import input.InputParser;
import input.TxtFileInputParser;
import models.Pair;
import models.Participant;
import output.ConsoleOutputPresenter;
import output.OutputPresenter;
import output.TxtFileOutputPresenter;

import java.util.List;
import java.util.Map;

public class MainApp {
    public static void main(String[] args) {
        String defaultInputFile = "people.txt";
        String defaultOutputFile = "connections.txt";

        ArgumentsValidator argumentsValidator = new ArgumentsValidator(args);
        String inputFile = argumentsValidator.getInputFile(defaultInputFile);
        String outputFile = argumentsValidator.getOutputFile(defaultOutputFile);
        System.out.println();

        InputParser parser = new TxtFileInputParser();
        List<Participant> participants = parser.parse(inputFile);

        MatchEvaluator defaultEvaluator = new DefaultMatchEvaluator();
        ConnectingAlgorithm algorithm = new OptimizedConnectingAlgorithm(defaultEvaluator);
        Map<Participant, List<Pair>> matches = algorithm.getTopMatches(participants);

        OutputPresenter consoleOutputPresenter = new ConsoleOutputPresenter();
        consoleOutputPresenter.present(matches);

        OutputPresenter txtFileOutputPresenter = new TxtFileOutputPresenter(outputFile);
        txtFileOutputPresenter.present(matches);
    }
}
