package com.efxcode.aws.lambdas

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.model.ScanRequest
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.efxcode.aws.LambdaEnvironment.DATATABLE_NAME
import com.efxcode.aws.LambdaEnvironment.DEFAULT_FETCH_LIMIT
import com.efxcode.aws.domain.WeatherDataEvent
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper

class WeatherDataQueryLambda {

    private val objectMapper: ObjectMapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    private val dynamoDB:AmazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient()

    fun handler(request: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent {
        val scanRequest = ScanRequest().withTableName(DATATABLE_NAME).withLimit(request.limit())
        val scanResult = dynamoDB.scan(scanRequest)
        val weatherData = scanResult.items.asSequence().map {
            WeatherDataEvent(it["temperature"]?.n?.toBigDecimal()!!,it["timestamp"]?.n?.toLong(), it["latitude"]?.n?.toBigDecimal(),
            it["longitude"]?.n?.toBigDecimal(),it["location"]?.s?.toString()!!)
        }.toList()
        val jsonResult = objectMapper.writeValueAsString(weatherData)
        return APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonResult)
    }

    private fun APIGatewayProxyRequestEvent.limit(): Int {
        return this.queryStringParameters?.get("limit")?.toInt() ?: DEFAULT_FETCH_LIMIT.toInt()
    }
}