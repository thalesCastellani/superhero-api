package com.thanosdev.reactivesuperheroes.api

import com.thanosdev.reactivesuperheroes.api.entity.Sex
import com.thanosdev.reactivesuperheroes.api.entity.SuperHero
import com.thanosdev.reactivesuperheroes.api.repository.SuperHeroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class SuperHeroInit(@Autowired private val superHeroRepository: SuperHeroRepository) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        superHeroRepository.deleteAll()
            .thenMany(Flux.just(
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
                )))
            .flatMap { superHeroRepository.save(it) }
            .then()
            .doOnEach { println(it) }
            .block()
    }

}