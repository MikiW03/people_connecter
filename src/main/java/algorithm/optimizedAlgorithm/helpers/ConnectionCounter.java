package algorithm.optimizedAlgorithm.helpers;

import models.Pair;
import models.Participant;

import java.util.Map;

public class ConnectionCounter {

    public boolean canAddPair(Pair pair, Map<Participant, Integer> connectionCount, int maxConnections) {
        Participant p1 = pair.participant1();
        Participant p2 = pair.participant2();

        boolean canAdd = connectionCount.getOrDefault(p1, 0) < maxConnections &&
                connectionCount.getOrDefault(p2, 0) < maxConnections;

        if (canAdd) {
            connectionCount.put(p1, connectionCount.getOrDefault(p1, 0) + 1);
            connectionCount.put(p2, connectionCount.getOrDefault(p2, 0) + 1);
        }

        return canAdd;
    }
}
