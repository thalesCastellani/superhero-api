package com.thanosdev.reactivesuperheroes.api.service

import com.thanosdev.reactivesuperheroes.api.entity.Biography
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class SuperHeroService {

    private val webClient: WebClient = WebClient.create("https://akabab.github.io/superhero-api/api")

    fun getSuperHeroBiography(id: String): Mono<Biography> {
        val path = "/biography/${id}.json"

        return webClient.get()
            .uri(path, id)
            .retrieve()
            .bodyToMono(Biography::class.java)
            .map { bio ->
                Biography(
                    bio.fullName,
                    bio.alterEgos,
                    bio.aliases,
                    bio.placeOfBirth,
                    bio.firstAppearance,
                    bio.publisher,
                    bio.alignment
                )
            }
    }

}