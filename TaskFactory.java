import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class TaskFactory {
    public Task createTask(String description, String startTime, String endTime, String priority) throws IllegalArgumentException {
        try {
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);

            if (start.isAfter(end)) {
                throw new IllegalArgumentException("Start time must be before end time.");
            }

            return new Task(description, start, end, priority);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format.");
        }
    }
}
