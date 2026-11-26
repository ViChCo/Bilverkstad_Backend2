# Car Workshop – Spring Data JPA

A REST API for a car workshop that manages customers and vehicles.
Built with Spring Boot, Spring Data JPA and H2 (in-memory database).

## Starting the project

**Windows:** double-click `start.bat` or run it in the terminal:
```
start.bat
```

**Mac/Linux:**
```
./start.sh
```

Alternatively, run `DomainApplication` from IntelliJ.

The application starts on http://localhost:8080

The database is stored in memory, so all data is lost when the application stops.

## Endpoints

| Function | Method | URL |
|---|---|---|
| Create customer | POST | /customers?name=Anna&phone=123456789 |
| Get customer ID by name | GET | /customer-id?name=Anna |
| List all customers with their vehicles | GET | /customers |
| Create vehicle | POST | /vehicles?registrationNumber=ABC123&brand=Volvo&model=V60&productionYear=2022 |
| List all vehicles | GET | /vehicles |
| List vehicles by brand | GET | /vehicles-by-brand?brand=Volvo |
| Add vehicle to customer | POST | /addvehicletocustomer?customerId=1&vehicleId=1 |
| Update vehicle brand | PUT | /vehicles/1/brand?brand=Saab |
| List registration numbers and brands | GET | /vehicle-brand-info |

## Example requests (curl)

```
curl.exe -X POST "http://localhost:8080/customers?name=Anna&phone=123456789"
curl.exe "http://localhost:8080/customer-id?name=Anna"
curl.exe -X POST "http://localhost:8080/vehicles?registrationNumber=ABC123&brand=Volvo&model=V60&productionYear=2022"
curl.exe -X POST "http://localhost:8080/addvehicletocustomer?customerId=1&vehicleId=1"
curl.exe "http://localhost:8080/customers"
curl.exe "http://localhost:8080/vehicles"
curl.exe "http://localhost:8080/vehicles-by-brand?brand=Volvo"
curl.exe -X PUT "http://localhost:8080/vehicles/1/brand?brand=Saab"
curl.exe "http://localhost:8080/vehicle-brand-info"
```

(On Mac/Linux, use `curl` instead of `curl.exe`.)

## Testing
The file `test.http` contains all requests and can be run directly in IntelliJ.
Run the requests in order, from top to bottom.