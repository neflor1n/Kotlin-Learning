package org.example.LearnKotlin.terminal
import java.util.*;
import java.io.*;


fun main() {

    val scan = Scanner(System.`in`);
    println("Kotlin Terminal")
    println("(C) Kotlin Terminal beta test")

    var currentDir : String = System.getProperty("user.dir")
    while (true) {
        print("$currentDir> ");
        var task = scan.nextLine();

        val parts: List<String> = task.split(" ", limit = 2)
        val command : String = parts[0]
        val argument: String = if (parts.size > 1) parts[1] else ""


        when (command) {
            "ls" -> showDirectoryContents(currentDir)
            "cd" -> {
                if (argument.isNotEmpty()) {
                    currentDir = changeDirectory(currentDir, argument)
                } else {
                    println("Usage: cd <directory>")
                }
            }
            "rm" -> {
                if (argument.isNotEmpty()) {
                    removeFileOrDirectory(currentDir, argument)
                } else {
                    println("Usage: rm <file_or_directory>")
                }
            }
            "cat" -> {
                if (argument.isNotEmpty()) {
                    catFile(currentDir, argument)
                } else {
                    println("Usage: cat <file>")
                }
            }
            "nano" -> {
                if (argument.isNotEmpty()) {
                    val parts = argument.split(" ", limit = 2)
                    val fileName = parts[0]
                    val content = if (parts.size > 1) parts[1] else null

                    if (content == null) {
                        // Если контента нет, только создаём файл
                        createFile(currentDir, fileName)
                    } else {

                        writeFileContent(currentDir, fileName, content)
                    }
                } else {
                    println("Usage: nano <file_name> [content]")
                }
            }
            "mkdir" -> {
                if (argument.isNotEmpty()) {
                    createDirectory(currentDir, argument)
                } else {
                    println("Usage: mkdir <directory_name>")
                }
            }
            "mv" -> {
                val moveParts = argument.split(" ", limit = 2)
                if (moveParts.size == 2) {
                    val sourceFile = moveParts[0]
                    val targetDir = moveParts[1]
                    moveFile(currentDir, sourceFile, targetDir)
                } else {
                    println("Usage: mv <source_file> <target_directory>")
                }
            }
            "clear" -> clearTerminal()
            "help" -> showHelp()
            "rename" -> {
                if (argument.isNotEmpty()) {
                    val parts = argument.split(" ", limit = 2)
                    if (parts.size == 2) {
                        val oldName = parts[0]
                        val newName = parts[1]
                        renameFile(currentDir, oldName, newName)
                    } else {
                        println("Usage: rename <old_name> <new_name>")
                    }
                } else {
                    println("Usage: rename <old_name> <new_name>")
                }
            }
            "exit" -> {
                println("Exiting Kotlin Terminal...")
                break
            }
            else -> println("Unknown command: $command")
        }


    }

}



