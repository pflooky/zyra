package com.github.zyra.service

import com.github.zyra.model.AdmissionReviewRequest
import com.github.zyra.model.AdmissionReviewResponse

interface KubeService {
    fun mutateKubeResource(admissionReviewRequest: AdmissionReviewRequest, type: String): AdmissionReviewResponse
}