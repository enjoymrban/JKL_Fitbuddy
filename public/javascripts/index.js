let endZoom = 13;
let startZoom = 10;
let map = L.map('mapid', {zoomControl: false}).setView([0, 0], startZoom);

// markers Cluster Group for collecting marker plugin
let markersClusterGroup = L.markerClusterGroup();

// all markers
let markers = [];

//
let createEventPopupMarker = undefined;

// long lat of new event
let newEventLong;
let newEventLat;


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
    map.setView([myLatitude, myLongitude], endZoom);
}


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


function addMarkerToMap(event, wasCreated = false) {
    let {id, category, nrOfPlayers, coordinateX, coordinateY} = event;
    let {title} = category;


    let marker = L.marker([coordinateX, coordinateY]);
    marker.bindPopup(`<div class="popupclickable" id="popupInfoShort${id}"><p>Sport: <b>${title}</b> | Requested buddies: <b>${nrOfPlayers}</b></p></div><div class="popupclickable" id="popupInfoLarge${id}"></div>`, {
        closeOnClick: false,
        autoClose: false,
        autoPan: false,
        className: "custom"
    }).openPopup();

//

    let mobj = {marker: marker, eventId: id};
    marker.on('click', () => {
        popUpOpens(mobj);
    });

    markersClusterGroup.addLayer(marker);
    markers.push(mobj);
    if (wasCreated) {
        marker.openPopup();
    }
}


/*when the user clicks on the map a popup opens*/
map.on('click', function (e) {
    let popLocation = e.latlng;
    let id = `${popLocation.lat}${popLocation.lng}`;
    id = id.replace(/\./g, "");

    if (createEventPopupMarker !== undefined) {
        map.removeLayer(createEventPopupMarker);
    }

    createEventPopupMarker = L.marker(popLocation);
    createEventPopupMarker.addTo(map);
    createEventPopupMarker.bindPopup(`<div><button id="createEventButton${id}" class="btn btn-default" type="button">Create event here!</button></p></div>`);
    createEventPopupMarker.openPopup();


    $(`#createEventButton${id}`).click(() => {
        newEventLat = (popLocation.lat);
        newEventLong = (popLocation.lng);
        $("#createEventModal").modal("show");
    });
});


map.on('zoom move', function () {
    let mapBounds = map.getBounds();
    let {_northEast, _southWest} = mapBounds;


    if (createEventPopupMarker !== undefined) {
        map.removeLayer(createEventPopupMarker);
    }

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
                popUpOpens(mobj);
            }
        }
    }
});


map.on('popupopen', function (e) {
    for (const mobj of markers) {
        if (mobj.marker === e.popup._source) {
            popUpOpens(mobj);
        }
    }
});

// Funktion wird mehrfach aufgerufen z.B wenn sich der Benutzer auf der Map bewegt oder hinein und herauszoomt evt. beheben
function popUpOpens(mobj) {


        $.ajax({
            url: url + "/api/event/" + mobj.eventId,
            type: "GET",
            dataType: "json"
        }).done((event) => {
            let {id, description, date, creator, interested} = event;
            $('#popupInfoLarge' + id).empty();

            if (creator.id === myId) {
                let popupInfoLargeCreator = `<p>Description: <b>${description}</b> | Date: <b>${date}</b> | Creator:  <b>${creator.fullName}</b></p>`;
                $('#popupInfoLarge' + id).append(popupInfoLargeCreator);
                return;
            } else if (interested.indexOf(myId) !== -1) {
                let popupInfoLargeInterested = `<p>Description: <b>${description}</b> | Date: <b>${date}</b> | Creator:  <b>${creator.fullName}</b></p><button id="interestedInEvent${id}" class="btn btn-default" type="button" disabled>I'm interested!</button>`;
                $('#popupInfoLarge' + id).append(popupInfoLargeInterested);
            } else {
                let popupInfoLargeInterested = `<p>Description: <b>${description}</b> | Date: <b>${date}</b> | Creator:  <b>${creator.fullName}</b></p><button id="interestedInEvent${id}" class="btn btn-default" type="button" >I'm interested!</button>`;
                $('#popupInfoLarge' + id).append(popupInfoLargeInterested);
            }

            let updatedInterestedArray = event.interested;
            updatedInterestedArray.push(myId);
            let eventUpdated = {
                description: event.description,
                category: event.category,
                creator: event.creator,
                date: event.date,
                nrOfPlayers: event.nrOfPlayers,
                coordinateX: event.coordinateX,
                coordinateY: event.coordinateY,
                interested: updatedInterestedArray,
                participants: event.participants
            };

            $('#popupInfoLarge' + id).unbind();
            $('#popupInfoLarge' + id).click(() => {

                $.ajax({
                    type: "PUT",
                    url: url + "/api/event/" + mobj.eventId,
                    data: JSON.stringify(eventUpdated),
                    contentType: "application/json",
                }).done(msg => {
                    console.log("");
                    popUpOpens(mobj);
                }).catch(err => {
                    console.log(err);
                });
            })
        }).catch(err => {
            console.log(err)
        });
    $('#popupInfoShort' + mobj.eventId).unbind();
    $('#popupInfoShort' + mobj.eventId).click(() => {
        console.log("show large info div");
        $('#popupInfoLarge' + mobj.eventId).show();

    });
}


function createEvent() {
    $(`#sendEventForm`).click(() => {

        let event = {
            description: $('#descriptionEventForm').val(),
            category: {
                id: $('#sportEventForm').find(":selected").val(),
                title: $('#sportEventForm').find(":selected").text() // id doesn't suffice since the returned json wouldn't contain the title.
            },
            creator: {
                id: myId
            },

            date: $('#dateEventForm').val(),
            nrOfPlayers: $('#amoutBuddiesEventForm').val(),
            coordinateX: newEventLat,
            coordinateY: newEventLong

        };

        $.ajax({

            type: "POST",
            url: url + "/api/event",
            data: JSON.stringify(event),
            contentType: "application/json",
            dataType: 'json'
        }).done(msg => {

            addMarkerToMap(msg, true);
            map.removeLayer(createEventPopupMarker);
            $('#createEventModal').modal('toggle')

        }).catch(err => {
            console.log(err);
        });


    });

}




