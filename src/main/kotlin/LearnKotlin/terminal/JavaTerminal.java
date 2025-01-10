package LearnKotlin.terminal;
import java.util.*;
import java.io.*;
import java.lang.*;

public class JavaTerminal {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Java Terminal");
        System.out.println("(C) Java terminal beta test");

        String currentDir = System.getProperty("user.dir");

        while (true) {
            System.out.print(currentDir + "> ");
            String task = scan.nextLine();

            String[] parts = task.split(" ", 2);
            String command = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "ls":
                    showDirectoryContents(currentDir);
                    break;
                case "cd":
                    if (!argument.isEmpty()) {
                        currentDir = changeDirectory(currentDir, argument);

                    } else {
                        System.out.println("Usage: cd <directory>");
                    }
                    break;
                case "rm":
                    if (!argument.isEmpty()) {
                        removeFileOrDirectory(currentDir, argument);
                    } else {
                        System.out.println("Usage: rm <file_or_directory>");
                    }
                    break;

                case "exit":
                    System.out.println("Exiting Java Terminal...");
                    scan.close();
                    break;

                default:
                    System.out.println("Unknown task: " + task);
            }
        }
    }


    private static void showDirectoryContents(String currentDir) {
        File directory = new File(currentDir);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("[DIR] " + file.getName());
                } else {
                    System.out.println("[FILE] " + file.getName());
                }
            }
        } else {
            System.out.println("Failed to list directory contents.");
        }
    }
    private static String changeDirectory(String currentDir, String newDir) {
        File targetDir;

        if (newDir.equals("..")) {
            targetDir = new File(currentDir).getParentFile();
            if (targetDir != null) {
                return targetDir.getAbsolutePath();
            } else {
                System.out.println("Already at the root directory.");
                return currentDir;
            }
        }

        if (newDir.startsWith("/")) {
            targetDir = new File(newDir);
        } else {
            targetDir = new File(currentDir, newDir);

        }


        if (targetDir.exists() && targetDir.isDirectory()) {
            return targetDir.getAbsolutePath();
        } else {
            System.out.println("cd: No such directory: " + newDir);
            return currentDir;
        }

    }

    private static void removeFileOrDirectory(String currentDir, String target) {
        File targetFile;

        if (target.startsWith("/")) {
            targetFile = new File(target);
        }
        else{
            targetFile = new File(currentDir, target);
        }

        if (!targetFile.exists()) {
            System.out.println("rm: No such file or directory: " + target);
            return;
        }

        if (targetFile.isDirectory()) {
            boolean success = deleteDirectoryRecursively(targetFile);
            if (success) {
                System.out.println("Directory removed: " + target);
            } else {
                System.out.println("rm: Failed to remove directory: " + target);
            }

        } else {
            if (targetFile.delete()) {
                System.out.println("File removed: " + target);
            } else {
                System.out.println("rm: Failed to remove file: " + target);
            }
        }
    }


    private static boolean deleteDirectoryRecursively(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if(!deleteDirectoryRecursively(file)) {
                        return false;
                    }
                } else {
                    if (!file.delete()) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }
}