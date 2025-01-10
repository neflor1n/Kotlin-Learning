package org.example.LearnKotlin

import java.util.Scanner

fun main() {

    println("Conditions")
    val scan = Scanner(System.`in`);
    print("Enter the first number: ")
    val x = scan.nextInt()
    print("Enter the second number: ")
    val y = scan.nextInt()
    if (x > y) {
        println("$x is greater than $y")

    } else if (x == y){
        println("$x is equal to $y")
    }
    else {
        println("$x is less than $y")
    }
}