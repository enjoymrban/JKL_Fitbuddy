let mapZoom = 15;
let map;
let mapCenter = 0;

$().ready(() => {
    // when the user visits the site, check geodata
    getLocation();


});

// get Current Location

function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(setPosition, showError);

    } else {
        console.log("Geolocation is not supported by this browser.");
    }
}

// assign Coordinates
function setPosition(position) {
    let myLatitude = position.coords.latitude;
    let myLongitude = position.coords.longitude;
    console.log(myLatitude,myLongitude);
    createMap(myLatitude, myLongitude);


}

function showError(error) {
    switch (error.code) {
        case error.PERMISSION_DENIED:
            console.log("User denied the request for Geolocation.");
            createMap();
            break;
        case error.POSITION_UNAVAILABLE:
            console.log("Location information is unavailable.");
            break;
        case error.TIMEOUT:
            console.log("The request to get user location timed out.");
            break;
        default:
            console.log("An unknown error occurred.");
            break;
    }
}

// create Leaflet Map on index page
function createMap(myLatitude,myLongitude) {

    map = L.map('mapid').setView([myLatitude, myLongitude], mapZoom);

// Leaflet Map on index page
    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoiZW5qb3ltcmJhbiIsImEiOiJjam5hY3EwcDQwZ2hiM3BwYWQ2dWt4a2x1In0.nlX1GeaPE2DQn3aZH0IJaA', {
        maxZoom: 18,
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' + '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        id: 'mapbox.streets'
    }).addTo(map);

    map.on('click', function (e) {
        let popLocation = e.latlng;
        let id = `${popLocation.lat}${popLocation.lng}`;
        id = id.replace(/\./g, "");
        let popup = L.popup()
            .setLatLng(popLocation)
            .setContent(`<div><button id="createEventButton${id}" class="btn btn-default" type="button">Create event here!</button></p></div>`)
            .openOn(map);


        $(`#createEventButton${id}`).click(() => {
            $("#whereLatEventForm").val(popLocation.lat);
            $("#whereLngEventForm").val(popLocation.lng);
            $("#createEventModal").modal("show");
            $(`#sendEventForm`).click(() => {
                let newEvent = {
                    id: events.length + 1,
                    description: $('#descriptionEventForm').val(),
                    sport: $('#sportEventForm').find(":selected").text(),
                    date: $('#dateEventForm').val(),
                    requestedBuddies: $('#amoutBuddiesEventForm').val(),
                    location: {
                        lat: $("#whereLatEventForm").val(),
                        long: $("#whereLngEventForm").val()

                    },
                    interested: false,
                    creator: "me"
                };
                events.push(newEvent);
                $('#createEventModal').modal('toggle');
                placeEventsOnMap(map);
                setMapCenter();
            });

        })
    });

    placeEventsOnMap(map);
    setMapCenter();

}

function setMapCenter() {
    mapCenter = map.getCenter();
    console.log(mapCenter.lat);
    map.panTo(new L.LatLng(mapCenter.lat, mapCenter.long));


}






// Place Markers on the map
function placeEventsOnMap(map) {

    for (const i of events) {

        let {id, location, sport, requestedBuddies} = i;
        let {lat, long} = location;
        let marker = L.marker([lat, long]).addTo(map);
        marker.bindPopup(`<p>Sport: <b>${sport}</b> | Requested buddies: <b>${requestedBuddies}</b></p> <button id="interestedInEvent${id}" class="btn btn-default" type="button" >I'm interested!</button>`, {
            closeOnClick: false,
            autoClose: false
        }).openPopup();

        $(`#interestedInEvent${id}`).click(() => {
            for (const e of events) {
                let eventIndex = e.interested.indexOf("me");
                if (e.id === id && eventIndex === -1) {
                    e.interested.push("me");
                    //alert(`Event: ${e.sport} has been added to your events`);
                    $(`#interestedInEvent${id}`).text("I'm not longer interested");
                } else if (e.id === id && eventIndex !== -1) {
                    //alert(`Event: ${e.sport} has been removed from your events`);
                    e.interested.splice(eventIndex, 1);
                    $(`#interestedInEvent${id}`).text("I'm interested!");
                }
            }
        })
    }


}



