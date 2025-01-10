package org.example.LearnKotlin.DatabaseInKotlin

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement


fun main(){
    val url = "jdbc:mysql://localhost:3306/KotlinUser"
    val user = "KotlinUser"
    val password = "12345"

    var connection: Connection? = null

    try {
        connection = DriverManager.getConnection(url, user, password)
        println("Connected to database")

        val statement: Statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM users")
//        while (resultSet.next()) {
//            println("ID: ${resultSet.getInt("Id")}, Name: ${resultSet.getString("Username")} Password: ${resultSet.getString("Pass")}")
//
//        }
        while(true) {
            print(
                """
                |Hello, this is the database controller!
                |1. Show table data
                |2. Add data in table
                |3. Delete data from table
                |4. Update data in table
                |5. Exit
                |Choose the option: 
            """.trimMargin()
            )
            val resultNum: Int = readLine()!!.toInt()

            when (resultNum) {
                1 -> showData(connection)
                2 -> addData(connection)
                3 -> deleteDate(connection)
                4 -> updateData(connection)
                5 -> break
                else -> println("Invalid option, try again")
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    } finally {
        connection?.close()
    }
}

