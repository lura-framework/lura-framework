package me.lura.logging.aspect

import me.lura.logging.service.LogService
import me.lura.logging.utils.ThrowableUtil
import nl.basjes.parse.useragent.UserAgent
import nl.basjes.parse.useragent.UserAgent.ImmutableUserAgent
import nl.basjes.parse.useragent.UserAgentAnalyzer
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.net.InetAddress
import java.net.UnknownHostException
import javax.servlet.http.HttpServletRequest

@Aspect
class LogAspect(private val logService: LogService) {
  var currentTime = ThreadLocal<Long>()
  private val unknown = "unknown"
  private val log: Logger = LoggerFactory.getLogger(this::class.java)

  private val userAgentAnalyzer: UserAgentAnalyzer = UserAgentAnalyzer
    .newBuilder()
    .hideMatcherLoadStats()
    .withCache(10000)
    .withField(UserAgent.AGENT_NAME_VERSION)
    .build()

  @Pointcut("@annotation(me.lura.logging.annotation.Log)")
  fun logPointcut() {
  }

  @Around("logPointcut()")
  fun logAround(joinPoint: ProceedingJoinPoint): Any {
    currentTime.set(System.currentTimeMillis())
    val result = joinPoint.proceed()

    val request: HttpServletRequest = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
    logService.save("getUsername()", getBrowser(request), getIp(request), "INFO",System.currentTimeMillis() - currentTime.get() , joinPoint)
    currentTime.remove()
    return result

  }

  @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
  fun logAfterThrowing(joinPoint: JoinPoint, e: Throwable) {
    val request: HttpServletRequest = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
    logService.save(
      "getUsername()",
      getBrowser(request),
      getIp(request),
      "ERROR",
      System.currentTimeMillis() - currentTime.get(),
      joinPoint as ProceedingJoinPoint,
      ThrowableUtil.getStackTrace(e)
    )
    currentTime.remove()
  }

  private fun getBrowser(request: HttpServletRequest): String {
    val userAgent: ImmutableUserAgent =
      userAgentAnalyzer.parse(request.getHeader("User-Agent"))
    return userAgent[UserAgent.AGENT_NAME_VERSION].value
  }


  /**
   * 获取ip地址
   */
  private fun getIp(request: HttpServletRequest): String {
    var ip = request.getHeader("x-forwarded-for")
    if (ip == null || ip.isEmpty() || unknown.equals(ip, ignoreCase = true)) {
      ip = request.getHeader("Proxy-Client-IP")
    }
    if (ip == null || ip.isEmpty() || unknown.equals(ip, ignoreCase = true)) {
      ip = request.getHeader("WL-Proxy-Client-IP")
    }
    if (ip == null || ip.isEmpty() || unknown.equals(ip, ignoreCase = true)) {
      ip = request.remoteAddr
    }
    val comma = ","
    val localhost = "127.0.0.1"
    if (ip!!.contains(comma)) {
      ip = ip.split(",").toTypedArray()[0]
    }
    if (localhost == ip) {
      // 获取本机真正的ip地址
      try {
        ip = InetAddress.getLocalHost().hostAddress
      } catch (e: UnknownHostException) {
        log.error(e.message, e)
      }
    }
    return ip
  }
}
