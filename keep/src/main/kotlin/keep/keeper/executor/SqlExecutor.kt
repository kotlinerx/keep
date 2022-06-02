package keep.keeper.executor

import keep.sql.statement.*
import keep.table.Table
import java.sql.ResultSet

interface SqlExecutor {
    fun <T : Table> executeDrop(sql: Drop<T>)
    fun <T : Table> executeCreate(sql: Create<T>)
    fun <T : Table> executeInsert(sql: Insert<T>)
    fun <T : Table> executeUpdate(sql: Update<T>): Int
    fun <T : Table> executeSelect(sql: Select<T>): ResultSet
    fun <T : Table> executeDelete(sql: Delete<T>): Int
}
