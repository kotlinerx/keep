package keep.sql.statement

import keep.sql.*
import keep.table.Table

class Delete<T : Table>(
    override val table: T,
) : Sql<T>, WhereStatement<T, Delete<T>> {
    override val tag: Tag = DeleteTag
    private val whereStatement: WhereStatement<T, Delete<T>> = WhereStatementImpl(this)
    override fun where(block: WhereScope.(table: T) -> Condition): Delete<T> = whereStatement.where(block)
    override fun obtainCondition(): Condition = whereStatement.obtainCondition()
}