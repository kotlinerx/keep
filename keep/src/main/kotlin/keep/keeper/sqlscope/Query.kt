package keep.keeper.sqlscope

import kotlin.reflect.KClass

interface Query {
    val defaultMapper: ResultMapper
    fun <E : Any> obtainQueryScope(kClass: KClass<E>, mapper: ResultMapper? = null): QueryScope<E>
}
