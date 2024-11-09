package algorithm.optimizedAlgorithm.evaluator;

import models.Participant;

public class DefaultMatchEvaluator implements MatchEvaluator {
    @Override
    public double calculateQuality(Participant participant1, Participant participant2) {
        long commonDesired = participant1.desiredOccupations().stream()
                .filter(participant2.occupation()::contains)
                .count();

        long reverseCommonDesired = participant2.desiredOccupations().stream()
                .filter(participant1.occupation()::contains)
                .count();

        long uniqueTraits = participant2.occupation().stream()
                .filter(trait -> !participant1.desiredOccupations().contains(trait))
                .count();

        long perfectMatch = participant1.occupation().stream()
                .filter(trait -> participant2.desiredOccupations().contains(trait) &&
                        participant1.desiredOccupations().contains(trait) &&
                        participant2.occupation().contains(trait))
                .count();

        double score = 2 * (commonDesired + reverseCommonDesired) +
                0.5 * uniqueTraits +
                5 * perfectMatch;

        if (commonDesired == 0 && reverseCommonDesired == 0) {
            score -= 3;
        }

        return score;
    }
}
