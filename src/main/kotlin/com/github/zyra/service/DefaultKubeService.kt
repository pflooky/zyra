package com.github.zyra.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.zyra.config.PatchConfig
import com.github.zyra.model.AdmissionReviewPatch
import com.github.zyra.model.AdmissionReviewRequest
import com.github.zyra.model.AdmissionReviewResponse
import com.github.zyra.model.Patch
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class DefaultKubeService(private val patchConfig: Map<String, List<Patch>>) : KubeService {

    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val objectMapper = ObjectMapper()

    override fun mutateKubeResource(
        admissionReviewRequest: AdmissionReviewRequest,
        type: String
    ): AdmissionReviewResponse {
        val allJsonPatches = type.split(",")
            .flatMap { patchType ->
                val trimType = patchType.trim()
                val baseLog = "task=get-json-patch, type=$trimType, name=${admissionReviewRequest.request.name}"
                val jsonPatch = patchConfig[trimType]
                if (jsonPatch.isNullOrEmpty()) {
                    logger.warn("$baseLog, msg=No corresponding patch found for type. Will continue with empty patch")
                } else {
                    logger.info("$baseLog, msg=Found patch config. Will apply to Kubernetes resource")
                }
                jsonPatch.orEmpty()
            }

        return AdmissionReviewResponse(
            response = AdmissionReviewPatch(
                admissionReviewRequest.request.uid,
                true,
                "JSONPatch",
                objectMapper.writeValueAsString(allJsonPatches)
            )
        )
    }
}