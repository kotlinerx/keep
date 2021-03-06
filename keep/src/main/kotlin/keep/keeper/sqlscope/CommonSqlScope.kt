package keep.keeper.sqlscope

import keep.keeper.context.KeepContext
import keep.keeper.executor.SqlExecutor
import keep.sql.ColumnValue
import keep.sql.Condition
import keep.sql.WhereScope
import keep.sql.statement.*
import keep.table.Column
import keep.table.Table
import keep.table.findValue
import java.sql.ResultSet

class CommonSqlScope(
    override val context: KeepContext,
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

    @Suppress("UNCHECKED_CAST")
    override fun <T : Table> update(table: T, values: List<ColumnValue<*>>, condition: WhereScope.(T) -> Condition) {
        update(table) {
            set {
                values.forEach {
                    (it.column as Column<Any>) eq it.value
                }
            }
            where(condition)
        }
    }


    override fun <T : Table> select(table: T, block: Select<T>.() -> Unit): ResultSet {
        val sql = Select(table).apply(block)
        return executor.executeSelect(sql)
    }

    override fun <T : Table> delete(table: T, block: Delete<T>.() -> Unit): Int {
        val sql = Delete(table).apply(block)
        return executor.executeDelete(sql)
    }

    override fun <E : Any, T : Table> add(table: T, vararg entities: E) {
        entities.forEach { addOne(table, it) }
    }

    override fun <E : Any, T : Table> add(table: T, list: List<E>) {
        list.forEach { addOne(table, it) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <E : Any, T : Table> addOne(table: T, entity: E, callback: E.(ResultSet) -> E): E {
        val columns = table.columns
        val auto: Column<*>? = columns.firstOrNull() { it.auto }
        val insert: Insert<T> = Insert(table)
        insert.set {
            columns.filterNot { it.auto }.forEach {
                val v = it.findValue(entity)
                val c = (it as Column<Any>)
                c eq v
            }
        }
        val resultSet = executor.executeInsert(insert)
        if (auto == null) return entity
        return entity.callback(resultSet)
    }
}
