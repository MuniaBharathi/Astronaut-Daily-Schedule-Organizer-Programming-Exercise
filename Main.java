import java.util.List;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ScheduleManager scheduleManager = ScheduleManager.getInstance();
        TaskFactory taskFactory = new TaskFactory();
        User user = new User("Astronaut");

        scheduleManager.addObserver(user);

        while (true) {
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. View Tasks");
            System.out.println("4. View Tasks by Priority");
            System.out.println("5. Mark Task as Completed");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter description:");
                    String description = scanner.nextLine();
                    System.out.println("Enter start time (HH:mm):");
                    String startTime = scanner.nextLine();
                    System.out.println("Enter end time (HH:mm):");
                    String endTime = scanner.nextLine();
                    System.out.println("Enter priority:");
                    String priority = scanner.nextLine();
                    try {
                        Task task = taskFactory.createTask(description, startTime, endTime, priority);
                        System.out.println(scheduleManager.addTask(task));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Enter task description to remove:");
                    String descToRemove = scanner.nextLine();
                    System.out.println(scheduleManager.removeTask(descToRemove));
                    break;
                case 3:
                    List<Task> tasks = scheduleManager.viewTasks();
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks scheduled for the day.");
                    } else {
                        tasks.forEach(System.out::println);
                    }
                    break;
                case 4:
                    System.out.println("Enter priority to view:");
                    String priorityToView = scanner.nextLine();
                    List<Task> tasksByPriority = scheduleManager.viewTasksByPriority(priorityToView);
                    if (tasksByPriority.isEmpty()) {
                        System.out.println("No tasks found with priority " + priorityToView + ".");
                    } else {
                        tasksByPriority.forEach(System.out::println);
                    }
                    break;
                case 5:
                    System.out.println("Enter task description to mark as completed:");
                    String descToComplete = scanner.nextLine();
                    System.out.println(scheduleManager.markTaskAsCompleted(descToComplete));
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
