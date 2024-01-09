# Amount Counter API

## API description

Small API for requesting data or file

## Available routes

### Get products list

> GET http://0.0.0.0:8080/api/getProducts

```shell
curl -X 'GET' \
  'http://0.0.0.0:8080/api/getProducts' \
  -H 'accept: application/json'
```

### Get products summary pdf file

> POST http://0.0.0.0:8080/api/getSummary

```shell
curl -X 'POST' \
  'http://0.0.0.0:8080/api/getSummary' \
  -H 'accept: application/pdf' \
  -H 'Content-Type: application/json' \
  -d '{
  "ids": [
    "5d6aa284-0f05-49f5-b563-f83c3d2ffd90",
    "77ac3a82-6a9c-427e-b281-a0a73b8a5847",
    "1e0b7134-cfd1-4a0b-89b2-9c2616478f36"
  ]
}'
```

### SwaggerUI:

<http://0.0.0.0:8080/docs/swagger>

### Metrics:

<http://0.0.0.0:8080/metrics-micrometer>

## Build

#### local build:

```shell
gradle build --no-daemon
```

```shell
java -jar /app/amount-counter-all.jar
```

#### docker build:

```shell
docker build -t amount-counter .
```

```shell
docker run -dp 8080:8080 amount-counter
```

## Additional info

*Project uses ktlint and detekt rules

