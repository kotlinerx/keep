package keep.sql

import keep.table.Table

interface WhereStatement<T : Table, S : Sql<T>> {
    fun where(block: WhereScope.(table: T) -> Condition): S
    fun obtainCondition(): Condition
}

class WhereStatementImpl<T : Table, S : Sql<T>>(
    private val sql: S,
) : WhereStatement<T, S> {
    private val whereScope: WhereScope = object : WhereScope {}
    private var _condition: Condition = Condition.All

    override fun where(block: WhereScope.(table: T) -> Condition): S {
        _condition = whereScope.block(sql.table)
        return sql
    }

    override fun obtainCondition(): Condition = _condition
}