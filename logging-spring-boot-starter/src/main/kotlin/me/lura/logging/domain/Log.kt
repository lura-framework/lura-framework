package me.lura.logging.domain

import java.sql.Timestamp

data class Log(
  private var id: Long? = null,

  /** 操作用户  */
  val username: String,

  /** 描述  */
  val description: String,

  /** 方法名  */
  val method: String,

  /** 参数  */
  val params: String ,

  /** 日志类型  */
  val logType: String,

  /** 请求ip  */
  val requestIp: String,

  /** 地址  */
  val address: String ,

  /** 浏览器   */
  val browser: String ,

  /** 请求耗时  */
  val time: Long,

  /** 异常详细   */
  val exceptionDetail: String? = null,

  /** 创建日期  */
  private val createTime: Timestamp? = null
)
