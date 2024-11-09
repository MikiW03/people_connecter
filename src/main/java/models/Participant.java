package models;

import java.util.List;

public record Participant(int id, List<String> occupation, List<String> desiredOccupations) {
}
