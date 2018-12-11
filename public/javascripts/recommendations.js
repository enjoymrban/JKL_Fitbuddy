myId = sessionStorage.getItem('myId');
let sportData;

// Contains rectangles and circles --> used to show and hide for leaflet
// A LayerGroup can be added or removed completeley form the map
let recommendations = L.layerGroup();

// Contains all rectangles and circles with id for personal use e.g. connecting with popups
let recommendationLayers = [];

// Carousel Parts
let entireCarousel = $('#carouselExample');
let carouselBody = $("#carouselBody");

// Notification Parts
let recomInfoDiv = $('#recomInfoDiv');
let recomInfo = $('#recomInfo');

// Initialisation of the bootstrap carousel
// TODO a bug does stop the carousel in certain situations --> not identified yet
entireCarousel.on('slide.bs.carousel', function (e) {

    let $e = $(e.relatedTarget);
    let idx = $e.index();
    let itemsPerSlide = 4;
    let totalItems = $('.carousel-item').length;

    if (idx >= totalItems - (itemsPerSlide - 1)) {
        let it = itemsPerSlide - (totalItems - idx);
        for (let i = 0; i < it; i++) {
            // append slides to end
            if (e.direction == "left") {
                $('.carousel-item').eq(i).appendTo('.carousel-inner');
            }
            else {
                $('.carousel-item').eq(0).appendTo('.carousel-inner');
            }
        }
    }
});


$().ready(() => {

    // hide recommendations for small screens, it takes too much space
    // TODO change bootstrap css so that it fits on smaller screens
    window.onresize = function () {
        if ($(window).width() < 500) {

            recomInfoDiv.hide();
            recomInfo.hide();
        } else {
            recomInfoDiv.show();
            recomInfo.show();
        }
    };

    // Query that loads only nodes
    let nodesOnly = "http://overpass-api.de/api/interpreter?data=%2F*%0AThis%20has%20been%20generated%20by%20the%20overpass-turbo%20wizard.%0AThe%20original%20search%20was%3A%0A%E2%80%9Csport%3D*%20and%20country%3DSwitzerland%E2%80%9D%0A*%2F%0A%0A%5Bout%3Ajson%5D%5Bmaxsize%3A1073741824%5D%3B%0Aarea%283600051701%29-%3E.searchArea%3B%28node%5B%22sport%22%5D%28area.searchArea%29%3B%3E%3B%29%3Bout%20bb%3B";
    // Query to load everything sport=* from Switzerland
    let allData = "http://overpass-api.de/api/interpreter?data=%2F*%0AThis%20has%20been%20generated%20by%20the%20overpass-turbo%20wizard.%0AThe%20original%20search%20was%3A%0A%E2%80%9Csport%3D*%20and%20country%3D%22Switzerland%22%E2%80%9D%0A*%2F%0A%0A%5Bout%3Ajson%5D%5Bmaxsize%3A1073741824%5D%3B%0Aarea%283600051701%29-%3E.searchArea%3B%28node%5B%22sport%22%5D%28area.searchArea%29%3B%3E%3Bway%5B%22sport%22%5D%28area.searchArea%29%3B%3E%3Brelation%5B%22sport%22%5D%28area.searchArea%29%3B%3E%3B%29%3Bout%20bb%3B";
    // Local file with all data sport=* loads much faster!
    let localData = "/assets/recomData/export.json";
    $.ajax({
        url: localData,
        type: "GET",
        dataType: "json"
    }).done((json) => {


        // Checks if User is logged in if not don't show any recommendations
        userAware().done(() => {
            sportData = json;
            buildRecommendations();
            recomInfo.text("No recommendatons found! Add more favorite sports to your profile!");
            entireCarousel.hide();
            if ($(window).width() < 500) {
                recommendations.clearLayers();
                entireCarousel.hide();
                recomInfo.hide();
            }
            map.on('zoomend moveend', function () {
                if (map.getZoom() <= endZoom - 1 || $(window).width() < 500) {
                    recomInfo.text("Zoom closer for recommendations!");
                    recommendations.clearLayers();
                    entireCarousel.hide();
                } else {
                    entireCarousel.show();
                    buildRecommendations();
                }
            });


            // If the user is not logged in hide carousel and show
        }).catch(err => {
            console.log("You're not logged in!", err);
            recomInfo.text("Log in for recommendations!");
            entireCarousel.hide();
        });
    }).catch(err => console.log(err));
});


