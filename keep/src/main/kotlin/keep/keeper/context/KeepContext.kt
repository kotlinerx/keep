package keep.keeper.context

import keep.analyzer.SqlAnalyzersManager
import keep.keeper.sqlscope.ResultMapper

class KeepContext(
    val config: SourceConfig,
    val analyzersManager: SqlAnalyzersManager,
    val mapper: ResultMapper,
)