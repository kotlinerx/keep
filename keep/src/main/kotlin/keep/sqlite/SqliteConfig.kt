package keep.sqlite

import keep.analyzer.SqlAnalyzersManager
import keep.analyzer.analyzerManager
import keep.keeper.context.KeepContext
import keep.keeper.context.SourceConfig
import keep.keeper.mapper.NameMapper
import keep.keeper.mapper.ResultMapper

fun sqliteContext(
    url: String,
    analyzersManager: SqlAnalyzersManager = analyzerManager(*sqliteAnalyzers().toTypedArray()),
    mapper: ResultMapper = NameMapper(),
): KeepContext {
    val config = SourceConfig(url = url, username = "", password = "")
    return KeepContext(config = config, analyzersManager = analyzersManager, mapper = mapper)
}