package keep.sqlite

import keep.analyzer.analyzerManager
import keep.keeper.context.KeepContext
import keep.keeper.context.SourceConfig

fun sqliteContext(url:String): KeepContext {
    val config = SourceConfig(url = url, username = "", password = "")
    val analyzersManager = analyzerManager(*sqliteAnalyzers().toTypedArray())
    return KeepContext(config = config, analyzersManager = analyzersManager)
}