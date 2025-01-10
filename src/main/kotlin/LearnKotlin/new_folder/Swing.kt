package org.example.LearnKotlin.new_folder

import java.awt.Color
import java.awt.Font
import javax.swing.*
import javax.swing.table.DefaultTableModel
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import javax.management.relation.Role

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
        openLoginFrame()
        frame.dispose()
    }

    frame.isVisible = true
}

fun updateLabelText(label: JLabel, newText: String) {
    label.text = newText
}

fun openLoginFrame() {
    val frame = JFrame("Login").apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(800, 800)
        layout = null
        setLocationRelativeTo(null)
    }

    val titleLabel = JLabel("Login").apply {
        font = font.deriveFont(Font.BOLD, 24f)
        setBounds(350, 20, 600, 30)
    }

    val usernameLabel = JLabel("Username:").apply {
        font = font.deriveFont(Font.BOLD, 18f)
        setBounds(230, 200, 600, 30)
    }

    val passwordLabel = JLabel("Password:").apply {
        font = font.deriveFont(Font.BOLD, 18f)
        setBounds(230, 250, 600, 30)
    }

    val usernameField = JTextField("").apply {
        font = font.deriveFont(Font.BOLD, 16f)
        setBounds(350, 200, 200, 40)
    }

    val passwordField = JPasswordField("").apply {
        font = font.deriveFont(Font.BOLD, 16f)
        setBounds(350, 250, 200, 40)
    }

    val loginButton = JButton("Login").apply {
        setBounds(300, 300, 200, 40)
    }
    var text2 = JLabel().apply {
        text = ""
        font = font.deriveFont(Font.BOLD, 24f)
        setBounds(100, 500, 700, 30)
        isVisible = false
        foreground = Color.RED
    }

    loginButton.addActionListener {
        if (usernameField.text.isNotEmpty() && passwordField.text.isNotEmpty()) {
            val username = usernameField.text
            val password = passwordField.text
            val url = "jdbc:mysql://localhost:3306/KotlinUser"
            val dbUser = "KotlinUser"
            val dbPassword = "12345"
            var connection: Connection? = null

            try {
                connection = DriverManager.getConnection(url, dbUser, dbPassword)
                val sql = "SELECT * FROM users WHERE username = ?"
                val preparedStatement = connection.prepareStatement(sql)
                preparedStatement.setString(1, username)

                val resultSet = preparedStatement.executeQuery()

                if (resultSet.next()) {
                    val storedPassword = resultSet.getString("Pass")

                    if (storedPassword == password) {
                        println("Login successful!")

                        val role = getRoleFromDatabase(username)
                        if (roleChecker(usernameField, passwordField, role)) {
                            println("User has admin privileges.")
                            chooseThePage(usernameField, passwordField)
                        } else {
                            println("User doesn't have admin privileges.")
                            openUserFrame(usernameField, passwordField)

                        }

                        frame.dispose()
                    } else {
                        println("Incorrect username or password!")
                        text2.apply {
                            text = "Incorrect username or password!"
                            isVisible = true
                        }
                    }
                } else {
                    println("User not found!")
                    text2.apply {
                        text = "User not found!"
                        isVisible = true
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                connection?.close()
            }
        } else {
            println("Username or password cannot be empty!")
        }
    }
    frame.add(text2)
    frame.add(titleLabel)
    frame.add(usernameLabel)
    frame.add(passwordLabel)
    frame.add(usernameField)
    frame.add(passwordField)
    frame.add(loginButton)
    frame.isVisible = true
}




fun chooseThePage(usernameField: JTextField, passwordField: JPasswordField) {
    val frame = JFrame("Choose a Page").apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(500, 500)
        isVisible = true
        layout = null
        setLocationRelativeTo(null)
    }



    val button1 = JButton("Kinokava").apply {
        setBounds(150, 150, 200, 40)

    }

    val button2 = JButton("Users").apply {
        setBounds(150, 250, 200, 40)
    }

    button1.addActionListener {
        openUserFrame(usernameField, passwordField)
        frame.dispose()
    }
    button2.addActionListener {
        openDatabaseFrame()
        frame.dispose()
    }

    frame.add(button1)
    frame.add(button2)

}

