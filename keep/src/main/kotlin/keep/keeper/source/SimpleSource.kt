package keep.keeper.source

import keep.keeper.context.KeepContext
import keep.keeper.session.ConnectionSession
import keep.keeper.session.JDBCSession
import java.sql.Connection
import java.sql.DriverManager

abstract class JDBCSource : KeepSource<JDBCSession>

class SimpleSource(private val context: KeepContext) : JDBCSource() {
    override fun obtainSession(): JDBCSession {
        return ConnectionSession(context, createConnection())
    }

    override fun close(session: JDBCSession) {
        session.connection().close()
    }

    private fun createConnection(): Connection {
        val config = context.config
        return DriverManager.getConnection(config.url, config.username, config.password)
    }
}