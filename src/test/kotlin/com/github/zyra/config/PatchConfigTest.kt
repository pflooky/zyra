package com.github.zyra.config

import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import java.io.File

internal class PatchConfigTest {

    private val resourceLoader: ResourceLoader = mockk(relaxed = true)
    private val fileMock: Resource = mockk()
    private val sampleConfigFolder = File(javaClass.getResource("/sample/patch")?.path!!)
    private val badConfigFolder = File(javaClass.getResource("/sample/bad_patch")?.path!!)

    @Test
    fun getPatches() {
        every { resourceLoader.getResource(any()) } returns fileMock
        every { fileMock.file } returns sampleConfigFolder
        val result = PatchConfig(resourceLoader, "classpath:sample/patch").getPatches()
        assertEquals(1, result.size)
        assertEquals(1, result["simple-apps"]?.size)
    }

    @Test
    fun getPatchesFailToParse() {
        every { resourceLoader.getResource(any()) } returns fileMock
        every { fileMock.file } returns badConfigFolder
        assertThrows<Exception> {
            PatchConfig(resourceLoader, "classpath:sample/patch").getPatches()
        }
    }
}