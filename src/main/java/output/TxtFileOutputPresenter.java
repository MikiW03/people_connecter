package output;

import models.Pair;
import models.Participant;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TxtFileOutputPresenter extends AbstractOutputPresenter {
    private final String filePath;

    public TxtFileOutputPresenter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void present(Map<Participant, List<Pair>> matches) {
        if (matches.isEmpty()) return;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (var entry : sortMatches(matches)) {
                Participant participant = entry.getKey();
                List<Pair> pairs = entry.getValue();
                writer.write(formatMatchLine(participant, pairs));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("File saving error: " + e.getMessage());
        }
    }
}
