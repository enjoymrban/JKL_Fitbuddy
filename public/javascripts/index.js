let endZoom = 13;
let startZoom = 10;
let map = L.map('mapid', {zoomControl: false}).setView([0, 0], startZoom);

// markers Cluster Group for collecting marker plugin
let markersClusterGroup = L.markerClusterGroup();

// all markers
let markers = [];


$().ready(() => {
    // when the user visits the site, check geodata
    getLocation();
    createEvent();


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
    console.log(myLatitude, myLongitude);
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
function createMap(myLatitude, myLongitude) {


// Leaflet Map on index page
    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoiZW5qb3ltcmJhbiIsImEiOiJjam5hY3EwcDQwZ2hiM3BwYWQ2dWt4a2x1In0.nlX1GeaPE2DQn3aZH0IJaA', {
        maxZoom: 18,
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' + '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' + 'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        id: 'mapbox.streets'
    }).addTo(map);

    placeEventsOnMap();

    //set map center after all events are placed
    //map.panTo(new L.LatLng(myLatitude, myLongitude));
    map.setView([myLatitude, myLongitude], endZoom);
}


/*when the user clicks on the map a popup opens*/
map.on('click', function (e) {
    let popLocation = e.latlng;
    let id = `${popLocation.lat}${popLocation.lng}`;
    id = id.replace(/\./g, "");

    L.popup().setLatLng(popLocation)
        .setContent(`<div><button id="createEventButton${id}" class="btn btn-default" type="button">Create event here!</button></p></div>`)
        .openOn(map);

    $(`#createEventButton${id}`).click(() => {
        $("#whereLatEventForm").val(popLocation.lat);
        $("#whereLngEventForm").val(popLocation.lng);
        $("#createEventModal").modal("show");
    });
});


map.on('zoom move', function () {
    let mapBounds = map.getBounds();
    let {_northEast, _southWest} = mapBounds;

    if (map.getZoom() <= 13) {
        for (const mobj of markers) {
            mobj.marker.closePopup();
        }
    }

    else {
        for (const mobj of markers) {
            let {eventId, marker} = mobj;
            let {lat, lng} = mobj.marker._latlng;
            if (_northEast.lat > lat && lat > _southWest.lat && _northEast.lng > lng && lng > _southWest.lng) {
                marker.openPopup();
                popUpOpens(eventId);

            }
        }
    }
});


function popUpOpens(markerId) {
    let event = "";
    $.ajax({
        url: url + "/api/event",
        type: "GET",
        dataType: "json"
    }).done((events) => {
        for (const e of events) {
            if (e.id === markerId) {
                event = e;
            }
        }
        let {interested, creator} = event;


        let interestedInEvent = $(`#interestedInEvent${markerId}`);


        if (creator === "me") {
            interestedInEvent.prop("disabled", true);

        } else if (interested.indexOf("me") !== -1) {
            interestedInEvent.text("I'm not longer interested");
        }

        interestedInEvent.click(() => {

            let eventIndex = events.indexOf(event);
            console.log(eventIndex);
            let amIInterested = events[eventIndex].interested.indexOf("me");

            if (events[eventIndex].id === markerId && amIInterested === -1) {
                events[eventIndex].interested.push("me");
                /*sessionStorage*/
                saveEvents(events);

                interestedInEvent.text("I'm not longer interested");
            } else if (events[eventIndex].id === markerId && amIInterested !== -1) {

                event.interested.splice(amIInterested, 1);
                interestedInEvent.text("I'm interested!");
            }
        })
    });




}

// Place Markers on the map
function placeEventsOnMap() {

    $.ajax({
        url: url + "/api/event",
        type: "GET",
        dataType: "json"
    }).done((json) => {
        $.each(json, (key, value) => {
            addMarkerToMap(value);
        })
    });


    console.log("place events on map");
    map.addLayer(markersClusterGroup);
}

function addMarkerToMap(event) {
    let {id, sport, nrOfPlayers, interested, coordinateX, coordinateY} = event;


    let marker = L.marker([coordinateX, coordinateY]);

    marker.bindPopup(`<p>Sport: <b>${sport}</b> | Requested buddies: <b>${nrOfPlayers}</b></p> <button id="interestedInEvent${id}" class="btn btn-default" type="button" >I'm interested!</button>`, {
        closeOnClick: false,
        autoClose: false,
        autoPan: false
    }).openPopup();
    marker.on('click', () => {
        popUpOpens(id);
    });
    markersClusterGroup.addLayer(marker);
    markers.push({marker: marker, eventId: id});


}


function createEvent() {
    $(`#sendEventForm`).click(() => {

        let event = {
            description: $('#descriptionEventForm').val(),
            category: {
                id: $('#sportEventForm').find(":selected").val()
            },
            creator: {
                id: myId
            },

            date: $('#dateEventForm').val(),
            requestedBuddies: $('#amoutBuddiesEventForm').val(),
            coordinateX: $("#whereLatEventForm").val(),
            coordinateY: $("#whereLngEventForm").val()

        };

        $.ajax({

            type: "POST",
            url: url+"/api/event",
            data: JSON.stringify(event),
            contentType: "application/json",
            dataType: 'json'
        }).done(msg =>{
            console.log(msg);
            $('#createEventModal').modal('toggle');
            addMarkerToMap(event);
        }).catch(err =>{
            console.log(err);
        });







    });

}




