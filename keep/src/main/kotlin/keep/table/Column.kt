package keep.table

import java.sql.JDBCType
import kotlin.reflect.KClass

/**
 * 数据库列
 * @param [T] kotlin类型
 */
interface Column<T : Any> {
    val kClass: KClass<T>

    /**
     * 列名
     */
    val label: String

    /**
     * 序号
     */
    val index: Int

    /**
     * 数据库 数据类型
     */
    val sqlType: JDBCType

    /**
     * 长度
     */
    val length: Int

    /**
     * 非空
     */
    val notNull: Boolean

    /**
     * 主键
     */
    val primary: Boolean

    /**
     * 自动递增
     */
    val auto: Boolean

    /**
     * 默认值
     */
    val default: T?

    /**
     * 字段描述
     */
    val description: String


}