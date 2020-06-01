# Lambda API Usage

Explore the usage of API Gateway and dynamo db to store and retrieve the data. <br>

![](docs/images/awslambda_api.png?raw=true)

### Steps to follow
* Dependencies required: aws-java-sdk-dynamodb, aws-lambda-java-core
* SAM Template
```
AWSTemplateFormatVersion: 2010-09-09
Transform: AWS::Serverless-2016-10-31
Description: lambda-api-usage

Globals:
  Function:
    Runtime: java8
    MemorySize: 512
    Timeout: 25
    Environment:
      Variables:
        LOCATIONS_TABLE: !Ref LocationsTable
  Api:
    OpenApiVersion: '3.0.3'

Resources:
  LocationsTable:
    Type: AWS::Serverless::SimpleTable
    Properties:
      PrimaryKey:
        Name: location
        Type: String

  WeatherEventLambda:
    Type: AWS::Serverless::Function
    Properties:
      CodeUri: target/lambda.zip
      Handler: com.efxcode.aws.lambdas.WeatherDataEventLambda::handler
      Policies:
        - DynamoDBCrudPolicy:
            TableName: !Ref LocationsTable
      Events:
        ApiEvents:
          Type: Api
          Properties:
            Path: /events
            Method: POST

  WeatherQueryLambda:
    Type: AWS::Serverless::Function
    Properties:
      CodeUri: target/lambda.zip
      Handler: com.efxcode.aws.lambdas.WeatherDataQueryLambda::handler
      Policies:
        - DynamoDBReadPolicy:
            TableName: !Ref LocationsTable
      Events:
        ApiEvents:
          Type: Api
          Properties:
            Path: /locations
            Method: GET
```
* `./mvnw package`
* `sam deploy --s3-bucket your_bucket_name --stack-name lambda-api --capabilities CAPABILITY_IAM --region your_region`
*  Test the APIs
ex: 
```
POST https://xyz1222.execute-api.eu-west-2.amazonaws.com/Prod/events
{
  "temperature": 10.77,
  "timestamp": 1591047508496,
  "latitude": -21.400,
  "longitude": 10.401,
  "location": "Venice"
}

GET https://xyz1222.execute-api.eu-west-2.amazonaws.com/Prod/locations
[
    {
            "temperature": 10.77,
            "timestamp": 1591047508496,
            "latitude": -21.4,
            "longitude": 10.401,
            "location": "Venice"
     }
]
```

