package keep.keeper.sqlscope

import keep.keeper.context.KeepContext
import keep.keeper.executor.SqlExecutor
import keep.sql.statement.*
import keep.table.Table
import java.sql.ResultSet

class CommonSqlScope(
    private val context: KeepContext,
    private val executor: SqlExecutor,
) : SqlScope {
    override fun <T : Table> drop(table: T) {
        val sql = Drop(table)
        executor.executeDrop(sql)
    }

    override fun <T : Table> create(table: T) {
        val sql = Create(table)
        executor.executeCreate(sql)
    }

    override fun <T : Table> insert(table: T, block: Insert<T>.() -> Unit) {
        val sql = Insert(table).apply(block)
        executor.executeInsert(sql)
    }

    override fun <T : Table> update(table: T, block: Update<T>.() -> Unit): Int {
        val sql = Update(table).apply(block)
        return executor.executeUpdate(sql)
    }

    override fun <T : Table> select(table: T, block: Select<T>.() -> Unit): ResultSet {
        val sql = Select(table).apply(block)
        return executor.executeSelect(sql)
    }

    override fun <T : Table> delete(table: T, block: Delete<T>.() -> Unit): Int {
        val sql = Delete(table).apply(block)
        return executor.executeDelete(sql)
    }

}
