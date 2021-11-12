package com.thanosdev.reactivesuperheroes.api.controller

import com.thanosdev.reactivesuperheroes.api.entity.SuperHero
import com.thanosdev.reactivesuperheroes.api.repository.SuperHeroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/superheroes")
class SuperHeroController(@Autowired private val superHeroRepository: SuperHeroRepository) {

    @GetMapping
    fun retrieveAll(): Flux<SuperHero> = superHeroRepository.findAll()

    @GetMapping("{id}")
    fun findSuperHeroById(@PathVariable id: String): Mono<SuperHero> = superHeroRepository.findById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveSuperHero(@RequestBody superHero: SuperHero): Mono<SuperHero> = superHeroRepository.save(superHero)

    @PutMapping("{id}")
    fun updateSuperHero(
        @PathVariable id: String,
        @RequestBody superHero: SuperHero
    ): Mono<ResponseEntity<SuperHero>> =
        superHeroRepository.findById(id)
            .flatMap { existingSuperHero ->
                existingSuperHero.name = superHero.name
                existingSuperHero.codename = superHero.codename
                existingSuperHero.age = superHero.age
                existingSuperHero.homeLand = superHero.homeLand
                existingSuperHero.sex = superHero.sex
                existingSuperHero.power = superHero.power
                return@flatMap superHeroRepository.save(existingSuperHero)
            }.map { ResponseEntity(it, HttpStatus.OK )}
            .defaultIfEmpty(ResponseEntity(HttpStatus.NOT_FOUND))

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSuperHero(@PathVariable id: String): Mono<ResponseEntity<Unit>> =
        superHeroRepository.deleteById(id)
            .then(Mono.just(ResponseEntity<Unit>(HttpStatus.NO_CONTENT)))
            .defaultIfEmpty(ResponseEntity(HttpStatus.NOT_FOUND))

    @DeleteMapping
    fun deleteAll(): Mono<Void> = superHeroRepository.deleteAll()

}