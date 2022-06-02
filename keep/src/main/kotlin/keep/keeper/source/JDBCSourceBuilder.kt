package keep.keeper.source

import keep.keeper.context.KeepContext


fun buildJDBCSource(context: KeepContext): JDBCSource {
    val config = context.config
    return if (config.useSourcePool) {
        throw UnsupportedOperationException("useSourcePool is unsupported currently.please set false")
    } else {
        SimpleSource(context)
    }
}