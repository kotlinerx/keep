package keep.table

interface NamePolicy {
    fun obtainName(key: Any): String
    fun baseName(): String
}

class SimpleNamePolicy(
    private val name: String,
) : NamePolicy {
    override fun obtainName(key: Any): String {
        return name
    }

    override fun baseName(): String {
        return name
    }
}