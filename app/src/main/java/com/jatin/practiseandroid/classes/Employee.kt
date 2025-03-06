package com.jatin.practiseandroid.classes

data class Employee(

    var studentID: String ?= null,
    val name : String ?= null,
    val surName: String ?= null
) {
    constructor(): this("","","")
}
