package com.example.numbletimedealserver

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class NumbleTimeDealServerApplication{
    @PersistenceContext
    lateinit var em:EntityManager
    @Bean
    fun jpaQueryFactory():JPAQueryFactory = JPAQueryFactory(em)
}

fun main(args: Array<String>) {
    runApplication<NumbleTimeDealServerApplication>(*args)
}
