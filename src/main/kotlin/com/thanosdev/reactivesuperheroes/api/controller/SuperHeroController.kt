package com.thanosdev.reactivesuperheroes.api.controller

import com.thanosdev.reactivesuperheroes.api.dto.SuperHeroForm
import com.thanosdev.reactivesuperheroes.api.dto.SuperHeroView
import com.thanosdev.reactivesuperheroes.api.entity.SuperHero
import com.thanosdev.reactivesuperheroes.api.repository.SuperHeroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/superheroes")
class SuperHeroController(
    @Autowired private val superHeroRepository: SuperHeroRepository,
    //@Autowired private val superHeroService: SuperHeroService
    ) {

    @GetMapping
    fun retrieveAll(): Flux<SuperHeroView> = superHeroRepository.findAll().map { it.convertView(it) }

    @GetMapping("{id}")
    fun findSuperHeroById(@PathVariable id: String): Mono<ResponseEntity<SuperHeroView>> {
        //val superHeroBiography = superHeroService.getSuperHeroBiography(id)

        return superHeroRepository.findById(id)
            .map { ResponseEntity(it.convertView(it), HttpStatus.OK) }
            .defaultIfEmpty(ResponseEntity(HttpStatus.NOT_FOUND))
    }


    @PostMapping
    @Transactional
    fun saveSuperHero(
        @RequestBody @Valid superHeroForm: SuperHeroForm,
        uriComponentsBuilder: UriComponentsBuilder
    ): Mono<ResponseEntity<SuperHero>> {
        val superHero = superHeroForm.convertForm(superHeroForm)

        return superHeroRepository.save(superHero)
            .map {
                ResponseEntity.created(
                    uriComponentsBuilder.path("/superheroes/{id}")
                        .buildAndExpand(superHero.id)
                        .toUri()
                ).body(superHero)
            }.doOnError { print("ERROR") }
    }

    @PutMapping("{id}")
    @Transactional
    fun updateSuperHero(
        @PathVariable id: String,
        @RequestBody superHeroForm: SuperHeroForm
    ): Mono<ResponseEntity<SuperHero>> =
        superHeroRepository.findById(id)
            .flatMap { existingSuperHero ->
                existingSuperHero.name = superHeroForm.name
                existingSuperHero.codename = superHeroForm.codename
                existingSuperHero.age = superHeroForm.age
                existingSuperHero.homeLand = superHeroForm.homeLand
                existingSuperHero.sex = superHeroForm.sex
                existingSuperHero.power = superHeroForm.power
                return@flatMap superHeroRepository.save(existingSuperHero)
            }.map { ResponseEntity(it, HttpStatus.OK) }
            .defaultIfEmpty(ResponseEntity(HttpStatus.NOT_FOUND))

    @DeleteMapping("{id}")
    @Transactional
    fun deleteSuperHero(@PathVariable id: String) =
        superHeroRepository.deleteById(id)
            .then(Mono.just(ResponseEntity<Unit>(HttpStatus.NO_CONTENT)))
            .defaultIfEmpty(ResponseEntity(HttpStatus.NOT_FOUND))

    @DeleteMapping
    @Transactional
    fun deleteAll(): Mono<ResponseEntity<Void>> =
        superHeroRepository.deleteAll()
            .then(Mono.just(ResponseEntity<Void>(HttpStatus.NO_CONTENT)))

    fun SuperHero.convertView(superHero: SuperHero) = SuperHeroView(
        id = id,
        codename = codename,
        age = age,
        homeLand = homeLand,
        sex = sex,
        power = power
        //biography = biography
    )

    fun SuperHeroForm.convertForm(superHeroForm: SuperHeroForm) = SuperHero(
        name = name,
        codename = codename,
        age = age,
        homeLand = homeLand,
        sex = sex,
        power = power
        //biography = biography
    )
}