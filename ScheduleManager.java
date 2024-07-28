import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleManager {
    private static ScheduleManager instance;
    private List<Task> tasks;
    private List<Observer> observers;

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

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public String addTask(Task task) {
        for (Task t : tasks) {
            if (t.getStartTime().isBefore(task.getEndTime()) && task.getStartTime().isBefore(t.getEndTime())) {
                notifyObservers("Task conflict detected: " + task.getDescription() + " conflicts with " + t.getDescription());
                return "Error: Task conflicts with existing task \"" + t.getDescription() + "\".";
            }
        }
        tasks.add(task);
        notifyObservers("Task added: " + task.getDescription());
        return "Task added successfully. No conflicts.";
    }

    public String removeTask(String description) {
        Task taskToRemove = null;
        for (Task t : tasks) {
            if (t.getDescription().equalsIgnoreCase(description)) {
                taskToRemove = t;
                break;
            }
        }
        if (taskToRemove != null) {
            tasks.remove(taskToRemove);
            notifyObservers("Task removed: " + description);
            return "Task removed successfully.";
        } else {
            return "Error: Task not found.";
        }
    }

    public List<Task> viewTasks() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getStartTime))
                .collect(Collectors.toList());
    }

    public List<Task> viewTasksByPriority(String priority) {
        return tasks.stream()
                .filter(task -> task.getPriority().equalsIgnoreCase(priority))
                .sorted(Comparator.comparing(Task::getStartTime))
                .collect(Collectors.toList());
    }

    public String markTaskAsCompleted(String description) {
        for (Task t : tasks) {
            if (t.getDescription().equalsIgnoreCase(description)) {
                t.markAsCompleted();
                notifyObservers("Task completed: " + description);
                return "Task marked as completed.";
            }
        }
        return "Error: Task not found.";
    }
}
