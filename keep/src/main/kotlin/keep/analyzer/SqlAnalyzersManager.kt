package keep.analyzer

import keep.sql.Tag


interface SqlAnalyzersManager {
    fun registerTag(tag: Tag, analyzer: SqlAnalyzer<*>)
    fun register(analyzer: SqlAnalyzer<*>) = registerTag(analyzer.tag, analyzer)
    fun provideAnalyzer(tag: Tag): SqlAnalyzer<*>
}

open class CommonAnalyzerManager() : SqlAnalyzersManager {
    private val analyzerMap: MutableMap<String, SqlAnalyzer<*>> = HashMap()


    override fun registerTag(tag: Tag, analyzer: SqlAnalyzer<*>) {
        analyzerMap[tag.name] = analyzer
    }

    override fun provideAnalyzer(tag: Tag): SqlAnalyzer<*> {
        return analyzerMap[tag.name] ?: throw IllegalStateException("Please register Analyzer of [$tag] first.")
    }

}

fun analyzerManager(vararg args: SqlAnalyzer<*>): SqlAnalyzersManager {
    return CommonAnalyzerManager().apply {
        args.forEach(this::register)
    }
}