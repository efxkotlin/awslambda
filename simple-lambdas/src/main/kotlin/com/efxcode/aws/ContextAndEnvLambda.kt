package com.efxcode.aws

import com.amazonaws.services.lambda.runtime.Context

/**
 * Lambda to return some of the context information to the caller
 */
class ContextAndEnvLambda {
    fun handleContextRequest(input: String, context: Context):ContextInfo {
        val authData = System.getenv("REQ_AUTH_NAME")
        return when (input) {
            authData -> ContextInfo(context.logStreamName,context.logGroupName)
            else -> ContextInfo("Invalid","Invalid")
        }
    }
}

data class ContextInfo(val logStreamName:String, val logGroupName:String)
