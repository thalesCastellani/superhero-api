package com.thanosdev.reactivesuperheroes.api.dto

import com.thanosdev.reactivesuperheroes.api.entity.Biography
import com.thanosdev.reactivesuperheroes.api.entity.Sex

data class SuperHeroView(
    var id: String?,
    var codename: String?,
    var age: Int?,
    var homeLand: String?,
    var sex: Sex?,
    var power: String?
    //var biography: Biography
)











