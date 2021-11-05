package com.github.zyra.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.zyra.model.Patch
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.io.File

@Component
class PatchConfig {

    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val objectMapper = ObjectMapper()

    @Bean
    fun getPatches(): Map<String, List<Patch>> {
        val patchList: List<Patch> = mutableListOf()
        return javaClass.getResource("/patch")?.path?.let { patchPath ->
            File(patchPath)
                .listFiles()
                ?.filter { it.extension == "json" }
                ?.associate { file ->
                    logger.debug("task=read-patch-config, file=${file.name}, msg=Attempting to process patch config from file")
                    try {
                        objectMapper.readValue(file.inputStream(), patchList.javaClass)
                        Pair(file.nameWithoutExtension, objectMapper.readValue(file.inputStream(), patchList.javaClass))
                    } catch (ex: Exception) {
                        throw Exception("task=read-patch-config, file=${file.name}", ex)
                    } finally {
                        logger.info("task=read-patch-config, file=${file.name}, " +
                                "map-key=${file.nameWithoutExtension}, msg=Processed patch config from file")
                    }
                }
        }.orEmpty()
    }
}