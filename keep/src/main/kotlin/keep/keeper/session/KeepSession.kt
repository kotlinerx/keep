package keep.keeper.session

interface KeepSession {
    fun obtainTransaction(): Transaction
}