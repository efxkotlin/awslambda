package com.efxcode.aws

object LambdaEnvironment {
    val DATATABLE_NAME: String = System.getenv("LOCATIONS_TABLE")
    const val DEFAULT_FETCH_LIMIT:String = "50"
}