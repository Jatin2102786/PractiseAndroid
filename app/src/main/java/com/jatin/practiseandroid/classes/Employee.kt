package com.jatin.practiseandroid.classes

data class Employee(
    val name : String ?= null,
    val surName: String ?= null
) {
    constructor(): this("","")
}
