package com.github.zyra.service

import com.github.zyra.model.AdmissionRequest
import com.github.zyra.model.AdmissionReviewRequest
import com.github.zyra.model.AdmissionReviewResponse
import com.github.zyra.model.Patch
import org.junit.Test

import org.junit.jupiter.api.Assertions.*

internal class DefaultKubeServiceTest {

    private val kubeService = DefaultKubeService(
        mapOf(
            "apps" to listOf(Patch("add", "/spec/volumes/-", "\"name\": \"tmp-dir\",\n\"emptyDir\": {}"))
        )
    )
    private val admissionReviewRequest = AdmissionReviewRequest(
        "api/v1", "Pod", AdmissionRequest()
    )

    @Test
    fun mutateKubeResourceFoundPatchType() {
        val result = kubeService.mutateKubeResource(admissionReviewRequest, "apps")
        baseAdmissionReviewResponseTests(result)
        assertEquals("[{\"op\":\"add\",\"path\":\"/spec/volumes/-\",\"value\":\"\\\"name\\\": \\\"tmp-dir\\\",\\n\\\"emptyDir\\\": {}\"}]",
            result.response.patch)
    }

    @Test
    fun mutateKubeResourceNotFoundPatchType() {
        val result = kubeService.mutateKubeResource(admissionReviewRequest, "jobs")
        baseAdmissionReviewResponseTests(result)
        assertEquals("[]", result.response.patch)
    }

    private fun baseAdmissionReviewResponseTests(result: AdmissionReviewResponse) {
        assertTrue(result.response.uid.isNotEmpty())
        assertTrue(result.response.allowed)
        assertEquals("JSONPatch", result.response.patchType)
    }
}