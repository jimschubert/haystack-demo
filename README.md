# haystack demo

## Build

```bash
./gradlew assemble
docker build -t hsd .
docker run -p 8080:8080 hsd
```

## Queries

* http://localhost:8080/v1/products
  - Default: offset=0,limit=20
* http://localhost:8080/v1/products?offset=0&limit=1
  - "Special" case, queries 2 records at a time, 10 times (serial database calls)
