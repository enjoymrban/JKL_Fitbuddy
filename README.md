# JKL_Fitbuddy
The goal of this webapplication is to create a platform where sport enthusiasts can meet with others very easy.
From our own experiences we know that it can be difficult to find a suitable sport buddy or just some hobby sport people. With this application, we created a place to share sport with different people without loosing our flexibility or being bound to another person.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You need a environment that can handle sbt projects. We recommend using Intelij.
You also need to have sbt installed to run the application.

### Installing

Clone the repository to your preferred folder.

```
git clone https://github.com/enjoymrban/JKL_Fitbuddy
```
You also need to have a PostgreSQL database on Heroku. Set the following 
system environment variable on your computer:
```
DATABASE_URL = your_database_url_from_heroku_with_user_and_password
```
You will have to update this value when Heroku cycles your database credentials. This will 
not be necessary on the Heroku server later when you deployed your application. If you wish not 
to use a deployed database, you will have to use a local PostgreSQL database and provide the 
needed information in conf/application.conf. The needed information is in comments, so it is easy to switch.

Now import the project to your environment. 
To start the app just run:

```
sbt run
```

The app should now be available on: http://localhost:9000
## API

### Single page routes
```
(**)  GET     /                      
```
- Response: index.html
```
(*) GET     /fitbuddies                
```
- Response: fitbuddies.html
```
(*) GET     /myprofile                
```
- Response: myprofile.html
```
(*) GET     /myevents                   
```
- Response: myevents.html 
### Events

#### Get all Events
```
(**) GET     /api/event               
```
 - Response logged in:
```javascript
[{
    id: 1,
    category: {
        id: 1,
        title: "Fussball"
    },
    creator: {
        id: 3,
        description: "Bester Basler Export",
        firstName: "Roger",
        lastName: "Federer",
        fullName: "Roger Federer",
        email: "fedi@ch",
        avatarUrl: "https://i.ytimg.com/vi/UV79c5Ubn7s/hqdefault.jpg",
        providerId: "facebook",
        authUserId: "632318664554645",
        categories: [{
                id: 3,
                title: "Tennis"
            },
            {
                id: 5,
                title: "Sonstiges"
            }
        ]
    },
    description: "Fussballspielen mit Profis",
    date: "12.09.2018",
    nrOfPlayers: 22,
    coordinateX: 12.3456789,
    coordinateY: 98.7654321
},{...}]
```

- Response not logged in:
```javascript
[{
        id: 1,
        category: {
            id: 1,
            title: "Fussball"
        },
        creator: null,
        description: null,
        date: null,
        nrOfPlayers: 22,
        coordinateX: 12.3456789,
        coordinateY: 98.7654321
    },{...}]
 ```

---

#### Get an event by id
```
(*) GET     /api/event/:id             
```
- Params Required:
`[id]=[eventId]`
- Response logged in:
```javascript
{
    id: 1,
    category: {
        id: 1,
        title: "Fussball"
    },
    creator: {
        id: 3,
        description: "Bester Basler Export",
        firstName: "Roger",
        lastName: "Federer",
        fullName: "Roger Federer",
        email: "fedi@ch",
        avatarUrl: "https://i.ytimg.com/vi/UV79c5Ubn7s/hqdefault.jpg",
        providerId: "facebook",
        authUserId: "632318664554645",
        categories: [{
                id: 3,
                title: "Tennis"
            },
            {
                id: 5,
                title: "Sonstiges"
            }
        ]
    },
    description: "Fussballspielen mit Profis",
    date: "12.09.2018",
    nrOfPlayers: 22,
    coordinateX: 12.3456789,
    coordinateY: 98.7654321,
    interested: [
        1,
        6
    ],
    participants: []
}
 ```
 - Response not logged in:
    Status Code 303, redirect to the login page
---
#### Create an Event
```
(*) POST	/api/event                  
```

- Example Request:
```javascript
let event = {
            description: "testDescription",
            category: {
                id: 1,
                title: "Fussball" // Not necessary unless you want to use the entire event in the response
            },
            creator: {
                id: 1
            },
            date: 2018-12-12,
            nrOfPlayers: 22,
            coordinateX: 47.5585247,
            coordinateY: 9.2545698
        };

        $.ajax({
            type: "POST",
            url: url + "/api/event",
            data: JSON.stringify(event),
            contentType: "application/json",
            dataType: 'json'
        })
 ```
 The response contains the posted event

- Response not logged in:
        Status Code 303, redirect to the login page
#### Update an event
```
(*) PUT	    /api/event/:id             
```
an event can only be updated by its creator!
- Params Required:
`[id]=[eventId]`

- Example Request:
```javascript
same as the POST event request
```
- Response not logged in:
        Status Code 303, redirect to the login page

#### Delete an event
```
(*) DELETE	/api/event/:id             
```
only the creator can delete his events
- Response logged in: Status Code 200
- Response not logged in:
        Status Code 303, redirect to the login page
        