fun openUserFrame(usernameField: JTextField, passwordField: JPasswordField) {
    val frame = JFrame("KotlinUser").apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        layout = null
        isVisible = true
        setSize(1000, 1000)
        setLocationRelativeTo(null)
    }
    val role = getRoleFromDatabase(usernameField.text)

    val titlelabel = JLabel().apply {
        text = ""
        setBounds(350, 20, 400, 20)
        font = font.deriveFont(Font.BOLD, 24f)
        if (roleChecker(usernameField, passwordField, role)) {
            text = "Hello, Admin!"
        } else {
            text = "Hello, User!"
        }
    }


    val tableModel = DefaultTableModel()
    val table = JTable(tableModel).apply {
        setBounds(200, 200, 500, 300)
        setSize(200, 500)
        loadKinokava(tableModel)
    }

    frame.add(JScrollPane(table).apply { setBounds(50, 600, 900, 300) })

    if (roleChecker(usernameField, passwordField, role)) {
        val deleteButton = JButton("Delete Film").apply {
            setBounds(50, 200, 200, 40)
            font = font.deriveFont(Font.BOLD, 14f)
        }

        val textfieldTitle = JTextField().apply {
            setBounds(150, 250, 200, 40)
            font = font.deriveFont(Font.PLAIN, 14f)
            placeholder("Enter film title")
        }

        val textfieldDescription = JTextField().apply {
            setBounds(150, 300, 200, 40)
            font = font.deriveFont(Font.PLAIN, 14f)
            placeholder("Enter film description")
        }

        val yearLabel = JLabel("Year:").apply {
            font = font.deriveFont(Font.BOLD, 18f)
            setBounds(50, 350, 100, 30)
        }

        val years = JSpinner(SpinnerNumberModel(2024, 1900, 2100, 1)).apply {
            setBounds(150, 350, 200, 40)
            font = font.deriveFont(Font.PLAIN, 14f)
        }

        val sessionDateLabel = JLabel("Session Date:").apply {
            font = font.deriveFont(Font.BOLD, 18f)
            setBounds(50, 400, 200, 30)
        }

        val sessionDate = JSpinner(SpinnerDateModel()).apply {
            setBounds(150, 400, 200, 40)
            font = font.deriveFont(Font.PLAIN, 14f)
        }

        val addButton = JButton("Add film").apply {
            setBounds(150, 450, 200, 40)
        }


        deleteButton.addActionListener {
            deleteFilm(tableModel, table, textfieldTitle, textfieldDescription, years, sessionDate)
        }
        addButton.addActionListener {
            addKinokava(tableModel, table, textfieldTitle, textfieldDescription, years, sessionDate)

        }

        frame.add(addButton)
        frame.add(deleteButton)
        frame.add(textfieldTitle)
        frame.add(textfieldDescription)
        frame.add(yearLabel)
        frame.add(years)
        frame.add(sessionDateLabel)
        frame.add(sessionDate)
    } else {
        
    }

    frame.add(titlelabel)
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
        viewDataInFields(table, textFieldUsername, textFieldPassword, textFieldRole)
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


fun viewDataInFields(table: JTable, textFieldUsername: JTextField, textFieldPassword: JTextField, textFieldRole: JTextField) {

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

            val sql = "DELETE FROM users WHERE Id = ?"
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


fun roleChecker(usernameField: JTextField, passwordField: JTextField, role: String): Boolean {
    return role.equals("Admin", ignoreCase = true)
}


fun getRoleFromDatabase(username: String): String {
    val url = "jdbc:mysql://localhost:3306/KotlinUser"
    val dbUser = "KotlinUser"
    val dbPassword = "12345"
    var connection: Connection? = null
    var role = ""

    try {
        connection = DriverManager.getConnection(url, dbUser, dbPassword)
        val sql = "SELECT Role FROM users WHERE Username = ?"
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, username)

        val resultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            role = resultSet.getString("Role")
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    } finally {
        connection?.close()
    }

    return role
}


