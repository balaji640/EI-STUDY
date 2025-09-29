package project;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// ----- Task Class -----
class Task {
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private String priority;
    private boolean completed;

    public Task(String description, LocalTime startTime, LocalTime endTime, String priority) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.completed = false;
    }

    public String getDescription() { return description; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public String getPriority() { return priority; }
    public boolean isCompleted() { return completed; }
    public void markCompleted() { this.completed = true; }

    @Override
    public String toString() {
        return startTime + " - " + endTime + ": " + description +
               " [" + priority + "]" + (completed ? " (Done)" : "");
    }
}

// ----- Factory Pattern -----
class TaskFactory {
    public static Task createTask(String desc, String start, String end, String priority) {
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTime = LocalTime.parse(start, fmt);
            LocalTime endTime = LocalTime.parse(end, fmt);
            if (endTime.isBefore(startTime)) {
                throw new IllegalArgumentException("End time must be after start time");
            }
            return new Task(desc, startTime, endTime, priority);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: Invalid time format. Use HH:mm");
        }
    }
}

// ----- Observer Pattern -----
interface Observer {
    void update(String msg);
}

class ConflictNotifier implements Observer {
    @Override
    public void update(String msg) {
        System.out.println("⚠ Notification: " + msg);
    }
}

// ----- Singleton Pattern -----
class ScheduleManager {
    private static ScheduleManager instance;
    private List<Task> tasks = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();

    private ScheduleManager() {}

    public static ScheduleManager getInstance() {
        if (instance == null) instance = new ScheduleManager();
        return instance;
    }

    public void addObserver(Observer o) { observers.add(o); }
    private void notifyObservers(String msg) {
        for (Observer o : observers) o.update(msg);
    }

    public void addTask(Task task) {
        for (Task t : tasks) {
            if (task.getStartTime().isBefore(t.getEndTime()) &&
                task.getEndTime().isAfter(t.getStartTime())) {
                notifyObservers("Task conflicts with existing task \"" + t.getDescription() + "\"");
                return;
            }
        }
        tasks.add(task);
        tasks.sort(Comparator.comparing(Task::getStartTime));
        System.out.println("Task added successfully. No conflicts.");
    }

    public void removeTask(String description) {
        boolean removed = tasks.removeIf(t -> t.getDescription().equalsIgnoreCase(description));
        if (removed) System.out.println("Task removed successfully.");
        else System.out.println("Error: Task not found.");
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled for the day.");
            return;
        }
        tasks.forEach(System.out::println);
    }

    public void markTaskDone(String description) {
        for (Task t : tasks) {
            if (t.getDescription().equalsIgnoreCase(description)) {
                t.markCompleted();
                System.out.println("Task marked as completed.");
                return;
            }
        }
        System.out.println("Error: Task not found.");
    }
}

// ----- Console App -----
public class astronut {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ScheduleManager manager = ScheduleManager.getInstance();
        manager.addObserver(new ConflictNotifier());

        while (true) {
            System.out.println("\n--- Astronaut Daily Schedule ---");
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. View Tasks");
            System.out.println("4. Mark Task Completed");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    System.out.print("Start time (HH:mm): ");
                    String st = sc.nextLine();
                    System.out.print("End time (HH:mm): ");
                    String et = sc.nextLine();
                    System.out.print("Priority (High/Medium/Low): ");
                    String pr = sc.nextLine();
                    try {
                        Task task = TaskFactory.createTask(desc, st, et, pr);
                        manager.addTask(task);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Enter task description to remove: ");
                    manager.removeTask(sc.nextLine());
                    break;
                case 3:
                    manager.viewTasks();
                    break;
                case 4:
                    System.out.print("Enter task description to mark done: ");
                    manager.markTaskDone(sc.nextLine());
                    break;
                case 5:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
