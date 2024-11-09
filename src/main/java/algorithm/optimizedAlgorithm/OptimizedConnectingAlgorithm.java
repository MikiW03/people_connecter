package algorithm.optimizedAlgorithm;

import algorithm.ConnectingAlgorithm;
import algorithm.optimizedAlgorithm.helpers.ConnectionCounter;
import algorithm.optimizedAlgorithm.helpers.PairGenerator;
import algorithm.optimizedAlgorithm.helpers.PairOptimizer;
import algorithm.optimizedAlgorithm.evaluator.MatchEvaluator;
import models.Pair;
import models.Participant;

import java.util.*;

public class OptimizedConnectingAlgorithm implements ConnectingAlgorithm {
    private final ConnectionCounter connectionCounter;
    private final PairGenerator pairGenerator;
    private final PairOptimizer pairOptimizer;

    public OptimizedConnectingAlgorithm(MatchEvaluator matchEvaluator) {
        this.connectionCounter = new ConnectionCounter();
        this.pairGenerator = new PairGenerator(matchEvaluator);
        this.pairOptimizer = new PairOptimizer(matchEvaluator);
    }

    @Override
    public Map<Participant, List<Pair>> getTopMatches(List<Participant> participants) {
        if (participants.isEmpty()) return new HashMap<>();

        List<Pair> allPairs = pairGenerator.generateAndSortPairs(participants);

        Map<Participant, Integer> participantConnectionCount = new HashMap<>();
        Map<Participant, List<Pair>> resultMap = new HashMap<>();

        final int maxConnections = 5;
        double[] totalQuality = {0};

        allPairs.stream()
                .filter(pair -> connectionCounter.canAddPair(pair, participantConnectionCount, maxConnections))
                .forEach(pair -> {
                    resultMap.computeIfAbsent(pair.participant1(), k -> new ArrayList<>()).add(pair);
                    resultMap.computeIfAbsent(pair.participant2(), k -> new ArrayList<>()).add(pair);
                    totalQuality[0] += pair.quality();
                });

        System.out.printf("Total quality score of selected pairs: %.2f\n", totalQuality[0]);

        pairOptimizer.optimizeMatches(resultMap, totalQuality[0]);
        return resultMap;
    }
}
