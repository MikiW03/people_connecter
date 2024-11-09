package algorithm.optimizedAlgorithm.evaluator;

import models.Participant;

public interface MatchEvaluator {
    double calculateQuality(Participant p1, Participant p2);
}

