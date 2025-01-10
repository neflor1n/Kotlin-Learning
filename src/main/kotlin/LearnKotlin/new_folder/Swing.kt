package org.example.LearnKotlin.new_folder

import java.awt.Font
import javax.swing.*
import javax.swing.table.DefaultTableModel
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

fun main() {
    println("Swing with Kotlin and Java")

    val frame = JFrame("Swing with Kotlin and Java").apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(500, 500)
        layout = null
        setLocationRelativeTo(null)
    }

    val openNewFrameButton = JButton("Open New Frame").apply {
        setBounds(150, 150, 180, 40)
    }

    val button = JButton("Click me").apply {
        setBounds(150, 100, 100, 40)
    }

    val label = JLabel("Hello, Kotlin Swing").apply {
        setBounds(140, 50, 200, 50)
        font = font.deriveFont(Font.BOLD, 18f)
    }

    frame.add(button)
    frame.add(label)
    frame.add(openNewFrameButton)

    button.addActionListener {
        updateLabelText(label, "Button Clicked!")
    }

    openNewFrameButton.addActionListener {
        openDatabaseFrame()
    }

    frame.isVisible = true
}

fun updateLabelText(label: JLabel, newText: String) {
    label.text = newText
}

fun openDatabaseFrame() {
    val frame2 = JFrame("Database Swing").apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(800, 800)
        layout = null
        setLocationRelativeTo(null)
    }

    val titleLabel = JLabel("Database with Swing").apply {
        setBounds(50, 20, 600, 30)
        font = font.deriveFont(Font.BOLD, 24f)
    }

    val checkDataButton = JButton("Check Data").apply {
        setBounds(600, 20, 200, 50)
    }

    val textFieldUsername = JTextField("Username").apply {
        font = font.deriveFont(Font.BOLD, 14f)
        setBounds(350, 100, 200, 50)
    }

    val textFieldPassword = JTextField("Password").apply {
        font = font.deriveFont(Font.BOLD, 14f)
        setBounds(350, 150, 200, 50)
    }

    val textFieldRole = JTextField("Role").apply {
        font = font.deriveFont(Font.BOLD, 14f)
        setBounds(350, 200, 200, 50)
    }


    val labelUsername = JLabel("Username:").apply {
        font = font.deriveFont(Font.BOLD, 18f)
        setBounds(250, 100, 200, 50)
    }

    val labelPassword = JLabel("Password:").apply {
        font = font.deriveFont(Font.BOLD, 18f)
        setBounds(250, 150, 200, 50)
    }

    val labelRole = JLabel("Role:").apply {
        font = font.deriveFont(Font.BOLD, 18f)
        setBounds(250, 200, 200, 50)
    }


    val updateButton = JButton("Update data").apply {
        setBounds(350, 300, 200, 50)
        font = font.deriveFont(Font.BOLD, 14f)
    }

    val deleteButton = JButton("Delete data").apply {
        setBounds(350, 350, 200, 50)
        font = font.deriveFont(Font.BOLD, 14f)
    }

    val addButton = JButton("Add data").apply {
        setBounds(350, 250, 200, 50)
        font = font.deriveFont(Font.BOLD, 14f)
    }
    val tableModel = DefaultTableModel()
    val table = JTable(tableModel).apply {
        setBounds(200, 200, 500, 300)
        setSize(200, 500)
    }
    checkDataButton.addActionListener {
        loadDataToTable(tableModel)
    }

    table.selectionModel.addListSelectionListener {
        ViewDataInFields(table, textFieldUsername, textFieldPassword, textFieldRole)
    }

    updateButton.addActionListener {
        updateDataToTable(tableModel, table, textFieldUsername, textFieldPassword, textFieldRole)
    }


    deleteButton.addActionListener {
        deleteDataToTable(tableModel, table, textFieldUsername, textFieldPassword, textFieldRole)
    }

    addButton.addActionListener {
        addData(tableModel, table, textFieldUsername, textFieldPassword, textFieldRole, titleLabel)
    }

    frame2.add(labelUsername)
    frame2.add(labelPassword)
    frame2.add(labelRole)
    frame2.add(titleLabel)
    frame2.add(checkDataButton)
    frame2.add(JScrollPane(table).apply { setBounds(50, 400, 700, 300) })
    frame2.add(textFieldUsername)
    frame2.add(textFieldPassword)
    frame2.add(textFieldRole)
    frame2.add(updateButton)
    frame2.add(deleteButton)
    frame2.add(addButton)
    frame2.isVisible = true
}

