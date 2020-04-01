# bitbox-api
BITBOX Selection process assignment java spring boot api


## Open Endpoints

* `POST /authenticate`

**Data example**

```json
{
    "username": "admin",
    "password": "admin"
}
```

#### Success Response

**Code** : `200 OK`

**Content example**

```json
{
    "token": "93144b288eb1fdccbe46d6fc0f241a51766ecd3d"
}
```

* `POST /register`
   body: User

## Endpoints that require Authentication

Closed endpoints require a valid Token to be included in the header of the
request. A Token can be acquired from the Login view above.

* `POST /items body:Item`
* `GET /items/{itemCode}`
* `GET /items/supplierCheapest/{supplierName}`
* `GET /items`
* `PUT /items/{itemCode} body:Item`
* `PUT /items/{itemCode}/deactivate`
* `DELETE /items/{itemCode}`


* `POST /users body:User`
* `GET /users/{id}`
* `GET /users`
* `DELETE /users/{id}`


* `GET /supplier/itemPriceReduced`

###Item
```json
{
    "itemCode": 1234,
    "description": "description",
    "price": 10,
    "suppliers": [
        {
            "name": "name",
            "country": "ES"
        }
    ],
    "priceReductions": [
        {
            "reducedPrice": 5,
            "startDate": "",
            "endDate": ""
        }
    ]
}
```
###Deactivate
```json
{
    "reason": "reason"
}
```
###User
```json
{
    "username": "admin",
    "password": "admin",
    "firstName": "firstName",
    "lastName": "lastName"
}
```

### Project structure
``` 
+───src
│   +───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───jarvi
│   │   │           └───bitboxapi
│   │   │               │   BitboxApiApplication.java
│   │   │               │
│   │   │               ├───config
│   │   │               │       JwtAuthenticationEntryPoint.java
│   │   │               │       JwtRequestFilter.java
│   │   │               │       JwtTokenUtil.java
│   │   │               │       SetupDataLoader.java
│   │   │               │       WebSecurityConfig.java
│   │   │               │
│   │   │               ├───controller
│   │   │               │   │   ItemController.java
│   │   │               │   │   JwtAuthenticationController.java
│   │   │               │   │   PingController.java
│   │   │               │   │   SupplierController.java
│   │   │               │   │   UserController.java
│   │   │               │   │
│   │   │               │   ├───exception
│   │   │               │   │       AlreadyExistException.java
│   │   │               │   │       InvalidDataException.java
│   │   │               │   │       InvalidStateException.java
│   │   │               │   │       NotFoundException.java
│   │   │               │   │
│   │   │               │   └───security
│   │   │               │           IsAdmin.java
│   │   │               │           IsUser.java
│   │   │               │
│   │   │               ├───model
│   │   │               │       DeactivateItemDTO.java
│   │   │               │       ItemDTO.java
│   │   │               │       JwtRequest.java
│   │   │               │       JwtResponse.java
│   │   │               │       PriceReductionDTO.java
│   │   │               │       SupplierDTO.java
│   │   │               │       UserDTO.java
│   │   │               │
│   │   │               ├───persistence
│   │   │               │   ├───entity
│   │   │               │   │       Item.java
│   │   │               │   │       PriceReduction.java
│   │   │               │   │       Privilege.java
│   │   │               │   │       Role.java
│   │   │               │   │       Supplier.java
│   │   │               │   │       User.java
│   │   │               │   │
│   │   │               │   └───repository
│   │   │               │           ItemRepository.java
│   │   │               │           PriceReductionRepository.java
│   │   │               │           PrivilegeRepository.java
│   │   │               │           RoleRepository.java
│   │   │               │           SupplierRepository.java
│   │   │               │           UserRepository.java
│   │   │               │
│   │   │               ├───service
│   │   │               │       ItemService.java
│   │   │               │       JwtUserDetailsService.java
│   │   │               │       PriceReductionService.java
│   │   │               │       SupplierService.java
│   │   │               │       UserService.java
│   │   │               │
│   │   │               └───util
│   │   │                       Period.java
│   │   │
│   │   └───resources
│   │       │   application.properties
│   │       │
│   │       ├───static
│   │       └───templates
│   └───test
│       └───java
│           └───com
│               └───jarvi
│                   └───bitboxapi
│                       │   BitboxApiApplicationTests.java
│                       │
│                       └───service
│                               ItemServiceTest.java
│                               PriceReductionServiceTest.java
```


### Build and Deploy the Project
```
mvn clean install
```

### Set up postgrest DataBase
By default, the project is configured to use the embedded H2 database.
If you want to use the Postgres instead, you need to uncomment relevant section in the [application.properties](src/main/resources/application.properties) and create the db bitbox as shown below:
```
> CREATE DATABASE bitbox
      WITH 
      OWNER = postgres
      ENCODING = 'UTF8'
      LC_COLLATE = 'English_United States.1252'
      LC_CTYPE = 'English_United States.1252'
      TABLESPACE = pg_default
      CONNECTION LIMIT = -1;
> GRANT ALL PRIVILEGES ON *.* TO 'bitbox'@'localhost';
> FLUSH PRIVILEGES;
```