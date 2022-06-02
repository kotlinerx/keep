package keep.keeper.context

import keep.analyzer.SqlAnalyzersManager

class KeepContext(
    val config: SourceConfig,
    val analyzersManager: SqlAnalyzersManager,
)