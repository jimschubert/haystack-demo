package us.jimschubert.haystackdemo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Component which pulls a typed database configuration from the active profile's settings.
 */
@Component
data class DatabaseConfig(
    @Value(value = "\${spring.datasource.url}") val url: String,
    @Value(value = "\${spring.datasource.driver-class-name}") val driver: String,
    @Value(value = "\${spring.datasource.username}") val user: String = "",
    @Value(value = "\${spring.datasource.password}") val password: String = ""
)
