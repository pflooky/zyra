package com.github.zyra.model

data class Patch(
    val op: String,
    val path: String,
    val value: Any
)
