package keep.table

import java.sql.JDBCType
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class ColumnDelegate<T : Any>(
    override val kClass: KClass<T>,
    override val label: String,
    override val index: Int,
    override val sqlType: JDBCType,
) : Column<T>, ReadOnlyProperty<Any, Column<T>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Column<T> {
        return this
    }

    override var length: Int = 0
    override var notNull: Boolean = false
    override var primary: Boolean = false
    override var auto: Boolean = false
    override var default: T? = null
    override var description: String = ""

    fun notNull(): ColumnDelegate<T> {
        notNull = true
        return this
    }

    fun primary(): ColumnDelegate<T> {
        primary = true
        return this
    }

    fun auto(): ColumnDelegate<T> {
        auto = true
        return this
    }

    fun default(value: T): ColumnDelegate<T> {
        default = value
        return this
    }

    fun description(desc: String): ColumnDelegate<T> {
        description = desc
        return this
    }
}

