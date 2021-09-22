
package me.lura.logging.utils

import java.io.StringWriter
import java.io.PrintWriter


object ThrowableUtil {
  /**
   * 获取堆栈信息
   */
  fun getStackTrace(throwable: Throwable): String {
    val sw = StringWriter()
    PrintWriter(sw).use { pw ->
      throwable.printStackTrace(pw)
      return sw.toString()
    }
  }
}
