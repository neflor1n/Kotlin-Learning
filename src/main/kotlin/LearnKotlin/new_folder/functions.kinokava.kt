package org.example.LearnKotlin.new_folder

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import javax.swing.JTable
import javax.swing.table.DefaultTableModel
import javax.swing.*
import java.awt.Color
import java.awt.Font
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import java.io.File
import java.text.SimpleDateFormat

fun loadKinokava(tableModel: DefaultTableModel) {
    val url = "jdbc:mysql://localhost:3306/KotlinUser"
    val user = "KotlinUser"
    val password = "12345"
    var connection: Connection? = null
    try {
        connection = DriverManager.getConnection(url, user, password)
        var statement = connection.createStatement()
        var resultSet = statement.executeQuery("SELECT * FROM kinokava")

        tableModel.setColumnIdentifiers(arrayOf("ID", "Title", "Description", "Release Year", "Session Date"))
        tableModel.setRowCount(0)

        while (resultSet.next()) {
            tableModel.addRow(
                arrayOf(
                    resultSet.getString("ID"),
                    resultSet.getString("Title"),
                    resultSet.getString("Description"),
                    resultSet.getString("ReleaseYear"),
                    resultSet.getString("SessionDate"),
                )
            )
        }

    } catch (e: SQLException) {
        e.printStackTrace()
    } finally {
        connection?.close()
    }
}

fun JTextField.placeholder(text: String) {
    this.foreground = Color.GRAY
    this.text = text
    this.addFocusListener(object : java.awt.event.FocusAdapter() {
        override fun focusGained(e: java.awt.event.FocusEvent) {
            if (this@placeholder.text == text) {
                this@placeholder.text = ""
                this@placeholder.foreground = Color.BLACK
            }
        }

        override fun focusLost(e: java.awt.event.FocusEvent) {
            if (this@placeholder.text.isEmpty()) {
                this@placeholder.text = text
                this@placeholder.foreground = Color.GRAY
            }
        }
    })
}

fun addKinokava(tableModel: DefaultTableModel, table: JTable, textfieldTitle: JTextField, textfieldDescription: JTextField,
                years: JSpinner, sessionDate: JSpinner) {
    val url = "jdbc:mysql://localhost:3306/KotlinUser"
    val user = "KotlinUser"
    val password2 = "12345"
    var connection: Connection? = null
    val error = JLabel("Enter the Date").apply {
        font = font.deriveFont(Font.BOLD, 18f)
        isVisible = false
    }

    if (textfieldTitle.text.isNotEmpty() && textfieldDescription.text.isNotEmpty() && years.value != null && sessionDate.value != null) {
        try {
            val title = textfieldTitle.text
            val description = textfieldDescription.text
            val year = years.value as Int
            val sessionDateValue = sessionDate.value as java.util.Date

            connection = DriverManager.getConnection(url, user, password2)


            val sql = "INSERT INTO kinokava (Title, Description, ReleaseYear, SessionDate) VALUES (?, ?, ?, ?)"
            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, title)
            preparedStatement.setString(2, description)
            preparedStatement.setInt(3, year)
            preparedStatement.setTimestamp(4, java.sql.Timestamp(sessionDateValue.time))  // Set the sessionDate as a timestamp

            preparedStatement.executeUpdate()
            loadKinokava(tableModel)

            error.isVisible = false
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            connection?.close()
        }
    } else {

        error.isVisible = true
    }
}


fun deleteFilm(tableModel: DefaultTableModel, table: JTable, textfieldTitle: JTextField, textfieldDescription: JTextField, years: JSpinner, sessionDate: JSpinner) {
    val url = "jdbc:mysql://localhost:3306/KotlinUser"
    val user = "KotlinUser"
    val password = "12345"
    var connection: Connection? = null

    val selectedRow = table.selectedRow
    if (selectedRow != -1) {
        val filmId = table.getValueAt(selectedRow, 0).toString()

        try {
            connection = DriverManager.getConnection(url, user, password)
            val sql = "delete from kinokava where Id = ?"
            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, filmId)

            val rowsAffected = preparedStatement.executeUpdate()

            if (rowsAffected > 0) {
                println("Film data deleted successfully")
                loadKinokava(tableModel)
            } else {
                println("Film data delete failed")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            connection?.close()
        }
    } else {
        println("Row wasn't selected")
    }

}

fun generateFilmPdf(title: String, description: String, year: Int, fileName: String, sessionDate: String) {
    val pdfPath = "$fileName.pdf"

    try {
        PdfWriter(pdfPath).use { writer ->
            val pdfDocument = PdfDocument(writer)
            val document = Document(pdfDocument)

            document.add(Paragraph("Film Information").setBold())
            document.add(Paragraph("Title: $title"))
            document.add(Paragraph("Description: $description"))
            document.add(Paragraph("Year: $year"))
            document.add(Paragraph("Session Date: $sessionDate"))

            document.close()
        }
        println("PDF file created: $pdfPath")
    } catch (e: Exception) {
        e.printStackTrace()
        println("Error while creating PDF: ${e.message}")
    }
}

fun brooneFilm(tableModel: DefaultTableModel, table: JTable, sessionDateSpinner: JSpinner) {
    val url = "jdbc:mysql://localhost:3306/KotlinUser"
    val user = "KotlinUser"
    val password = "12345"
    var connection: Connection? = null

    val selectedRow = table.selectedRow
    if (selectedRow != -1) {
        val filmId = table.getValueAt(selectedRow, 0).toString()
        try {
            connection = DriverManager.getConnection(url, user, password)
            val sql = "SELECT Title, Description, ReleaseYear, SessionDate FROM kinokava WHERE Id = ?"
            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.use { stmt ->
                stmt.setString(1, filmId)
                val resultSet = stmt.executeQuery()
                resultSet.use { rs ->
                    if (rs.next()) {
                        val title = rs.getString("Title")
                        val description = rs.getString("Description")
                        val year = rs.getInt("ReleaseYear")

                        val sessionDate = rs.getTimestamp("SessionDate")?.let {
                            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(it)
                        } ?: "N/A"

                        generateFilmPdf(title, description, year, "Film_$filmId", sessionDate)
                    } else {
                        println("Film with ID $filmId not found!")
                    }
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            connection?.close()
        }
    } else {
        println("No film selected in the table!")
    }
}
