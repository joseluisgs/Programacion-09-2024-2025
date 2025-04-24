package dev.joseluisgs.expedientesacademicos.di

import com.github.benmanes.caffeine.cache.Cache
import dev.joseluisgs.expedientesacademicos.alumnado.cache.provideAlumnosCache
import dev.joseluisgs.expedientesacademicos.alumnado.dao.AlumnosDao
import dev.joseluisgs.expedientesacademicos.alumnado.dao.provideAlumnosDao
import dev.joseluisgs.expedientesacademicos.alumnado.models.Alumno
import dev.joseluisgs.expedientesacademicos.alumnado.repositories.AlumnosRepository
import dev.joseluisgs.expedientesacademicos.alumnado.repositories.AlumnosRepositoryImpl
import dev.joseluisgs.expedientesacademicos.alumnado.services.AlumnosService
import dev.joseluisgs.expedientesacademicos.alumnado.services.AlumnosServiceImpl
import dev.joseluisgs.expedientesacademicos.alumnado.storage.*
import dev.joseluisgs.expedientesacademicos.alumnado.viewmodels.ExpedientesViewModel
import dev.joseluisgs.expedientesacademicos.config.AppConfig
import dev.joseluisgs.expedientesacademicos.database.provideDatabaseManager
import org.jdbi.v3.core.Jdbi
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


/**
 * Koin module for the application.
 */
val appModule = module {
    singleOf(::AppConfig)

    singleOf(::provideDatabaseManager) {
        bind<Jdbi>()
    }

    singleOf(::provideAlumnosDao) {
        bind<AlumnosDao>()
    }

    singleOf(::provideAlumnosCache) {
        bind<Cache<Long, Alumno>>()
    }

    singleOf(::AlumnosRepositoryImpl) {
        bind<AlumnosRepository>()
    }

    singleOf(::AlumnosStorageJsonImpl) {
        bind<AlumnosStorageJson>()
    }

    singleOf(::AlumnosStorageZipImpl) {
        bind<AlumnosStorageZip>()
    }

    singleOf(::AlumnosStorageImagesImpl) {
        bind<AlumnosStorageImages>()
    }

    singleOf(::AlumnosStorageImpl) {
        bind<AlumnosStorage>()
    }


    singleOf(::AlumnosServiceImpl) {
        bind<AlumnosService>()
    }

    singleOf(::ExpedientesViewModel)
}