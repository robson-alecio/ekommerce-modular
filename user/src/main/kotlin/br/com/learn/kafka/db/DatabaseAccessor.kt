package br.com.learn.kafka.db

import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

object DatabaseAccessor {

    private lateinit var connection: Connection

    fun start() {
        println("Creating database")
        val url = "jdbc:sqlite:build/users_database.db"
        connection = DriverManager.getConnection(url)
        if (Files.exists(Path.of( "build/users_database.db"))) {
            return
        }
        connection.createStatement().execute("create table Users (" +
                "uuid varchar(200) primary key," +
                "email varchar(200)" +
                ")")
    }

    fun existsUserByEmail(email: String): Boolean {
        val exists = connection.prepareStatement("select uuid from Users where email = ? limit 1")
        exists.setString(1, email)
        return exists.executeQuery().next()
    }

    fun insert(email: String) {
        val insert = connection.prepareStatement("insert into Users (uuid, email) values (?, ?)")
        val uuid = UUID.randomUUID().toString()
        insert.setString(1, uuid)
        insert.setString(2, email)
        insert.execute()
        println("User $email inserted with id $uuid")
    }

}
