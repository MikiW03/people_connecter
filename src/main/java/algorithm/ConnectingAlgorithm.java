package algorithm;

import java.util.List;
import java.util.Map;

import models.Pair;
import models.Participant;

public interface ConnectingAlgorithm {
    Map<Participant, List<Pair>> getTopMatches(List<Participant> participants);
}
