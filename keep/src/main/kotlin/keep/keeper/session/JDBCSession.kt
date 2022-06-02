package keep.keeper.session

import keep.keeper.context.KeepContext
import java.sql.Connection

/**
 * 可以获取 [Connection] 对象
 */
interface JDBCSession : KeepSession {
    val context: KeepContext
    fun connection(): Connection
    override fun obtainTransaction(): Transaction {
        return JDBCTransaction(
            context = context, useTransaction = false, connection = connection()
        )
    }
}


class ConnectionSession(
    override val context: KeepContext,
    private val connection: Connection,
) : JDBCSession {
    override fun connection(): Connection = connection

}