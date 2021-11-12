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
class SuperHeroController(
    @Autowired
    private val superHeroRepository: SuperHeroRepository
) {

    @GetMapping
    fun retrieveAll(): Flux<SuperHero> = superHeroRepository.findAll()

    @GetMapping("{id}")
    fun findSuperHeroById(@PathVariable(value = "id") id: String): Mono<SuperHero> = superHeroRepository.findById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveSuperHero(@RequestBody superHero: SuperHero): Mono<SuperHero> = superHeroRepository.save(superHero)

    @PutMapping("{id}")
    fun updateSuperHero(
        @PathVariable(value = "id") id: String,
        @RequestBody superHero: SuperHero
    ): Mono<ResponseEntity<SuperHero>> {
        return superHeroRepository.findById(id)
            .flatMap(existingSuperHero -> {
            existingSuperHero.name(superHero.name))
            existingSuperHero.codename(superHero.codename))
            existingSuperHero.age(superHero.age))
            existingSuperHero.homeLand(superHero.homeLand))
            existingSuperHero.sex(superHero.sex))
            existingSuperHero.power(superHero.power))
            return superHeroRepository.save(existingSuperHero))
        })
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSuperHero(@PathVariable(value = "id") id: String): Mono<ResponseEntity<SuperHero>> =
        superHeroRepository.deleteById(id)
            .then<ResponseEntity<SuperHero>?>(Mono.just(ResponseEntity(HttpStatus.NO_CONTENT)))
            .defaultIfEmpty(ResponseEntity(HttpStatus.NOT_FOUND))

    @DeleteMapping
    fun deleteAll(): Mono<Void> = superHeroRepository.deleteAll()

}