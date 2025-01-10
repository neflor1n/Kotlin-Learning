package practice.LearnKotlin

import practice.LearnKotlin.firstLesson;

fun main() {
    println("Learn Kotlin")
    val lesson = firstLesson()
    lesson.print()

    // Если написать переменную как:
    // var name : String
    // name = "Kotlin"
    // println(name)
    // Ошибкой не будет, но вот если написать...
    // var name
    // name = "Kotlin"
    // println(name)
    // Это считается ошибкой


    // Так же считается ошибкой если напишем
    // val name = "John"
    // name = "Robert"
    // println(name)

    // Но если мы напишем не val, а var
    var name = "John"
    name = "Robert"
    println(name)
    // То ошибкой не будет, так как с var ключевым словом, могут быть изменены/модифицированы, в то время как val переменные не изменяемые


    val firstName = "John"
    val lastName = "Doe"
    val fullName = "$firstName $lastName"
    println(fullName)


    // В котлине тип переменной определяется ее значением

    // например
    val myNum = 5             // Int
    val myDoubleNum = 5.99    // Double
    val myLetter = 'D'        // Char
    val myBoolean = true      // Boolean
    val myText = "Hello"      // String

    // или

    val myNum2: Int = 5
    val myDoubleNum2: Double = 5.99
    val myLetter2: Char = 'D'
    val myBoolean2: Boolean = true
    val myText2: String = "Hello"



    // Числа
    // Целые типы хранят целые числа, положительные или отрицательные (Например: 123 или -456), без десятичных знаков.
    // Допустимые типы: Byte, Short, Int и Long

    // Типы с плавующей запятой представляют числа с дробной частью, содержащие один или несколько десятичных знаков.
    // Существует два типа: Float и Double

    // Byte
    // Хранит целые числа от -128 до 127
    val myByte: Byte = 100;
    println(myByte) // 100

    // Short
    // Хранит целые числа от -32768 до 32767
    val myShort: Short = 5000;
    println(myShort) // 5000

    // Int
    // Хранит целые числа от -2147483648 до 2147483647
    val myInt: Int = 100000;
    println(myInt) // 100000

    // Long
    // Хранит целые числа от -9223372036854775808 до 9223372036854775807
    val myLong: Long = 15000000000L; // L при желании!
    println(myLong) // 15000000000


    // Double и Float

    //Точность значения с плавающей точкой указывает, сколько цифр после запятой может иметь значение.
    // Точность составляет Float всего шесть или семь десятичных цифр, в то время как Double переменные имеют точность около 15 цифр.
    // Поэтому его безопаснее использовать Double для большинства вычислений.




    // Число с плавающей точкой также может быть научным числом с буквой «e» или «E» для обозначения степени числа 10:
    val myF : Float = 35E3F
    val myD : Double = 12E4
    println(myF) // 35000.0 3 нуля до запятой
    println(myD) // 120000.0 4 нуля до запятой





}

