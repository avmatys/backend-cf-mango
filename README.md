# backend-cf-mango

## Description
Simple Java Spring Boot application, which catches Webhooks from Mango and routes to the SAP Integration Suite (Neo).
The same functionality can be achieved using SAP API Management in the SAP Integration Suite, hosted in Cloud Foundry.

## Settings
There are 2 profiles: dev and prod.
Name of the prop file should be: application-ENV.propertis.

# Properties list
- cpi.baseUrl=https://CPI.*.hana.ondemand.com
- cpi.accountDataPath=IFLOW_PATH
- cpi.username=BA_AUTH
- cpi.password=BA_AUTH
- logging.file.name=LOG_PATH



