# AWS Lambda samples

This module contains simple lambdas before diving in to complex scenarios. 


Function | Description 
--- | --- 
SimpleKotlinLambda.handleCheckAnagram | Function accepts a pair of strings and return if they are anagrams of each other. 
ContextAndEnvLambda.handleContextRequest | Lambda to return some of the context (wrt Lambda) information to the caller, if the input matches the environment variable. 

### Steps to follow
* Install AWS CLI and SAM CLI and configure aws credentials.
* Create the file template.yaml to specify the lambda properties
```
AWSTemplateFormatVersion: 2010-09-09
Transform: AWS::Serverless-2016-10-31
Description: AWS Simple Lambdas

Resources:
  SimpleLambda:
    Type: AWS::Serverless::Function
    Properties:
      FunctionName: SimpleLambda
      Runtime: java8
      MemorySize: 512
      Handler: com.efxcode.simple.SimpleKotlinLambda::checkIfAnagram #the method to invoke
      CodeUri: target/simple-lambda.jar

```
* `./mvnw package`
* `sam deploy --s3-bucket your_bucket_name --stack-name SimpleLambda --capabilities CAPABILITY_IAM --region your_region`

* Invoke the function
``` 
aws lambda invoke --invocation-type RequestResponse \
    --function-name SimpleLambda \
    --payload '{"adda": "aadd"}'  outputfile.txt \
  --region your_region \
  --cli-binary-format raw-in-base64-out
```
* Once the testing completes, delete the stack `aws cloudformation delete-stack --stack-name SimpleLambda --region your_region`



