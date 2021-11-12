package com.thanosdev.reactivesuperheroes.api.repository

import com.thanosdev.reactivesuperheroes.api.entity.Sex
import com.thanosdev.reactivesuperheroes.api.entity.SuperHero
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SuperHeroRepository: ReactiveMongoRepository<SuperHero, String> {

    override fun findById(id: String): Mono<SuperHero>
    fun findBySex(sex: Sex): Flux<SuperHero>
}