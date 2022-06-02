package keep.sql.statement

import keep.sql.*
import keep.table.Table

class Update<T : Table>(
    override val table: T,
) : Sql<T>, WhereStatement<T, Update<T>>, SetStatement<T, Update<T>> {
    override val tag: Tag = UpdateTag
    private val setStatement = SetStatementImpl(this)
    private val whereStatement: WhereStatement<T, Update<T>> = WhereStatementImpl(this)
    override fun set(block: SetScope.(table: T) -> Unit): Update<T> = setStatement.set(block)
    override fun obtainValues(): List<ColumnValue<*>> = setStatement.obtainValues()
    override fun where(block: WhereScope.(table: T) -> Condition): Update<T> = whereStatement.where(block)
    override fun obtainCondition(): Condition = whereStatement.obtainCondition()
}