#### Route to show interest in an event        
```
(*) GET     /api/joinEvent/:id         
```
Only the Id of the User calling the Request will be put into the interested array of the event

- Params Required:
`[id]=[eventId]`
- Response not logged in:
        Status Code 303, redirect to the login page
        

#### Route to withdraw the interest in an event
```
(*) GET     /api/leaveEvent/:id       
```
Only the Id of the User calling the Request will be removed from the interested array of the event
- Params Required:
`[id]=[eventId]`
- Response not logged in:
        Status Code 303, redirect to the login page
        

### User
#### Get all Users
```
(*) GET		/api/user                
```
- Response logged in:
````javascript
[{
        id: 1,
        description: "Ehemalige Miss Ostschweiz",
        firstName: "test",
        lastName: "user",
        fullName: "test user",
        email: "test.user@bla.ch",
        avatarUrl: "https://i.ytimg.com/vi/UV79c5Ubn7s/hqdefault.jpg",
        providerId: "facebook",
        authUserId: "029387450923475",
        categories: [{
                id: 1,
                title: "Fussball"
            },
            {
                id: 4,
                title: "Jogging"
            }
        ]
    },{...}]
````
- Response not logged in:
        Status Code 303, redirect to the login page

#### Get user by id
```
(*) GET		/api/user/:id             
```
- Params Required:
`[id]=[userId]`
- Response logged in:
````javascript
{
        id: 1,
        description: "Ehemalige Miss Ostschweiz",
        firstName: "test",
        lastName: "user",
        fullName: "test user",
        email: "test.user@bla.ch",
        avatarUrl: "https://i.ytimg.com/vi/UV79c5Ubn7s/hqdefault.jpg",
        providerId: "facebook",
        authUserId: "029387450923475",
        categories: [{
                id: 1,
                title: "Fussball"
            },
            {
                id: 4,
                title: "Jogging"
            }
        ]
    }
````

- Response not logged in:
        Status Code 303, redirect to the login page

#### Update user
```
(*) PUT		/api/user/:id            
```
Only the user with the id :id is allowed to update his profile. He is allowed to update his description and his favorite sports.
- Params Required:
`[id]=[userId]`
- Response not logged in:
        Status Code 303, redirect to the login page


### Categories
#### Get all categories
```
GET     /api/category           
```
- Response: 
````javascript
[{
        id: 1,
        title: "Fussball"
    },
    {
        id: 2,
        title: "Basketball"
    },
    {
        id: 3,
        title: "Tennis"
    },
    {
        id: 4,
        title: "Jogging"
    },
    {
        id: 5,
        title: "Sonstiges"
    }]
````
#### Get category by id
```
GET     /api/category/:id        
```
- Params Required:
`[id]=[categoryId]`
- Response:
````javascript
    {
        id: 1,
        title: "Fussball"
                        }
````



### SecureSocial
#### Check whether or not a user is logged in
```
GET        /userAware          
```
- Response logged in: Returns the User object
- Response not logged in: Retuns `not authenticated` on a seperate page

(\*)Route secured with SecureSocial and only accessible while logged in with a valid facebook account
(\***) Route is aware if the user is logged in or not and serves him with different information
## Running the tests

The tests for the project can be found in test/controllers/SecureSocialTests. The goal of the tests is to see if the routes are secured and only usable if logged in.
There are certain routes that return a response to a non logged-in user (see API). To run the test simply right-click and press Run.
For the tests to be successful, there has to be a deployed database on Heroku that is used in the DATABASE_URL environment variable.

### What is being tested

If the route is secured with @SecuredAction it should return a status code of 303 See Other.

```
assertEquals(303, result.status());
```

If the route is annotated with @UserAwareAction it should return a status code of 200 OK.

```
assertEquals(200, result.status());
```

## Deployment

To deploy this application to Heroku, create an application with a PostgreSQL addon.Change the needed URLs to your 
applications URL in public/javascripts/main.js and conf/securesocial.conf. 
Additionaly, register your application on developers.facebook.com and generate a clientId and a clientSecret and set the values 
in conf/securesocial.conf. 
Then you can use 
```
sbt stage deployHeroku
```
to deploy your application. Make sure to use https when going to your application so the Facebook login works.

## Built With

* [leaflet 1.3.1](https://leafletjs.com/) - The javascript library used
* [SecureSocial](https://maven.apache.org/) - Authentication for Play Framework Applications
* [Bootstrap 4.0.0](https://getbootstrap.com/) - Toolkit for developing with HTML, CSS, JS
* [Postgres 42.2.5](https://www.postgresql.org/) - Open Source Relational Database


## Authors

* [**Silvan Knecht**](https://github.com/enjoymrban)
* [**Silvio Jäger**](https://github.com/silviojaeger)
* [**Jann Lemm**](https://github.com/jannlemm0913)

See also the list of [contributors](https://github.com/enjoymrban/JKL_Fitbuddy/graphs/contributors) who participated in this project.

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc




