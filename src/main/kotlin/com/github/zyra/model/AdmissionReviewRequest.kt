package com.github.zyra.model

data class AdmissionReviewRequest(
    val apiVersion: String,
    val kind: String,
    val request: AdmissionRequest
)

data class AdmissionRequest(
    val uid: String,
    val kind: Map<String, String>,
    val resource: Map<String, String>,
    val subResource: String,
    val requestKind: Map<String, String>,
    val requestResource: Map<String, String>,
    val requestSubResource: String,
    val name: String,
    val namespace: String,
    val operation: String,
    val userInfo: Map<String, Any>,
    val `object`: Map<String, Any>,
    val oldObject: Map<String, Any>,
    val dryRun: String
)