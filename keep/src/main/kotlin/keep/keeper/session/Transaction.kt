package keep.keeper.session

import keep.keeper.sqlscope.SqlScope

interface Transaction {
    val useTransaction: Boolean
    fun start()
    fun rollback()
    fun commit()
    fun obtainSqlScope(): SqlScope
}