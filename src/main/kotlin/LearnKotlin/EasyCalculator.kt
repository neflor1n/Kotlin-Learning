package org.example.LearnKotlin
import java.util.Scanner;
fun main() {
    println("Hello!")
    val scan = Scanner(System.`in`)

    print("1. +\n" +
            "2. -\n" +
            "3. *\n" +
            "4. /\n" +
            "5. %\n" +
            "This is a easy calculator. Choose the operator: ")

    val operator = scan.nextLine()

    print("Enter the first number: ")
    val firstNumber = readLine()!!.toDouble()
    print("Enter the second number: ")
    val secondNumber = readLine()!!.toDouble()
    when (operator) {
        "+" -> println("Result: ${firstNumber + secondNumber}")
        "-" -> println("Result: ${firstNumber - secondNumber}")
        "*" -> println("Result: ${firstNumber * secondNumber}")
        "/" -> {
            if (secondNumber != 0.0) {
                println("Result: ${firstNumber / secondNumber}")
            } else {
                println("Error: Division by zero!")
            }
        }
        "%" -> {
            if (secondNumber != 0.0) {
                println("Result: ${firstNumber % secondNumber}")
            } else {
                println("Error: Division by zero!")
            }
        }
        else -> println("Invalid operator!")

    }

}