## Connect 4 game for 2 player

colour supported yellow and red.

###### `Technology used`

- Java 8
- Maven
- Spring Boot
- Spring Web
- JPA
- H2 Mem DataBase

###### `To Run the Application`

``` maven
mvn spring-boot:run
```

### REST Services
##### Create Game
`method:POST`

    URI:/connect4/new?userId={userId}
    

#### Get Game
`method:GET`

    URI:/connect4/{gameId}

#### Join Game
method:``POST

    URI:/connect4/{gameId}/join/{userId}

#### Play Game
`method:POST`

    URI:/connect4/{gameId}/play/{userId}/{column}


#### Winner

Note: please check the msg in response for check the winner.

Snapshot of postman in shared under
`src/main/resources/static`

Deployment link will shared soon.
please contact me at amitzgupta001@gmail.com
 


