public interface TaskObserver {
    void update(String message);
}

public class TaskConflictObserver implements TaskObserver {
    @Override
    public void update(String message) {
        System.out.println("Conflict Notification: " + message);
    }
}

public interface TaskCommand {
    void execute();
}

public class AddTaskCommand implements TaskCommand {
    private ScheduleManager manager;
    private Task task;

    public AddTaskCommand(ScheduleManager manager, Task task) {
        this.manager = manager;
        this.task = task;
    }

    @Override
    public void execute() {
        try {
            manager.addTask(task);
            System.out.println("Task added successfully.");
        } catch (TaskConflictException e) {
            System.out.println(e.getMessage());
        }
    }
}

public class RemoveTaskCommand implements TaskCommand {
    private ScheduleManager manager;
    private String description;

    public RemoveTaskCommand(ScheduleManager manager, String description) {
        this.manager = manager;
        this.description = description;
    }

    @Override
    public void execute() {
        manager.removeTask(description);
        System.out.println("Task removed successfully.");
    }
}
