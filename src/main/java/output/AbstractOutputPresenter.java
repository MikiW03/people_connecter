package output;

import models.Pair;
import models.Participant;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractOutputPresenter implements OutputPresenter {

    protected List<Map.Entry<Participant, List<Pair>>> sortMatches(Map<Participant, List<Pair>> matches) {
        return matches.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(Participant::id)))
                .collect(Collectors.toList());
    }

    protected String formatMatchLine(Participant participant, List<Pair> pairs) {
        StringBuilder line = new StringBuilder("Participant " + participant.id() + " -> ");
        pairs.forEach(pair -> {
            Participant match = (pair.participant1().equals(participant)) ? pair.participant2() : pair.participant1();
            line.append(match.id()).append(" ");
        });
        return line.toString().trim();
    }
}
