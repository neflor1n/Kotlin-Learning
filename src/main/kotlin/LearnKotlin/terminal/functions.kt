package org.example.LearnKotlin.terminal
import java.io.*;

fun showDirectoryContents(currentDir: String) {
    val directory = File(currentDir)
    val files = directory.listFiles()

    if (files != null) {
        for (file in files) {
            if (file.isDirectory) {
                println("[DIR] ${file.name}")
            }
            else {
                println("[FILE] ${file.name}")
            }
        }
    } else {
        println("Failed to list directory contents")
    }
}


fun changeDirectory(currentDir: String, newDir: String) : String {
    var targetDir: File

    if (newDir == "..") {
        val parentDir = File(currentDir).parentFile
        return if (parentDir != null) {
            parentDir.absolutePath
        } else {
            println("Already at the root directory.")
            currentDir
        }
    }

    if (newDir.startsWith("/")) {
        targetDir = File(newDir)
    } else {
        targetDir = File(currentDir, newDir)
    }


    if (newDir.contains("..")) {
        targetDir = File(currentDir, newDir).canonicalFile
    }

    return if (targetDir.exists() && targetDir.isDirectory) {
        targetDir.absolutePath
    } else {
        println("cd: No such directory: $newDir")
        currentDir
    }
}


fun removeFileOrDirectory(currentDir: String, target: String) {
    val targetFile: File = if (target.startsWith("/")){ File(target) }
    else { File(currentDir, target) }

    if (!targetFile.exists()) {
        println("rm: No such file or directory: $target")
        return
    }


    if (targetFile.isDirectory) {
        val success = deleteDirectoryRecursively(targetFile)
        if (success) {
            println("Directory removed: $target")
        } else {
            println("rm: Failed to remove directory: $target")
        }
    } else {
        if (targetFile.delete()) {
            println("File removed: $target")
        } else {
            println("rm: Failed to remove file: $target")
        }
    }
}

fun deleteDirectoryRecursively(dir: File): Boolean {
    val files = dir.listFiles()
    if (files != null) {
        for (file in files) {
            if (file.isDirectory) {
                if (!deleteDirectoryRecursively(file)) {
                    return false
                }
            } else {
                if (!file.delete()) {
                    return false
                }
            }
        }
    }
    return dir.delete()
}



fun catFile(currentDir: String, fileName: String) {
    val targetFile = if (fileName.startsWith("/")) {
        File(fileName)
    } else {
        File(currentDir, fileName)
    }

    if (!targetFile.exists()) {
        println("cat: No such file: $fileName")
        return
    }

    if (targetFile.isDirectory) {
        println("cat: $fileName is a directory")
        return
    }

    try {
        targetFile.forEachLine { line ->
            println(line)
        }
    } catch (e: Exception) {
        println("cat: Failed to read file: $fileName")
    }
}

fun createFile(currentDir: String, fileName: String) {
    if (fileName.isEmpty()) {
        println("nano: File name cannot be empty")
        return
    }

    val targetFile = if (fileName.startsWith("/")) {
        File(fileName)
    } else {
        File(currentDir, fileName)
    }

    if (targetFile.exists()) {
        println("nano: File '$fileName' already exists")
        return
    }

    try {
        if (targetFile.createNewFile()) {
            println("File '$fileName' created successfully")
        }else {
            println("nano: Failed to create file '$fileName'")
        }
    }catch (e: Exception) {
        println("nano: Error creating file '$fileName': ${e.message}")
    }
}


fun writeFileContent(currentDir: String, fileName: String, content: String) {
    val targetFile = if (fileName.startsWith("/")) {
        File(fileName)
    } else {
        File(currentDir, fileName)
    }

    try {
        if (!targetFile.exists()) {
            println("writeFileContent: File '$fileName' does not exist, creating it...")
            targetFile.createNewFile() // Создание файла, если он отсутствует
        }

        targetFile.writeText(content) // Запись текста в файл
        println("nano: Written content to '$fileName'")
    } catch (e: Exception) {
        println("writeFileContent: Error writing to file '$fileName': ${e.message}")
    }
}


fun createDirectory(currentDir: String, dirName: String) {
    if (dirName.isEmpty()) {
        println("mkdir: Directory name cannot be empty")
        return
    }

    val targetDir = if (dirName.startsWith("/")) {
        File(dirName)
    } else {
        File(currentDir, dirName)
    }

    if (targetDir.exists()) {
        println("mkdir: Directory '$dirName' already exists")
        return
    }


    try {
        if (targetDir.mkdirs()) {
            println("Directory '$dirName' created successfully")
        } else {
            println("mkdir: Failed to create directory '$dirName'")
        }
    } catch (e: Exception) {
        println("mkdir: Error creating directory '$dirName': ${e.message}")
    }
}


fun showHelp() {
    println("Available commands:")
    println("  ls               - List directory contents")
    println("  cd <directory>   - Change current directory")
    println("  rm <target>      - Remove a file or directory")
    println("  cat <file>       - Display the contents of a file")
    println("  nano <file> [content] - Create or edit a file")
    println("  mkdir <dir_name> - Create a new directory")
    println("  mv <source> <target> - Move a file to a new directory")
    println("  exit             - Exit the terminal")
    println("  rename <old_name> <new_name> - Rename the file")
    println("  clear            - Clear the terminal")
    println("  help             - Show this help message")
}


fun moveFile (currentDir: String, sourceFile: String, targetDir: String) {
    val source = File(currentDir, sourceFile)
    val target = File(currentDir, targetDir)

    if (!source.exists()) {
        println("mv: No such file: $sourceFile")
        return
    }

    if (!target.exists() || !target.isDirectory) {
        println("mv: No such directory: $targetDir")
        return
    }

    val movedFile = File(target, source.name)

    try {
        if (source.renameTo(movedFile)) {
            println("mv: '$sourceFile' moved to '$targetDir'")
        } else {
            println("mv: Failed to move '$sourceFile' to '$targetDir'")
        }
    } catch (e: Exception) {
        println("mv: Error moving file: ${e.message}")
    }
}


fun clearTerminal() {
    repeat(100) {println ()}
}


fun renameFile(currentDir: String, fileName: String, newFileName: String) {
    val sourceFile = File(currentDir, fileName)
    val targetFile = File(currentDir, newFileName)

    if (!sourceFile.exists()) {
        println("rename: No such file: $fileName")
        return
    }

    if (sourceFile.isDirectory) {
        println("rename: '$fileName' is a directory, not a file")
        return
    }

    try {
        if (sourceFile.renameTo(targetFile)) {
            println("rename: File '$fileName' renamed to '$newFileName'")
        } else {
            println("rename: Failed to rename file '$fileName'")
        }
    } catch (e: Exception) {
        println("rename: Error renaming file: ${e.message}")
    }
}
