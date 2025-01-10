package org.example.LearnKotlin.DatabaseInKotlin

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement




fun showData(connection: Connection) {
    try {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM users")
        println()
        println("Table data:")
        while (resultSet.next()) {
            println(
                "ID: ${resultSet.getInt("Id")}, " +
                        "Name: ${resultSet.getString("Username")}, " +
                        "Password: ${resultSet.getString("Pass")}, " +
                        "Role: ${resultSet.getString("Role")}"
            )
        }
        println()
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun addData(connection: Connection) {
    try {
        println()
        print("Enter username: ")
        val username = readLine()!!
        print("Enter password: ")
        val password = readLine()!!
        print("Enter 0-1 (0 - user, 1 - admin): ")
        var role = readLine()!!.toString()
        if (role == "1") {
           role = "Admin"
        } else if (role == "0") {
            role = "User"
        } else {
            println("Incorrect number!")
        }

        val statement = connection.prepareStatement("Insert into users (Username, Pass, Role) values (?, ?, ?)")
        statement.setString(1, username)
        statement.setString(2, password)
        statement.setString(3, role)
        statement.executeUpdate()

        println("Data added successfully!")
        println()

    } catch (e: SQLException) {
        e.printStackTrace()
    }
}


fun deleteDate(connection: Connection) {
    try {
        print("Enter ID of data: ")
        val idx = readLine()!!.toInt()

        val statement = connection.prepareStatement("DELETE FROM users where Id = ?")
        statement.setInt(1, idx)
        val rowsDeleted = statement.executeUpdate()

        if (rowsDeleted > 0) {
            println("Successfully deleted data!")
        } else {
            println("No user found with specified ID!")

        }


    } catch (e: SQLException) {
        e.printStackTrace()
    }
}

fun updateData(connection: Connection) {
    try {
        println()
        showData(connection)
        print("Enter ID of data: ")
        var idx = readLine()!!.toInt()

        print("""
            |1. Username
            |2. Password
            |3. Role
            |Enter the line you want to change:
        """.trimIndent())
        val choose: String = readLine()!!.toString()

        if (choose == "1") {
            print("Enter the new username: ")
            val username = readLine()!!.toString()
            val statement = connection.prepareStatement("UPDATE users SET Username = ? WHERE Id = ?")
            statement.setString(1, username)
            statement.setInt(2, idx)
            statement.executeUpdate()
            println("Data updated successfully!")

        } else if (choose == "2") {
            print("Enter the new password: ")
            val password = readLine()!!.toString()
            val statement = connection.prepareStatement("UPDATE users SET Pass = ? WHERE Id = ?")
            statement.setString(1, password)
            statement.setInt(2, idx)
            statement.executeUpdate()
            println("Data updated successfully!")

        } else if (choose == "3") {
            print("Enter the new role: ")
            val role = readLine()!!.toString()

            if (role != "admin" || role != "user" || role != "Admin" || role != "User") {
                println("Incorrect role!")
            }

            val statement = connection.prepareStatement("Update users set Role = ? where Id = ?")
            statement.setString(1, role)
            statement.setInt(2, idx)
            statement.executeUpdate()
            println("Data updated successfully!")
        } else {
            println("Incorrect number!")
        }
        println()
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}