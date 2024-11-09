package algorithm.optimizedAlgorithm.helpers;

import algorithm.optimizedAlgorithm.evaluator.MatchEvaluator;
import models.Pair;
import models.Participant;

import java.util.*;

public class PairOptimizer {
    private final MatchEvaluator matchEvaluator;

    public PairOptimizer(MatchEvaluator matchEvaluator) {
        this.matchEvaluator = matchEvaluator;
    }

    public void optimizeMatches(Map<Participant, List<Pair>> resultMap, double currentTotalQuality) {
        Random random = new Random();
        final int maxAttempts = 10000;
        int attemptsWithoutImprovement = 0;

        System.out.println("Beginning optimization.");
        List<Pair> allPairs = initializePairs(resultMap);

        for (int i = 0; i < maxAttempts; i++) {
            List<Pair> selectedPairs = selectRandomPairs(allPairs, random);

            Pair pair1 = selectedPairs.get(0);
            Pair pair2 = selectedPairs.get(1);

            if (!arePairsSwappable(pair1, pair2)) {
                continue;
            }

            List<Pair> bestPairs = findBestCombination(resultMap, pair1, pair2);
            double qualityA = pair1.quality() + pair2.quality();
            double bestQuality = calculateQuality(bestPairs);

            if (bestQuality > qualityA) {
                applyBestCombination(resultMap, bestPairs, pair1, pair2);
                currentTotalQuality += (bestQuality - qualityA);
                logOptimizationProgress(currentTotalQuality, attemptsWithoutImprovement);
                attemptsWithoutImprovement = 0;
            } else {
                attemptsWithoutImprovement++;
            }

            if (attemptsWithoutImprovement >= 500) {
                System.out.println("No improvements for 500 consecutive attempts. Ending optimization.");
                break;
            }
        }
    }

    private List<Pair> initializePairs(Map<Participant, List<Pair>> resultMap) {
        return resultMap.values().stream()
                .flatMap(List::stream)
                .distinct()
                .toList();
    }

    private List<Pair> selectRandomPairs(List<Pair> allPairs, Random random) {
        Pair pair1 = allPairs.get(random.nextInt(allPairs.size()));
        Pair pair2 = allPairs.get(random.nextInt(allPairs.size()));
        return List.of(pair1, pair2);
    }

    private boolean arePairsSwappable(Pair pair1, Pair pair2) {
        Participant p1 = pair1.participant1();
        Participant p2 = pair1.participant2();
        Participant p3 = pair2.participant1();
        Participant p4 = pair2.participant2();

        return !(p1.equals(p3) || p1.equals(p4) || p2.equals(p3) || p2.equals(p4));
    }

    private List<Pair> findBestCombination(Map<Participant, List<Pair>> resultMap, Pair pair1, Pair pair2) {
        Participant p1 = pair1.participant1();
        Participant p2 = pair1.participant2();
        Participant p3 = pair2.participant1();
        Participant p4 = pair2.participant2();

        Pair newPair1B = createSortedPair(p1, p3);
        Pair newPair2B = createSortedPair(p2, p4);
        Pair newPair1C = createSortedPair(p1, p4);
        Pair newPair2C = createSortedPair(p2, p3);

        double qualityA = pair1.quality() + pair2.quality();
        double qualityB = newPair1B.quality() + newPair2B.quality();
        double qualityC = newPair1C.quality() + newPair2C.quality();

        if (qualityB > qualityA && notContainsPair(resultMap, newPair1B) && notContainsPair(resultMap, newPair2B)) {
            return List.of(newPair1B, newPair2B);
        } else if (qualityC > qualityA && notContainsPair(resultMap, newPair1C) && notContainsPair(resultMap, newPair2C)) {
            return List.of(newPair1C, newPair2C);
        }
        return List.of(pair1, pair2);
    }

    private double calculateQuality(List<Pair> pairs) {
        return pairs.stream().mapToDouble(Pair::quality).sum();
    }

    private void applyBestCombination(Map<Participant, List<Pair>> resultMap, List<Pair> bestPairs, Pair pair1, Pair pair2) {
        Participant p1 = pair1.participant1();
        Participant p2 = pair1.participant2();
        Participant p3 = pair2.participant1();
        Participant p4 = pair2.participant2();

        resultMap.get(p1).remove(pair1);
        resultMap.get(p2).remove(pair1);
        resultMap.get(p3).remove(pair2);
        resultMap.get(p4).remove(pair2);

        for (Pair newPair : bestPairs) {
            resultMap.computeIfAbsent(newPair.participant1(), k -> new ArrayList<>()).add(newPair);
            resultMap.computeIfAbsent(newPair.participant2(), k -> new ArrayList<>()).add(newPair);
        }
    }

    private void logOptimizationProgress(double currentTotalQuality, int attemptsWithoutImprovement) {
        if (attemptsWithoutImprovement > 0) {
            System.out.printf("%d attempts without improvement.\n", attemptsWithoutImprovement);
        }
        System.out.printf("Improvement found. New total quality: %.2f\n", currentTotalQuality);
    }

    private Pair createSortedPair(Participant p1, Participant p2) {
        if (p1.id() < p2.id()) {
            return new Pair(p1, p2, matchEvaluator.calculateQuality(p1, p2));
        } else {
            return new Pair(p2, p1, matchEvaluator.calculateQuality(p2, p1));
        }
    }

    private boolean notContainsPair(Map<Participant, List<Pair>> resultMap, Pair pair) {
        return !resultMap.getOrDefault(pair.participant1(), new ArrayList<>()).contains(pair)
                && !resultMap.getOrDefault(pair.participant2(), new ArrayList<>()).contains(pair);
    }
}
