package com.github.zyra.model

data class AdmissionReviewResponse(
    val apiVersion: String = "admission.k8s.io/v1",
    val kind: String = "AdmissionReview",
    val response: AdmissionReviewPatch
)

data class AdmissionReviewPatch(
    val uid: String,
    val allowed: Boolean,
    val patchType: String,
    val patch: String
)