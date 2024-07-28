// TaskObserver.java
public interface TaskObserver {
    void update(String message);
}

// TaskConflictObserver.java
public class TaskConflictObserver implements TaskObserver {
    @Override
    public void update(String message) {
        System.out.println("Conflict Notification: " + message);
    }
}

// ScheduleManager.java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleManager {
    private static ScheduleManager instance;
    private List<Task> tasks;
    private List<TaskObserver> observers;

    private ScheduleManager() {
        tasks = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public static ScheduleManager getInstance() {
        if (instance == null) {
            instance = new ScheduleManager();
        }
        return instance;
    }

    public void addObserver(TaskObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TaskObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String message) {
        for (TaskObserver observer : observers) {
            observer.update(message);
        }
    }

    public void addTask(Task task) throws TaskConflictException {
        for (Task t : tasks) {
            if (t.conflictsWith(task)) {
                notifyObservers("Task conflicts with existing task: " + t.getDescription());
                throw new TaskConflictException("Task conflicts with existing task: " + t.getDescription());
            }
        }
        tasks.add(task);
        notifyObservers("Task added: " + task.getDescription());
    }

    public void removeTask(String description) {
        tasks.removeIf(t -> t.getDescription().equals(description));
        notifyObservers("Task removed: " + description);
    }

    public List<Task> viewTasks() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getStartTime))
                .collect(Collectors.toList());
    }
}
