package com.thanosdev.reactivesuperheroes.api.dto

import com.thanosdev.reactivesuperheroes.api.entity.Sex

data class SuperHeroDto(
    var id: String?,
    var codename: String?,
    var age: Int?,
    var homeLand: String?,
    var sex: Sex?,
    var power: String?
)











