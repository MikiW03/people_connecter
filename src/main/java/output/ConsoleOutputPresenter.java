package output;

import models.Pair;
import models.Participant;

import java.util.List;
import java.util.Map;

public class ConsoleOutputPresenter extends AbstractOutputPresenter {

    @Override
    public void present(Map<Participant, List<Pair>> matches) {
        if (matches.isEmpty()) return;

        System.out.println("\nRecommended connections:");

        sortMatches(matches).forEach(entry -> {
            Participant participant = entry.getKey();
            List<Pair> pairs = entry.getValue();
            System.out.println(formatMatchLine(participant, pairs));
        });
    }
}
