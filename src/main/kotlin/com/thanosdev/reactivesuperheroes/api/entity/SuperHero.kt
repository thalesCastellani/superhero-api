package com.thanosdev.reactivesuperheroes.api.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class SuperHero (
    @Id
    val id: String? = null,
    val name: String? = null,
    val codename: String? = null,
    val age: Int? = null,
    val homeLand: String? = null,
    val sex: Sex? = null,
    val power: String? = null
)