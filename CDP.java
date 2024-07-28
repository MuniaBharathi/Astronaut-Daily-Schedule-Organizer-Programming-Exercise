public class ScheduleManager {
    private static ScheduleManager instance;
    private List<Task> tasks;

    private ScheduleManager() {
        tasks = new ArrayList<>();
    }

    public static ScheduleManager getInstance() {
        if (instance == null) {
            instance = new ScheduleManager();
        }
        return instance;
    }

    public void addTask(Task task) throws TaskConflictException {
        for (Task t : tasks) {
            if (t.conflictsWith(task)) {
                throw new TaskConflictException("Task conflicts with existing task: " + t.getDescription());
            }
        }
        tasks.add(task);
        // Notify observers about the new task
    }

    public void removeTask(String description) {
        tasks.removeIf(t -> t.getDescription().equals(description));
        // Notify observers about the removal
    }

    public List<Task> viewTasks() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getStartTime))
                .collect(Collectors.toList());
    }

    // Other methods like editTask, markTaskAsCompleted, etc.
}

public class TaskFactory {
    public static Task createTask(String description, String startTime, String endTime, String priority) throws InvalidTimeException {
        // Validate time format and create Task
        if (!isValidTime(startTime) || !isValidTime(endTime)) {
            throw new InvalidTimeException("Invalid time format");
        }
        return new Task(description, startTime, endTime, Priority.valueOf(priority.toUpperCase()));
    }

    private static boolean isValidTime(String time) {
        // Implement time validation logic
        return true;
    }
}

