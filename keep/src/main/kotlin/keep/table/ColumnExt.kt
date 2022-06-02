package keep.table

import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

@Suppress("UNCHECKED_CAST")
fun <T> Column<*>.findValue(entity: T): Any {
    val p = entity!!::class.memberProperties.firstOrNull() {
        it.name == this.label
    } ?: throw IllegalStateException("There is no member property named <$label>")
    return (p as KProperty1<T, *>).get(entity) as Any
}