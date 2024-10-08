import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    private List<String> taskList;
    private static final String TASK_FILE = "tasks_data.txt";

    public TaskManager() {
        taskList = new ArrayList<>();
        loadTaskList();
    }

    private void loadTaskList() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TASK_FILE))) {
            String taskItem;
            while ((taskItem = reader.readLine()) != null) {
                taskList.add(taskItem);
            }
        } catch (IOException e) {
            System.out.println("No previous tasks found, creating a new list!");
        }
    }

    private void saveTaskList() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TASK_FILE))) {
            for (String task : taskList) {
                writer.write(task);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while saving tasks.");
        }
    }

    public void addTask(String task) {
        if (task != null && !task.trim().isEmpty()) {
            taskList.add(task);
            saveTaskList();
            System.out.println("Task added successfully: " + task);
        } else {
            System.out.println("Task cannot be blank.");
        }
    }

    public void displayTasks() {
        if (taskList.isEmpty()) {
            System.out.println("Task list is empty.");
        } else {
            for (int i = 0; i < taskList.size(); i++) {
                System.out.println((i + 1) + ". " + taskList.get(i));
            }
        }
    }

    public void modifyTask(int index, String updatedTask) {
        if (index >= 0 && index < taskList.size()) {
            taskList.set(index, updatedTask);
            saveTaskList();
            System.out.println("Task updated to: " + updatedTask);
        } else {
            System.out.println("Invalid task index provided.");
        }
    }

    public void removeTask(int index) {
        if (index >= 0 && index < taskList.size()) {
            String removedTask = taskList.remove(index);
            saveTaskList();
            System.out.println("Task removed: " + removedTask);
        } else {
            System.out.println("Invalid task index provided.");
        }
    }

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        String userCommand;

        while (true) {
            System.out.println("\nEnter a command (add, show, modify, remove, exit):");
            userCommand = scanner.nextLine();

            switch (userCommand.toLowerCase()) {
                case "add":
                    System.out.println("Enter the task description:");
                    String taskInput = scanner.nextLine();
                    taskManager.addTask(taskInput);
                    break;

                case "show":
                    taskManager.displayTasks();
                    break;

                case "modify":
                    System.out.println("Enter the task number to modify:");
                    int taskIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    System.out.println("Enter the updated task description:");
                    String newTaskInput = scanner.nextLine();
                    taskManager.modifyTask(taskIndex, newTaskInput);
                    break;

                case "remove":
                    System.out.println("Enter the task number to remove:");
                    int removeIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    taskManager.removeTask(removeIndex);
                    break;

                case "exit":
                    System.out.println("Exiting the Task Manager.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid command, please try again.");
            }
        }
    }
}
