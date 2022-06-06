package keep.keeper

import keep.keeper.context.KeepContext
import keep.keeper.sqlscope.Query
import keep.keeper.sqlscope.SqlScope

interface Keeper : Query {
    val context: KeepContext
    operator fun <T> invoke(startTransaction: Boolean = true, block: SqlScope.() -> T): T
}