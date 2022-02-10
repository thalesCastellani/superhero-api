package com.thanosdev.reactivesuperheroes.api.dto

import com.thanosdev.reactivesuperheroes.api.entity.Biography
import com.thanosdev.reactivesuperheroes.api.entity.Sex
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class SuperHeroForm(

    @field:NotNull @field:NotEmpty
    var name: String?,

    @field:NotNull @field:NotEmpty
    var codename: String?,

    var age: Int?,

    @field:NotNull @field:NotEmpty
    var homeLand: String?,

    var sex: Sex?,

    @field:NotNull @field:NotEmpty
    var power: String?,

    var biography: Biography
)
