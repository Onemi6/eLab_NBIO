package com.example.eLab_NBIO.models

class Lab {
    lateinit var ID: String

    lateinit var NAME: String

    var CODE: String? = null

    var ADDR: String? = null

    override fun toString(): String {
        return NAME
    }
}