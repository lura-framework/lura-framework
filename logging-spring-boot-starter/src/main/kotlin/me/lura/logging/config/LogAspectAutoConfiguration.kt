package me.lura.logging.config

import me.lura.logging.aspect.LogAspect
import me.lura.logging.service.LogService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(prefix = "lura.logging", name = ["auto"], havingValue = "true", matchIfMissing = true)
class LogAspectAutoConfiguration {

  @Bean
  fun logAspect(logService: LogService): LogAspect = LogAspect(logService)

  @Bean
  fun logService() = LogService()
}
