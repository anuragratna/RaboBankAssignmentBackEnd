# Rabobank Customer Statement Processor
Rabobank receives monthly deliveries of customer statement records. This information is delivered in two formats, CSV and XML. These records need to be validated.
 
### Setup
1- To set up applcation locally, first build the application with following command:
mvn clean install

2- To Run the spring boot application run following command:
mvn spring-boot:run
 
Application runs on PORT 8080

## Input
The format of the file is a simplified format of the MT940 format. The format is as follows:

Field  |Description
----|----
Transaction reference  | A numeric value
Account number   | An IBAN 
Account | IBAN 
Start Balance | The starting balance in Euros 
Mutation | Either and addition (+) or a deducation (-) 
Description | Free text 
End Balance | The end balance in Euros 

## Expected output
There are two validations:
* all transaction references should be unique
* the end balance needs to be validated

At the end of the processing, a report needs to be created which will display both the transaction reference and description of each of the erroneous records and also display 
duplicate records.

There are two endpoints to connect:

1- POST /rabobank/uploadStatement
This endpoint expect a multipart file as input and extract the records only from csv and xml file and validate the uploaded file.
2- GET /rabobank/test
To test the connection status