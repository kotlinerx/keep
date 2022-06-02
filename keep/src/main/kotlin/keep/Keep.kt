package keep

import keep.keeper.JDBCKeeper
import keep.keeper.Keeper
import keep.keeper.context.KeepContext
import keep.sqlite.sqliteContext

object Keep {
    fun sqlite(url: String): Keeper {
        val context = sqliteContext(url)
        return JDBCKeeper(context)
    }

    fun jdbcKeeper(context: KeepContext): Keeper = JDBCKeeper(context)

}