function buildRecommendations() {
    $("#recommendationDiv").empty();
    getUser(myId).done((user) => {
        let categoriesForRecom = [];
        for (const c of user.categories) {
            categoriesForRecom.push(c.title);
        }
        loadSportLocations(categoriesForRecom);
    })
}


// translation array needed to translate the categories of the app to the english words which are used in the overpass api json
// This data would normally be inside the database --> will not be done since this are two different projects
// With the pro account of font-awesome a lager variety of icons could be used, sports without an icon have the icon ?
let sportTranslation =
    [{id: 1, cat: "Fussball", eng: "soccer", icon: "fa fa-futbol", img: "soccer.jpg"}
        , {id: 2, cat: "Basketball", eng: "basketball", icon: "fa fa-basketball-ball", img: "basketball.jpg"}
        , {id: 3, cat: "Tennis", eng: "tennis", icon: "fa fa-question", img: "tennis.jpg"}
        , {id: 4, cat: "Jogging", eng: "athletics", icon: "fas fa-walking", img: "athletics.jpg"}];


function loadSportLocations(categories) {

    let bounds = map.getBounds();
    // contains all the sport translations relevant for this user
    let sports = [];

    for (let i = 0; i < sportTranslation.length; i++) {
        if (categories.includes(sportTranslation[i].cat)) {
            sports.push(sportTranslation[i]);
        }
    }

    // sports that needs to be extracted from the data
    let sportFilter = [];
    for (const s of sports) {
        sportFilter.push(s.eng);
    }

    // reset all data
    recommendations.clearLayers();

    // key value pair  key = id of the node or the way  value = rectangle or circle
    recommendationLayers = [];


    entireCarousel.hide();
    carouselBody.empty();


    // Loop through every element in the data "sport locations"
    $.each(sportData.elements, (key, value) => {
        let {tags, type} = value;

        // Ignore nodes without any tag
        if (tags !== undefined) {

            // If the type is a node create a cyrcle
            if (type === "node") {
                let {lat, lon} = value;
                if (sportFilter.includes(tags.sport) && bounds._southWest.lat < lat && bounds._northEast.lat > lat && bounds._southWest.lng < lon && bounds._northEast.lng > lon) {


                    // find the icon to use in the popup
                    let icon = sports[(sportFilter.indexOf(tags.sport))].icon;
                    let circle = L.circle([lat, lon], {
                        radius: 25,
                        color: 'red'
                    }).bindPopup(`<div id="recomPopup${value.id}"><p><i class="${icon}"></i><b>${tags.sport}</b></p><div><p id="recomTags${value.id}"></p></div><button id="createEventInRecom${value.id}" class="btn btn-default" type="button">Create event here!</button></div>`, {
                        maxWidth: "auto", minWidth: 135
                    });
                    recommendations.addLayer(circle);

                    // create Slide and add it to the carousel
                    let img = sports[(sportFilter.indexOf(tags.sport))].img;
                    carouselBody.append(createCarouselSlide(value.id, tags.sport, img));


                    // Clicking on the Img inside of the carousel should open the popup of the rectangle
                    $(`#recommendationInCarousel${value.id}`).click(() => {
                        recommendationLayers[value.id].openPopup();
                        fillPopupForCircles();
                    });
                    circle.on('click', () => {

                        fillPopupForCircles();
                    });

                    function fillPopupForCircles() {

                        let keys = Object.keys(tags);
                        for (const k in keys) {
                            // TODO Filter for only relevant Data? All relevant?
                            if (keys[k] !== "sport") {
                                $(`#recomTags${value.id}`).append(`<b>${keys[k]}:</b>${tags[keys[k]]}<br>`);
                            }
                        }

                        $(`#createEventInRecom${value.id}`).click(() => {
                            // Assign values to the create event form
                            newEventLat = (value.lat);
                            newEventLong = (value.lon);
                            let selectedValue = sports[(sportFilter.indexOf(tags.sport))].id;
                            $("#sportEventForm").val(selectedValue);

                            $("#createEventModal").modal("show");
                        });


                    }

                    //recommendationLayers.push({id: value.id, layer: circle, tags: tags});
                    recommendationLayers[value.id] = circle;
                }

                // If the type is a way create a rectangle with the min and max coordinates
                // TODO instead of a rectangle a polygon could be used but you would have to loop through all data again to get the coordinates of every node
            } else if (type === "way") {


                // -0.01 so its not under the recommendation carousel
                if (sportFilter.includes(tags.sport) && bounds._southWest.lat < value.bounds.minlat && bounds._northEast.lat-0.005 > value.bounds.maxlat && bounds._southWest.lng < value.bounds.minlon && bounds._northEast.lng > value.bounds.maxlon) {
                    let latlngs = [[value.bounds.minlat, value.bounds.minlon], [value.bounds.maxlat, value.bounds.maxlon]];

                    // find the icon to use in the popup
                    let icon = sports[(sportFilter.indexOf(tags.sport))].icon;
                    let rectangle = L.rectangle(latlngs, {color: 'red'}).bindPopup(`<div id="recomPopup${value.id}"><p><i class="${icon}"></i>   <b>${tags.sport}</b></p><div ><p id="recomTags${value.id}"></p></div><button id="createEventInRecom${value.id}" class="btn btn-default" type="button">Create event here!</button></div>`, {
                        maxWidth: "auto", minWidth: 135
                    });

                    recommendations.addLayer(rectangle);
                    // create Slide and add it to the carousel
                    let img = sports[(sportFilter.indexOf(tags.sport))].img;
                    carouselBody.append(createCarouselSlide(value.id, tags.sport, img));

                    // Clicking on the Img inside of the carousel should open the popup of the rectangle
                    /* TODO Would be nice if you could hover over a picture in the carousel and it would show the popup on the map and on leave the popup would close.
                       Seems not to be possible without storing a boolean for every "marker". A possibility is to tune the recommendationLayer and make the value an array with "marker" and boolean.
                       https://gis.stackexchange.com/questions/271602/show-popup-on-marker-hover-mouseover-hide-on-mouseout-and-keep-open-on-click?rq=1
                    */
                    $(`#recommendationInCarousel${value.id}`).on('click',() => {
                        console.log(recommendationLayers[value.id]);
                        recommendationLayers[value.id].openPopup();
                        fillPopup();
                    });
                    rectangle.on('click', () => {

                        fillPopup();
                    });

                    function fillPopup() {
                        let keys = Object.keys(tags);
                        for (const k in keys) {
                            // TODO Filter for only relevant Data? All relevant?
                            if (keys[k] !== "sport") {
                                $(`#recomTags${value.id}`).append(`<b>${keys[k]}:</b>${tags[keys[k]]}<br>`);
                            }
                            $(`#createEventInRecom${value.id}`).click(() => {

                                // calculating the middle of the rectangle
                                let randomLat = (value.bounds.maxlat - value.bounds.minlat) / 2 + value.bounds.minlat;
                                let randomLon = (value.bounds.maxlon - value.bounds.minlon) / 2 + value.bounds.minlon;

                                // Assign values to the create event form
                                newEventLat = randomLat;
                                newEventLong = randomLon;
                                let selectedValue = sports[(sportFilter.indexOf(tags.sport))].id;
                                $("#sportEventForm").val(selectedValue);

                                $("#createEventModal").modal("show");
                            });
                        }
                    }

                    //recommendationLayers.push({id: value.id, layer: rectangle, tags: tags});
                    recommendationLayers[value.id] = rectangle;
                }
            }
        }
    });


    // If no recommendations are found show notification, if there are give active class to the first element in the carousel
    if (carouselBody.children().length === 0) {

        recomInfo.text("No recommendatons found! Add more favorite sports to your profile!");

    } else {
        recomInfo.text("Enjoy your recommendations!");
        $("#carouselBody div:first-child").addClass("active");

        // add Layer to the leaflet map --> shows all the recommendations on the map
        map.addLayer(recommendations);
        entireCarousel.show();
    }


}

function createCarouselSlide(id, sport, img) {
    return template = `<div class="carousel-item col-3"><div class="panel panel-default"><div class="panel-thumbnail"><img id="recommendationInCarousel${id}" class="img-fluid mx-auto d-block" src="/assets/images/recommendations/${img}" alt="${sport}"></div></div></div>`;

}

