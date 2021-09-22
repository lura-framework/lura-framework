package me.lura.logging.service

import cn.hutool.json.JSONUtil
import me.lura.logging.domain.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.lang.Exception
import java.lang.reflect.Method
import java.util.ArrayList

class LogService {

  private val logger = LoggerFactory.getLogger(this::class.java)

  fun save(username: String, browser: String, ip: String, logType: String, time: Long, joinPoint: ProceedingJoinPoint, exceptionDetail: String? = null) {
    val signature = joinPoint.signature as MethodSignature
    val method = signature.method
    val aopLog: me.lura.logging.annotation.Log = method.getAnnotation(me.lura.logging.annotation.Log::class.java)

    // 方法路径
    val methodName = joinPoint.target.javaClass.name + "." + signature.name + "()"

    val log = Log(description = aopLog.value, requestIp = ip, address = "", method = methodName, username = username,
    params = getParameter(method, joinPoint.args), browser = browser, logType = logType, time = time, exceptionDetail = exceptionDetail)

    logger.info(log.toString())

  }

  /**
   * 根据方法和传入的参数获取请求参数
   */
  private fun getParameter(method: Method, args: Array<Any>): String {
    val argList: MutableList<Any> = ArrayList()
    val parameters = method.parameters
    for (i in parameters.indices) {
      //将RequestBody注解修饰的参数作为请求参数
      val requestBody = parameters[i].getAnnotation(RequestBody::class.java)
      if (requestBody != null) {
        argList.add(args[i])
      }
      //将RequestParam注解修饰的参数作为请求参数
      val requestParam = parameters[i].getAnnotation(RequestParam::class.java)
      if (requestParam != null) {
        val map: MutableMap<String, Any> = HashMap()
        var key = parameters[i].name
        if (!ObjectUtils.isEmpty(requestParam.value)) {
          key = requestParam.value
        }
        map[key] = args[i]
        argList.add(map)
      }
    }
    if (argList.size == 0) {
      return ""
    }
    return if (argList.size == 1) JSONUtil.toJsonStr(argList[0]) else JSONUtil.toJsonStr(argList)
  }

}
