package keep.keeper.sqlscope

import keep.sql.statement.*
import keep.table.Table
import java.sql.ResultSet

interface SqlScope {
    fun <T : Table> drop(table: T)
    fun <T : Table> create(table: T)
    fun <T : Table> insert(table: T, block: Insert<T>.() -> Unit)
    fun <T : Table> update(table: T, block: Update<T>.() -> Unit): Int
    fun <T : Table> select(table: T, block: Select<T>.() -> Unit): ResultSet
    fun <T : Table> delete(table: T, block: Delete<T>.() -> Unit): Int
}