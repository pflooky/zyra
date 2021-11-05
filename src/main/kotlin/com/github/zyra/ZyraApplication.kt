package com.github.zyra

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ZyraApplication

fun main(args: Array<String>) {
	runApplication<ZyraApplication>(*args)
}
