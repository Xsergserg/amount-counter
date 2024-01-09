# Amount Counter API

## API description

Small API for requesting data or file

## Available routes

### Get products list

`GET: http://0.0.0.0:8080/api/getProducts`

### Get products summary pdf file

`GET: http://0.0.0.0:8080/api/getSummary?ids=1,2,3,4,5`

### SwaggerUI:

`http://0.0.0.0:8080/docs/swagger`

### Metrics:

`http://0.0.0.0:8080/metrics-micrometer`

## Build
#### local build:
`gradle build --no-daemon`

`java -jar /app/amount-counter-all.jar`

#### docker build:

`docker build -t amount-counter .`

`docker run -dp 8080:8080 amount-counter`

## Additional info
*Project uses ktlint and detekt rules

