package dev.joseluisgs.expedientesacademicos.alumnado.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import dev.joseluisgs.expedientesacademicos.alumnado.models.Alumno
import dev.joseluisgs.expedientesacademicos.config.AppConfig
import org.lighthousegames.logging.logging
import java.util.concurrent.TimeUnit

fun provideAlumnosCache(config: AppConfig): Cache<Long, Alumno> {
    val logger = logging()
    logger.debug { "Inicializando Cache con capacidad ${config.cacheCapacity} y duración ${config.cacheExpiration} s" }
    return Caffeine.newBuilder()
        .maximumSize(config.cacheCapacity)
        .expireAfterWrite(
            config.cacheExpiration,
            TimeUnit.SECONDS
        )
        .build()
}