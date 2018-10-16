$().ready(() => {
    // when the user visits the site, check geodata
    getLocation();
    minDate();


});

// set minimum date for forms
function minDate() {
    let now = new Date(),
        minDate = now.toISOString().substring(0, 10);

    $('#dateEventForm').prop('min', minDate).prop('value', minDate);
}


// get Current Location
function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(setPosition);
    } else {
        console.log("Geolocation is not supported by this browser.");
    }
}

// assign Coordinates
function setPosition(position) {
    let myLatitude = position.coords.latitude;
    let myLongitude = position.coords.longitude;

    createMap(myLatitude, myLongitude);

}

// create Leaflet Map on index page
function createMap(myLatitude, myLongitude) {
    let map = L.map('mapid').setView([myLatitude, myLongitude], 15);

// Leaflet Map on index page
    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoiZW5qb3ltcmJhbiIsImEiOiJjam5hY3EwcDQwZ2hiM3BwYWQ2dWt4a2x1In0.nlX1GeaPE2DQn3aZH0IJaA', {
        maxZoom: 18,
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
            '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
            'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        id: 'mapbox.streets'
    }).addTo(map);

    map.on('click', function (e) {
        let popLocation = e.latlng;
        let popup = L.popup()
            .setLatLng(popLocation)
            .setContent(`<div><button id="createEventButton" class="btn btn-default" type="button">Create Event Here!</button></p></div>`)
            .openOn(map);

        $("#createEventButton").click(() => {
            $("#createEventModal").modal("show");
            $("#whereEventForm").val(`Lat: ${popLocation.lat} and Long: ${popLocation.lng}`)
        })
    });




}