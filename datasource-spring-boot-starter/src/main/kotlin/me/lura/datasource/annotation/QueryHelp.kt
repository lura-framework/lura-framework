package me.lura.datasource.annotation

import cn.hutool.core.collection.CollUtil
import cn.hutool.core.util.ObjectUtil
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.lang.reflect.Field
import java.util.ArrayList
import java.util.Arrays
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Join
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root


object QueryHelp {
  private val log = LoggerFactory.getLogger(this::class.java)

  fun <R, Q> getPredicate(root: Root<R>, query: Q?, cb: CriteriaBuilder): Predicate {
    val list: MutableList<Predicate> = ArrayList()
    if (query == null) {
      return cb.and(*list.toTypedArray())
    }

    try {
      val fields = getAllFields(query.javaClass, ArrayList())
      for (field in fields) {
        val accessible = field.isAccessible
        // 设置对象的访问权限，保证对private的属性的访
        field.isAccessible = true
        val q = field.getAnnotation(Query::class.java)
        if (q != null) {
          val propName = q.propName
          val joinName = q.joinName
          val blurry = q.blurry
          val attributeName = if (isBlank(propName)) field.name else propName
          val fieldType = field.type
          val value = field[query]
          if (ObjectUtil.isNull(value) || "" == value) {
            continue
          }
          var join: Join<R, *>? = null
          // 模糊多字段
          if (ObjectUtil.isNotEmpty(blurry)) {
            val blurrys = blurry.split(",").toTypedArray()
            val orPredicate: MutableList<Predicate> = mutableListOf()
            for (s in blurrys) {
              orPredicate.add(
                cb.like(
                  root.get<Any>(s)
                    .`as`(String::class.java), "%$value%"
                )
              )
            }
            list.add(cb.or(*orPredicate.toTypedArray()))
            continue
          }
          if (ObjectUtil.isNotEmpty(joinName)) {
            val joinNames = joinName.split(">").toTypedArray()
            for (name in joinNames) {
              when (q.join) {
                Query.Join.LEFT -> join = if (join != null && ObjectUtil.isNotNull(value)) {
                  join.join<R, Any>(name, JoinType.LEFT)
                } else {
                  root.join<R, Any>(name, JoinType.LEFT)
                }
                Query.Join.RIGHT -> join = if (join != null && ObjectUtil.isNotNull(value)) {
                  join.join<R, Any>(name, JoinType.RIGHT)
                } else {
                  root.join<R, Any>(name, JoinType.RIGHT)
                }
                Query.Join.INNER -> join = if (join != null && ObjectUtil.isNotNull(value)) {
                  join.join<R, Any>(name, JoinType.INNER)
                } else {
                  root.join<R, Any>(name, JoinType.INNER)
                }
              }
            }
          }
          when (q.type) {
            Query.Type.EQUAL -> list.add(
              cb.equal(
                getExpression(attributeName, join, root)
                  .`as`(fieldType as Class<*>), value
              )
            )
            Query.Type.GREATER_THAN -> list.add(
              cb.greaterThanOrEqualTo<Comparable<*>>(
                getExpression(attributeName, join, root)
                  .`as`(fieldType as Class<out Comparable<*>?>), value as Comparable<*>
              )
            )
            Query.Type.LESS_THAN -> list.add(
              cb.lessThanOrEqualTo<Comparable<*>>(
                getExpression(attributeName, join, root)
                  .`as`(fieldType as Class<out Comparable<*>?>), value as Comparable<*>
              )
            )
            Query.Type.LESS_THAN_NQ -> list.add(
              cb.lessThan<Comparable<*>>(
                getExpression(attributeName, join, root)
                  .`as`(fieldType as Class<out Comparable<*>?>), value as Comparable<*>
              )
            )
            Query.Type.INNER_LIKE -> list.add(
              cb.like(
                getExpression(attributeName, join, root)
                  .`as`(String::class.java), "%$value%"
              )
            )
            Query.Type.LEFT_LIKE -> list.add(
              cb.like(
                getExpression(attributeName, join, root)
                  .`as`(String::class.java), "%$value"
              )
            )
            Query.Type.RIGHT_LIKE -> list.add(
              cb.like(
                getExpression(attributeName, join, root)
                  .`as`(String::class.java), "$value%"
              )
            )
            Query.Type.IN -> if (CollUtil.isNotEmpty(value as Collection<Any?>)) {
              list.add(getExpression(attributeName, join, root).`in`(value))
            }
            Query.Type.NOT_IN -> if (CollUtil.isNotEmpty(value as Collection<Any?>)) {
              list.add(getExpression(attributeName, join, root).`in`(value).not())
            }
            Query.Type.NOT_EQUAL -> list.add(cb.notEqual(getExpression(attributeName, join, root), value))
            Query.Type.NOT_NULL -> list.add(cb.isNotNull(getExpression(attributeName, join, root)))
            Query.Type.IS_NULL -> list.add(cb.isNull(getExpression(attributeName, join, root)))
            Query.Type.BETWEEN -> {
              val between: List<Any> = listOf(value)
              list.add(
                cb.between<Comparable<*>>(
                  getExpression(attributeName, join, root).`as`(
                    between[0].javaClass as Class<out Comparable<*>?>
                  ),
                  between[0] as Comparable<*>, between[1] as Comparable<*>
                )
              )
            }
            else -> {
            }
          }
        }
        field.isAccessible = accessible
      }
    } catch (e: Exception) {
      log.error(e.message, e)
    }
    return cb.and(*list.toTypedArray())
  }

  private fun <T, R> getExpression(attributeName: String, join: Join<R, T>?, root: Root<R>): Expression<T> {
    return if (join != null) {
      join.get(attributeName)
    } else {
      root.get(attributeName)
    }
  }

  private fun isBlank(cs: CharSequence?): Boolean  = (cs == null || cs.trim().isEmpty())

  fun getAllFields(clazz: Class<*>?, fields: MutableList<Field>): List<Field> {
    if (clazz != null) {
      fields.addAll(Arrays.asList(*clazz.declaredFields))
      getAllFields(clazz.superclass, fields)
    }
    return fields
  }
}
