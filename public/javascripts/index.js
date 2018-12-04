// Create Event
let blueIcon = new L.Icon({
    iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-blue.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

// Available Events
let greenIcon = new L.Icon({
    iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

// Full Events
let redIcon = new L.Icon({
    iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

// Waiting for Response
let orangeIcon = new L.Icon({
    iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-orange.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

// myEvents
let blackIcon = new L.Icon({
    iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-black.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});


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

myId = sessionStorage.getItem('myId');

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
        createMap(51, 9);
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
            createMap(51, 9);
            break;
        case error.POSITION_UNAVAILABLE:
            console.log("Location information is unavailable.");
            createMap(51, 9);
            break;
        case error.TIMEOUT:
            console.log("The request to get user location timed out.");
            createMap(51, 9);
            break;
        default:
            console.log("An unknown error occurred.");
            createMap(51, 9);
            break;
    }
}

// create Leaflet Map on index page
function createMap(myLatitude, myLongitude) {


// Leaflet Map on index page
    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoiZW5qb3ltcmJhbiIsImEiOiJjam5hY3EwcDQwZ2hiM3BwYWQ2dWt4a2x1In0.nlX1GeaPE2DQn3aZH0IJaA', {
        maxZoom: 18,
        minZoom: 4,
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


function addMarkerToMap(event, wasNewlyCreated = false) {

    let color = blueIcon;

    if (myId !== "null") {
        if (event.creator.id === Number(myId)) {
            color = blackIcon;
            placeMarker(event, color, wasNewlyCreated);
        } else {
            getEvent(event.id).done((singleEvent) => {
                const {interested, participants, nrOfPlayers} = singleEvent;
                if (participants === nrOfPlayers - 1) {
                    color = redIcon;
                    placeMarker(event, color, wasNewlyCreated);

                } else if (interested.indexOf(Number(myId)) !== -1) {
                    color = orangeIcon;
                    placeMarker(event, color, wasNewlyCreated);

                } else {
                    placeMarker(event, color, wasNewlyCreated);
                }

            })
        }
    } else {
        placeMarker(event, color, wasNewlyCreated);
    }
}

function placeMarker(event, color, wasNewlyCreated) {
    let {id, category, nrOfPlayers, coordinateX, coordinateY} = event;
    let {title} = category;
    let marker = L.marker([coordinateX, coordinateY], {icon: color});
    marker.bindPopup(`<div class="clickable" id="popupInfo${id}"><div id="popupInfoShort${id}"><p>Sport: <b>${title}</b> | Requested buddies: <b>${nrOfPlayers}</b></p></div><div id="popupInfoLarge${id}"></div></div>`, {
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
    if (wasNewlyCreated) {
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
    createEventPopupMarker.setIcon(blueIcon);
    createEventPopupMarker.addTo(map);
    createEventPopupMarker.bindPopup(`<div><button id="createEventButton${id}" class="btn btn-default" type="button">Create event here!</button></p></div>`, {
        closeButton: false,
        closeOnEscapeKey: false

    });
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
            let popup = mobj.marker.getPopup();
            if (popup.isOpen()) {
                console.log("popup already open");
            }else{
                let {eventId, marker} = mobj;
                let {lat, lng} = mobj.marker._latlng;
                if (_northEast.lat > lat && lat > _southWest.lat && _northEast.lng > lng && lng > _southWest.lng) {
                    marker.openPopup();
                    popUpOpens(mobj);
                }
            }
        }
    }
});


map.on('popupopen', function (e) {
    for (const mobj of markers) {
        if (mobj.marker === e.popup._source) {
            popUpOpens(mobj);
            return;
        }
    }
});

// function is called multiple times. on map zoom or move
function popUpOpens(mobj) {

    if (myId !== "null") {
        getEvent(mobj.eventId).done((event) => {
                let {id, description, date, creator, interested, nrOfPlayers, participants} = event;
                $('#popupInfoLarge' + id).empty();

                if (creator.id === Number(myId)) {
                    let popupInfoLargeCreator = `<p>Description: <b>${description}</b> <br>Date: <b>${date}</b> <br>Creator:  <b>${creator.fullName}</b><br>Spots open: <b>${nrOfPlayers - participants.length}/${nrOfPlayers}</b></p>`;
                    $('#popupInfoLarge' + id).append(popupInfoLargeCreator);
                    return;
                } else if (interested.indexOf(Number(myId)) !== -1) {
                    let popupInfoLargeInterested = `<p>Description: <b>${description}</b><br> Date: <b>${date}</b> <br>Creator:  <b>${creator.fullName}</b><br>Spots open:  <b>${nrOfPlayers - participants.length}/${nrOfPlayers}</b></p><button id="desinterestedInEvent${id}" class="btn btn-default" type="button" >I'm NOT interested!</button>`;
                    $('#popupInfoLarge' + id).append(popupInfoLargeInterested);

                    $('#desinterestedInEvent' + id).unbind();
                    $('#desinterestedInEvent' + id).click(() => {


                            $.ajax({
                                type: "GET",
                                url: url + "/api/leaveEvent/" + id
                            }).done(msg => {
                                // popup is loaded again so that the i'm interested button is updated to disabled
                                popUpOpens(mobj);
                                mobj.marker.setIcon(blueIcon);
                            }).catch(err => {
                                console.log(err);
                            });


                        }
                    )
                } else {
                    let popupInfoLargeInterested = `<p>Description: <b>${description}</b><br>Date: <b>${date}</b> <br>Creator:  <b>${creator.fullName}</b><br>Spots open:  <b>${nrOfPlayers - participants.length}/${nrOfPlayers}</b></p><button id="interestedInEvent${id}" class="btn btn-default" type="button" >I'm interested!</button>`;
                    $('#popupInfoLarge' + id).append(popupInfoLargeInterested);

                    $('#interestedInEvent' + id).unbind();
                    $('#interestedInEvent' + id).click(() => {
                        imInterested(id).done(() => {
                            // popup is loaded again so that the i'm interested button is updated to disabled
                            popUpOpens(mobj);
                            mobj.marker.setIcon(orangeIcon);
                        });
                    })
                }
            }
        ).catch(err => {
            console.log(err)
        });

        $('#popupInfo' + mobj.eventId).unbind();
        $('#popupInfo' + mobj.eventId).click(() => {
            console.log("show large info div");
            $('#popupInfoLarge' + mobj.eventId).show();

        });

    } else {
        $('#popupInfo' + mobj.eventId).unbind();
        $('#popupInfo' + mobj.eventId).click(() => {
            $('#loginModal').modal('toggle')
        });
    }
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
                id: Number(myId)
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




