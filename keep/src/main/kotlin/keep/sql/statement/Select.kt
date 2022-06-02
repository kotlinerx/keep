package keep.sql.statement

import keep.sql.*
import keep.table.Table

class Select<T : Table>(
    override val table: T,
) : Sql<T>, WhereStatement<T, Select<T>> {
    var distinct: Boolean = false
    override val tag: Tag = SelectTag
    private val setStatement = SetStatementImpl(this)
    private val whereStatement: WhereStatement<T, Select<T>> = WhereStatementImpl(this)
    override fun where(block: WhereScope.(table: T) -> Condition): Select<T> = whereStatement.where(block)
    override fun obtainCondition(): Condition = whereStatement.obtainCondition()
}