package org.example.LearnKotlin.classes

class Car{
    var brand: String = ""
    var model: String = ""
    var year: Int = 0
}

class Languages {
    var name: String = ""
    var year: Int = 0
    var creator: String = ""

}


class Car2(var brand: String = "", var model: String = "", var year: Int = 0) {
    fun drive() {
        println("Wrooom")
    }

    fun speed(MaxSpeed: Int) {
        println("Max speed: $MaxSpeed")
    }

}


open class MyParantClass {
    val x: Int = 5
}

class MyChildClass : MyParantClass() {
    fun myFunction() {
        println(x)
    }
}


fun main(){
//    val c2 = Car2("Ford", "Mustang", 1969)
//    println(c2.brand)
//    println(c2.model)
//    println(c2.year)
//    c2.drive()
//    c2.speed(300)
//


//    val c1 = Car()
//    c1.brand = "Hellcat"
//    c1.model = "Kotlin"
//    c1.year = 2016
//
//    println(c1.brand)
//    println(c1.model)
//    println(c1.year)
//
//    val kotlin = Languages()
//    kotlin.name = "Kotlin"
//    kotlin.year = 2016
//    kotlin.creator = "JetBrains"
//    println("Language name: " + kotlin.name)
//    println("Language year: " + kotlin.year)
//    println("Language creator: " +kotlin.creator)
//
//
//    print(Languages())

    val myObj = MyChildClass()
    myObj.myFunction()

    if (myObj.x == 5) {
        println(true)
    } else {
        println(false)
    }


}