package com.example.vineria.persistance.sqlserver

import android.util.Log
import com.example.vineria.BuildConfig
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement


class DbConnection {

    fun<T> SELECT(query: String, mapper: (ResultSet) -> (T)): List<T> {
        var DbConn: Connection? = null
        val list: ArrayList<T> = ArrayList()
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance()
            // DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://${BuildConfig.DB_URL}:${BuildConfig.DB_PORT}/${BuildConfig.DATABASE};user=${BuildConfig.DB_USER};password=${BuildConfig.DB_PASSWORD}")
            DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://${BuildConfig.DB_URL}:${BuildConfig.DB_PORT};DatabaseName=${BuildConfig.DATABASE};instance=VSDQHL;user=${BuildConfig.DB_USER};password=${BuildConfig.DB_PASSWORD}")
            Log.i("Connection open", "Connection open")
            val stmt: Statement = DbConn.createStatement()
            val result = stmt.executeQuery(query)
            while(result!!.next()) {
                list.add(mapper(result))
            }
        } catch (e: Exception) {
            Log.e("Connection error", "" + e.message)
            throw e
        } finally {
            DbConn?.close()
        }

        return list
    }

    fun INSERT(query: String): Boolean {
        var DbConn: Connection? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance()
            DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://${BuildConfig.DB_URL}:${BuildConfig.DB_PORT}/${BuildConfig.DATABASE};user=${BuildConfig.DB_USER};password=${BuildConfig.DB_PASSWORD}")
            Log.i("Connection open", "Connection open")
            val stmt: Statement = DbConn.createStatement()
            stmt.executeUpdate(query)
        } catch (e: Exception) {
            Log.e("Connection error", "" + e.message)
            return false
        } finally {
            DbConn?.close()
        }

        return true
    }
}