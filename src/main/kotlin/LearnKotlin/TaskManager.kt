package org.example.LearnKotlin

fun main() {
    print("Hello! Enter your name: ")
    val name = readLine()!!
    println("Hello $name!")
    println("This is a task manager!")

    // Изменяемый список задач
    val tasks = mutableListOf("task1")

    while (true) {
        print(
            """
                
            1. Show tasks
            2. Add a task
            3. Remove a task
            4. Exit
            Choose an option: """.trimIndent()
        )

        val option = readLine()!!.toInt()

        when (option) {
            1 -> {
                println("Your tasks:")
                if (tasks.isEmpty()) {
                    println("No tasks available.")
                } else {
                    for ((index, task) in tasks.withIndex()) {
                        println("${index + 1}. $task")
                    }
                }
            }

            2 -> {
                print("Enter the task to add: ")
                val newTask = readLine()!!
                tasks.add(newTask) // Добавляем новую задачу в список
                println("Task added!")
            }

            3 -> {
                println("Enter the number of the task to remove: ")
                val taskNumber = readLine()!!.toInt()
                if (taskNumber in 1..tasks.size) {
                    tasks.removeAt(taskNumber - 1) // Удаляем задачу по индексу
                    println("Task removed!")
                } else {
                    println("Invalid task number!")
                }
            }

            4 -> {
                println("Goodbye, $name!")
                break
            }

            else -> println("Invalid option! Please try again.")
        }
    }
}