fun loadDataToTable(tableModel: DefaultTableModel) {
    val url = "jdbc:mysql://localhost:3306/KotlinUser"
    val user = "KotlinUser"
    val password = "12345"

    var connection: Connection? = null
    try {
        connection = DriverManager.getConnection(url, user, password)
        println("Connected to database")

        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM users")

        // Устанавливаем колонки в таблице
        tableModel.setColumnIdentifiers(arrayOf("ID", "Username", "Password", "Role"))

        // Очищаем предыдущие данные (если есть)
        tableModel.setRowCount(0)

        // Заполняем таблицу данными из базы
        while (resultSet.next()) {
            tableModel.addRow(
                arrayOf(
                    resultSet.getInt("Id"),
                    resultSet.getString("Username"),
                    resultSet.getString("Pass"),
                    resultSet.getString("Role")
                )
            )
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    } finally {
        connection?.close()
    }
}


fun ViewDataInFields(table: JTable, textFieldUsername: JTextField, textFieldPassword: JTextField, textFieldRole: JTextField) {

    val selectedRow = table.selectedRow

    if (selectedRow != -1) {
        val username = table.getValueAt(selectedRow, 1).toString()
        val password = table.getValueAt(selectedRow, 2).toString()
        val role = table.getValueAt(selectedRow, 3).toString()

        textFieldUsername.text = username
        textFieldPassword.text = password
        textFieldRole.text = role
    } else {
        println("Строка не выбрана.")
    }
}

fun updateDataToTable(tableModel: DefaultTableModel, table: JTable, textFieldUsername: JTextField, textFieldPassword: JTextField, textFieldRole: JTextField) {
    val selectedRow = table.selectedRow

    if (selectedRow != -1) {

        val newUsername = textFieldUsername.text
        val newPassword = textFieldPassword.text
        val newRole = textFieldRole.text


        val userId = table.getValueAt(selectedRow, 0).toString()


        val url = "jdbc:mysql://localhost:3306/KotlinUser"
        val user = "KotlinUser"
        val password = "12345"
        var connection: Connection? = null

        try {
            connection = DriverManager.getConnection(url, user, password)
            println("Connected to database")


            val sql = "UPDATE users SET Username = ?, Pass = ?, Role = ? WHERE Id = ?"
            val preparedStatement = connection.prepareStatement(sql)

            preparedStatement.setString(1, newUsername)
            preparedStatement.setString(2, newPassword)
            preparedStatement.setString(3, newRole)
            preparedStatement.setInt(4, userId.toInt())


            val rowsAffected = preparedStatement.executeUpdate()

            if (rowsAffected > 0) {
                println("User data updated successfully")
                loadDataToTable(tableModel)
            } else {
                println("No rows affected. Check the user ID.")
            }

            loadDataToTable(tableModel)
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            connection?.close()
        }
    } else {
        println("Строка не выбрана.")
    }
}


fun deleteDataToTable(tableModel: DefaultTableModel, table: JTable, textFieldUsername: JTextField, textFieldPassword: JTextField, textFieldRole: JTextField) {
    val selectedRow = table.selectedRow

    if (selectedRow != -1) {
        val userId = table.getValueAt(selectedRow, 0).toString()

        val url = "jdbc:mysql://localhost:3306/KotlinUser"
        val user = "KotlinUser"
        val password = "12345"
        var connection: Connection? = null

        try {
            connection = DriverManager.getConnection(url, user, password)
            println("Connected to database")

            val sql = "DELETE FROM users WHERE ID = ?"
            val preparedStatement = connection.prepareStatement(sql)

            preparedStatement.setString(1, userId)

            val rowsAffected = preparedStatement.executeUpdate()

            if (rowsAffected > 0) {
                println("User data deleted successfully")
                loadDataToTable(tableModel)
            } else {
                println("No rows affected. Check the user ID.")
            }

        } catch (e: SQLException) {
            e.printStackTrace()

        } finally {
            connection?.close()
        }

    } else {
        println("Строка не выбрана.")
    }
}

fun addData(tableModel: DefaultTableModel, table: JTable, textFieldUsername: JTextField, textFieldPassword: JTextField, textFieldRole: JTextField, titleLabel: JLabel) {
    val url = "jdbc:mysql://localhost:3306/KotlinUser"
    val user = "KotlinUser"
    val password2 = "12345"
    var connection: Connection? = null


    if (textFieldUsername.text.isNotEmpty() && textFieldPassword.text.isNotEmpty() && textFieldRole.text.isNotEmpty()) {
        if (textFieldRole.text == "User" || textFieldRole.text == "Admin" || textFieldRole.text == "user" || textFieldRole.text == "admin") {
            if (textFieldUsername.text != "Username" && textFieldPassword.text != "Password" && textFieldRole.text != "Role") {
                try {
                    val username = textFieldUsername.text
                    val password = textFieldPassword.text
                    val role = textFieldRole.text

                    connection = DriverManager.getConnection(url, user, password2)


                    val sql = "INSERT INTO users (Username, Pass, Role) VALUES (?, ?, ?)"
                    val preparedStatement = connection.prepareStatement(sql)
                    preparedStatement.setString(1, username)
                    preparedStatement.setString(2, password)
                    preparedStatement.setString(3, role)

                    val rowsAffected = preparedStatement.executeUpdate()

                    if (rowsAffected > 0) {
                        println("Data added successfully")

                        loadDataToTable(tableModel)
                        val text: String = "Database with Swing"
                        titleLabel.text = text
                    } else {
                        println("No data added.")
                        titleLabel.text = "No data added."
                    }

                } catch (e: SQLException) {
                    e.printStackTrace()
                } finally {
                    connection?.close()
                }
            } else {
                println("Enter the data")
                titleLabel.text = "Please, enter some data"
            }
        } else {
            println("Role has to be User or Admin.")
            titleLabel.text = "Role has to be User or Admin."

        }

    } else {
        println("Please fill all fields.")
    }
}
