package keep.keeper.context

/**
 * 数据库配置参数
 */
data class SourceConfig(
    val url: String,
    val username: String,
    val password: String,
    val useSourcePool: Boolean = false,
)