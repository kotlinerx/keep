package keep.analyzer

import keep.sql.ColumnValue
import keep.sql.Condition
import keep.table.Column

/**
 * 拼接 sql 字符串使用占位符?后,需要将对应参数通过[ResolveParameter]来处理对应的参数
 */
typealias ResolveParameter = (ColumnValue<*>) -> Unit

interface ConditionAnalyzer {
    fun analyseCondition(condition: Condition?, block: ResolveParameter): String {
        val conditionString = buildCondition(condition, block)
        if (conditionString == "") return ""
        return " where $conditionString"
    }

    @Suppress("UNCHECKED_CAST")
    fun buildCondition(condition: Condition?, block: ResolveParameter): String {
        if (condition == null) return ""
        return when (condition) {
            is Condition.Simple<*> -> {
                val columnName = condition.column.label
                val operator = condition.operator
                val valueText = if (operator.unary) "" else "?"
                if (!operator.unary) {
                    block(ColumnValue(condition.column as Column<*>, condition.value))
                }
                " $columnName ${operator.text} $valueText "
            }
            is Condition.Complex -> {
                val left = buildCondition(condition.condition0, block)
                val right = buildCondition(condition.condition1, block)
                val operatorText = condition.operator.text
                "( $left ) $operatorText ( $right )"
            }
            is Condition.Values<*> -> {
                val column = condition.column
                val values = condition.values.joinToString(",") {
                    block(ColumnValue(column, it))
                    "?"
                }
                "${column.label} ${condition.operator.text} ($values)"
            }
            Condition.All -> ""
        }
    }
}