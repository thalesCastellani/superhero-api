package com.thanosdev.reactivesuperheroes.api.repository

import com.thanosdev.reactivesuperheroes.api.entity.Sex
import com.thanosdev.reactivesuperheroes.api.entity.SuperHero
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@SpringBootTest
class SuperHeroRepositoryTest(
    @Autowired
    private val superHeroRepository: SuperHeroRepository
    ) {

    private val superHeroes: List<SuperHero> = listOf(
        SuperHero(
            name = "Peter Parker",
            codename = "Spider-man",
            age = 15,
            homeLand = "NY",
            sex = Sex.MALE,
            power = "Spider-Sense Alert"
        ),
        SuperHero(
            name = "Tony Stark",
            codename = "Iron Man",
            age = 40,
            homeLand = "Bulgary",
            sex = Sex.MALE,
            power = "Freeze-Beam"
        ),
        SuperHero(
            name = "Blackagar Boltagon",
            codename = "Black Bolt",
            age = 38,
            homeLand = "Attilan",
            sex = Sex.MALE,
            power = "Hypersonic Scream"
        )
    )

    @BeforeEach
    fun setUp() {
        // Delete all entitites managed by the repository | Returns a Mono of type void
        superHeroRepository.deleteAll()
            .thenMany(Flux.fromIterable(superHeroes))
            .flatMap(superHeroRepository::save)
            .then()
            .block()
    }

    @Test
    fun save() {
        val batman = SuperHero(
            name = "Bruce Wayne",
            codename = "Batman",
            age = 50,
            homeLand = "Unknown",
            sex = Sex.MALE,
            power = "Bat powers"
        )
        StepVerifier.create(superHeroRepository.save(batman))
            .expectNextMatches { hero -> !hero.id.equals("") }
            .verifyComplete()
    }
}