package com.github.zyra.model

import java.util.*

data class AdmissionReviewRequest(
    val apiVersion: String,
    val kind: String,
    val request: AdmissionRequest
)

data class AdmissionRequest(
    val uid: String = UUID.randomUUID().toString(),
    val kind: Map<String, String> = emptyMap(),
    val resource: Map<String, String> = emptyMap(),
    val subResource: String = "",
    val requestKind: Map<String, String> = emptyMap(),
    val requestResource: Map<String, String> = emptyMap(),
    val requestSubResource: String = "",
    val name: String = "",
    val namespace: String = "",
    val operation: String = "",
    val userInfo: Map<String, Any> = emptyMap(),
    val `object`: Map<String, Any> = emptyMap(),
    val oldObject: Map<String, Any> = emptyMap(),
    val dryRun: String = ""
)