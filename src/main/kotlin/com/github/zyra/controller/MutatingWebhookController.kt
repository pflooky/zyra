package com.github.zyra.controller

import com.github.zyra.model.AdmissionReviewRequest
import com.github.zyra.model.AdmissionReviewResponse
import com.github.zyra.service.KubeService
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kube")
class MutatingWebhookController(
    private val kubeService: KubeService
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @PostMapping(value = ["/patch/{type}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun patchKubeResource(@RequestBody request: AdmissionReviewRequest, @PathVariable type: String): AdmissionReviewResponse {
        logger.info("task=received-patch-request, type(s)=$type, name=${request.request.name}, msg=Received request to patch Kubernetes resource")
        return kubeService.mutateKubeResource(request, type)
    }
}