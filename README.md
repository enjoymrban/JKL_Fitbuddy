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
You also need to have the following system environment variable set:
```
SYSTEMENVIROMENTVARIABLE
```

Now import the project to your enviroment. 
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

####Get all Events
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


###Categories
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

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

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




