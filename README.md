# JKL_Fitbuddy

Das Ziel der Webapplikation ist es, eine Plattform zu schaffen, auf der sich Sportbegeisterte unkompliziert mit gleichgesinnten zum Sport verabreden können.
Aus eigener Erfahrung wissen wir, dass es schwierig sein kann einen geeigneten Trainingspartner oder aber auch einfach einen Freizeitsportler zu finden. Durch die Webapplikation ergibt sich die Möglichkeit sein geliebtes Hobby mit unterschiedlichen Personen zu teilen ohne dabei seine Unabhängigkeit zu verlieren und auf eine andere Person angewiesen zu sein.


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
Give examples
```

### Installing

A step by step series of examples that tell you how to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## API


### Single page routes
```
GET     /                           controllers.SinglePageController.index
```
* Returns the index page
```
GET     /fitbuddies                 controllers.SinglePageController.fitbuddies
```
* Returns the fitbuddies page
```
GET     /myprofile                  controllers.SinglePageController.myprofile
```
* Returns the myprofile page
```
GET     /myevents                   controllers.SinglePageController.myevents
```
* Returns the myevents page
### Events

```
GET     /api/event                  controllers.EventController.getAllEvents()
```
```
GET     /api/event/:id              controllers.EventController.getOneEvent(id: Long)
```
```
POST	/api/event                  controllers.EventController.addEvent()
```
```
PUT	    /api/event/:id              controllers.EventController.changeEvent(id: Long)
```
```
DELETE	/api/event/:id              controllers.EventController.deleteEvent(id: Long)
```
```
GET     /api/joinEvent/:id          controllers.EventController.joinEvent(id: Long)
```
```
GET     /api/leaveEvent/:id         controllers.EventController.leaveEvent(id: Long)
```

### User
```
GET		/api/user                  controllers.UserController.getAllUsers()
```
```
GET		/api/user/:id              controllers.UserController.getOneUser(id: Long)
```
```
PUT		/api/user/:id              controllers.UserController.changeUser(id: Long)
```


###Categories
```
GET     /api/category            controllers.CategoryController.category()
```
```
GET     /api/category/:id        controllers.CategoryController.getCategory(id: Long)
```

### SecureSocial
```
GET        /userAware           @controllers.Application.userAware
```


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

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc




