package output;

import models.Pair;
import models.Participant;

import java.util.List;
import java.util.Map;

public interface OutputPresenter {
    void present(Map<Participant, List<Pair>> matches);
}
