package keep.sql

import keep.table.Column
import keep.table.Table

interface SetStatement<T : Table, S : Sql<T>> {
    fun set(block: SetScope.(table: T) -> Unit): S
    fun obtainValues(): List<ColumnValue<*>>

}

class SetStatementImpl<T : Table, S : Sql<T>>(
    private val sql: S,
) : SetStatement<T, S> {
    private val values: MutableList<ColumnValue<*>> = ArrayList()
    private val setter: SetScope = object : SetScope {
        override fun <T : Any> Column<T>.eq(value: T?) {
            values.add(ColumnValue(this, value))
        }
    }

    override fun set(block: SetScope.(table: T) -> Unit): S {
        setter.block(sql.table)
        return sql
    }

    override fun obtainValues(): List<ColumnValue<*>> {
        return values
    }
}

