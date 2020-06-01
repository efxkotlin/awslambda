package com.efxcode.aws.lambdas

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.Table
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.efxcode.aws.LambdaEnvironment.DATATABLE_NAME
import com.efxcode.aws.domain.WeatherDataEvent
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper

class WeatherDataEventLambda {
    private val objectMapper: ObjectMapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    private val dynamoDB = DynamoDB(AmazonDynamoDBClientBuilder.defaultClient())

    fun handler(request: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent? {
        val weatherData = objectMapper.readValue(request.body, WeatherDataEvent::class.java)
        val table = dynamoDB.getTable(DATATABLE_NAME)
        table.addWeatherData(weatherData)
        return APIGatewayProxyResponseEvent().withStatusCode(200).withBody(weatherData.location)
    }

    private fun Table.addWeatherData(weatherDataData: WeatherDataEvent) {
        val item = Item()
                .withPrimaryKey("location", weatherDataData.location)
                .withDouble("temperature", weatherDataData.temperature.toDouble())
                .withDouble("latitude", weatherDataData.latitude?.toDouble()!!)
                .withDouble("longitude", weatherDataData.longitude?.toDouble()!!)
                .withLong("timestamp", weatherDataData.timestamp?.toLong()!!)
        this.putItem(item)
    }
}