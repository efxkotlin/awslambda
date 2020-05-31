# simple-lambdas-zip

Creates and deploys a "Hello World" AWS Lambda function, implemented in Kotlin.

The output artifact is "reproducible" - it will be identical for the same source inputs - and also uses
a zip file format rather than an "Uberjar".

### Steps to follow

* `export CF_BUCKET= your_s3_bucket_name`
* Created: An AWS account, and an S3 bucket for storing temporary deployment artifacts (referred to as $CF_BUCKET below)
* Installed: AWS CLI, SAM CLI
* Configured: AWS credentials

* `./mvnw package`
* `sam deploy --s3-bucket your_bucket_name --stack-name SimpleLambdaZip --capabilities CAPABILITY_IAM --region your_region`

* Invoke the function
``` 
aws lambda invoke --invocation-type RequestResponse \
    --function-name SimpleLambdaZip \
    --payload 'world'  outputfile.txt \
  --region your_region \
  --cli-binary-format raw-in-base64-out
```
* Once the testing completes, delete the stack `aws cloudformation delete-stack --stack-name SimpleLambdaZip --region your_region`

