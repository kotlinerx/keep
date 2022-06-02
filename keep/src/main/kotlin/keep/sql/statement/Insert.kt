package keep.sql.statement

import keep.sql.*
import keep.table.Table

class Insert<T : Table>(
    override val table: T,
) : Sql<T>, SetStatement<T, Insert<T>> {
    private val setStatement = SetStatementImpl(this)
    override val tag: Tag = InsertTag
    override fun set(block: SetScope.(table: T) -> Unit): Insert<T> = setStatement.set(block)
    override fun obtainValues(): List<ColumnValue<*>> = setStatement.obtainValues()
}