package com.thanosdev.reactivesuperheroes.api.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class SuperHero (
    @Id
    var id: String? = null,
    var name: String? = null,
    var codename: String? = null,
    var age: Int? = null,
    var homeLand: String? = null,
    var sex: Sex? = null,
    var power: String? = null
)



