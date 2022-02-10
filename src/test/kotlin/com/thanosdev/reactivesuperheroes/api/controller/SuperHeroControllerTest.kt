package com.thanosdev.reactivesuperheroes.api.controller

import com.thanosdev.reactivesuperheroes.api.entity.Sex
import com.thanosdev.reactivesuperheroes.api.entity.SuperHero
import com.thanosdev.reactivesuperheroes.api.repository.SuperHeroRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import java.util.*

//@WebFluxTest(SuperHeroController::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SuperHeroControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var superHeroRepository: SuperHeroRepository

    private val superHeroes: List<SuperHero> = Arrays.asList(
        SuperHero(
            name ="Peter Parker",
            codename = "Spider-man",
            age = 15,
            homeLand = "NY",
            sex = Sex.MALE,
            power = "Spider-sense"
        ),
        SuperHero(
            name ="Tony Stark",
            codename = "Iron Man",
            age = 40,
            homeLand = "Bulgary",
            sex = Sex.MALE,
            power = "Freeze-Beam"
        ),
        SuperHero(
            name ="Blackagar Boltagon",
            codename = "Black Bolt",
            age = 38,
            homeLand = "Attilan",
            sex = Sex.MALE,
            power = "Hypersonic scream"
        )
    )

    @BeforeEach
    fun setUp() {
        superHeroRepository.deleteAll()
            .thenMany(Flux.fromIterable(superHeroes))
            .flatMap { s -> superHeroRepository.save(s) }
            .doOnNext { s -> println(s) }
            .then()
            .block()
    }

    @Test
    fun `Should retrieve all superHeroes`() {
        webTestClient.get().uri("/superheroes")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(SuperHero::class.java)
            .hasSize(3)
    }

}