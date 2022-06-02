package keep.keeper.sqlscope

import keep.sql.Condition
import keep.sql.WhereScope
import keep.table.Table

/**
 * [E] 实体类
 */
interface QueryScope<E : Any> {
    fun <T : Table> from(table: T, block: WhereScope.(T) -> Condition): List<E>

    fun <T : Table> firstOrNull(table: T, block: WhereScope.(T) -> Condition): E? {
        return from(table, block).firstOrNull()
    }

    fun <T : Table> first(table: T, block: WhereScope.(T) -> Condition): E {
        return from(table, block).first()
    }
}