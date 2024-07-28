public class Task {
    private String description;
    private String startTime;
    private String endTime;
    private Priority priority;
    private List<Task> subTasks;

    public Task(String description, String startTime, String endTime, Priority priority) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.subTasks = new ArrayList<>();
    }

    public boolean conflictsWith(Task task) {
        // Implement conflict checking logic
        return false;
    }

    public void addSubTask(Task subTask) {
        subTasks.add(subTask);
    }

    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return startTime;
    }

    // Other getter methods
}

public class CompletedTaskDecorator extends Task {
    private Task task;
    private boolean isCompleted;

    public CompletedTaskDecorator(Task task) {
        super(task.getDescription(), task.getStartTime(), task.getEndTime(), task.getPriority());
        this.task = task;
        this.isCompleted = false;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
