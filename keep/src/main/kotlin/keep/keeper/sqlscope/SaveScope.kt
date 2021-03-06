package keep.keeper.sqlscope

import keep.sql.ColumnValue
import keep.sql.Condition
import keep.sql.WhereScope
import keep.table.Table
import java.sql.ResultSet

interface SaveScope {
    fun <E : Any, T : Table> add(table: T, vararg entities: E)
    fun <E : Any, T : Table> add(table: T, list: List<E>)
    fun <E : Any, T : Table> addOne(table: T, entity: E, callback: E.(ResultSet) -> E = { entity }): E

    fun <T : Table> update(table: T, values: List<ColumnValue<*>>, condition: WhereScope.(T) -> Condition)

}