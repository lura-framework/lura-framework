
package me.lura.datasource.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy


@Target(AnnotationTarget.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class Query(
  val propName: String = "",
  val type: Type = Type.EQUAL,
  /**
   * 连接查询的属性名，如User类中的dept
   */
  val joinName: String = "",
  /**
   * 默认左连接
   */
  val join: Join = Join.LEFT,
  /**
   * 多字段模糊搜索，仅支持String类型字段，多个用逗号隔开, 如@Query(blurry = "email,username")
   */
  val blurry: String = ""
) {
  enum class Type {
    EQUAL, GREATER_THAN, LESS_THAN, INNER_LIKE, LEFT_LIKE, RIGHT_LIKE, LESS_THAN_NQ, IN, NOT_IN, NOT_EQUAL, BETWEEN, NOT_NULL, IS_NULL
  }

  /**
   * 适用于简单连接查询，复杂的请自定义该注解，或者使用sql查询
   */
  enum class Join {
    LEFT, RIGHT, INNER
  }
}
