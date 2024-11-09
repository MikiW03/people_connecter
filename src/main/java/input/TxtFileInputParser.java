package input;

import models.Participant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TxtFileInputParser implements InputParser {

    @Override
    public List<Participant> parse(String filePath) {
        InputStream resourceStream = loadFile(filePath);
        if (resourceStream == null) {
            return new ArrayList<>();
        }

        return processFile(resourceStream);
    }

    private InputStream loadFile(String filePath) {
        InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (resourceStream == null) {
            System.err.printf("Error: File '%s' not found.\n", filePath);
        }
        return resourceStream;
    }

    private List<Participant> processFile(InputStream resourceStream) {
        List<Participant> participants = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                parseLine(line, lineNumber, participants);
            }
        } catch (IOException e) {
            System.err.printf("Error: Issue reading file. Details: %s\n", e.getMessage());
        }

        return participants;
    }

    private void parseLine(String line, int lineNumber, List<Participant> participants) {
        try {
            String[] parts = line.split("\t");

            if (validateLineFormat(parts, lineNumber)) {
                Participant participant = createParticipant(parts);
                participants.add(participant);
            }
        } catch (NumberFormatException e) {
            System.err.printf("Error: Incorrect id format on line %d. Skipping.\n", lineNumber);
        } catch (Exception e) {
            System.err.printf("Error: Unexpected issue on line %d. Details: %s\n", lineNumber, e.getMessage());
        }
    }

    private boolean validateLineFormat(String[] parts, int lineNumber) {
        if (parts.length < 3) {
            System.err.printf("Error: Invalid format on line %d. Expected three columns.\n", lineNumber);
            return false;
        }
        return true;
    }

    private Participant createParticipant(String[] parts) {
        int id = Integer.parseInt(parts[0]);
        List<String> occupation = Arrays.asList(parts[1].split(","));
        List<String> desiredOccupations = Arrays.asList(parts[2].split(","));
        return new Participant(id, occupation, desiredOccupations);
    }
}
