package algorithm.optimizedAlgorithm.helpers;

import algorithm.optimizedAlgorithm.evaluator.MatchEvaluator;
import models.Pair;
import models.Participant;

import java.util.ArrayList;
import java.util.List;

public class PairGenerator {
    private final MatchEvaluator matchEvaluator;

    public PairGenerator(MatchEvaluator matchEvaluator) {
        this.matchEvaluator = matchEvaluator;
    }

    public List<Pair> generateAndSortPairs(List<Participant> participants) {
        List<Pair> pairs = new ArrayList<>();

        for (int i = 0; i < participants.size(); i++) {
            for (int j = i + 1; j < participants.size(); j++) {
                Participant p1 = participants.get(i);
                Participant p2 = participants.get(j);

                double quality = matchEvaluator.calculateQuality(p1, p2);

                pairs.add(new Pair(p1, p2, quality));
            }
        }

        pairs.sort((pair1, pair2) -> Double.compare(pair2.quality(), pair1.quality()));

        return pairs;
    }
}

