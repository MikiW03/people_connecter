package input;

import models.Participant;
import java.util.List;
public interface InputParser {
    List<Participant> parse(String filePath);
}
