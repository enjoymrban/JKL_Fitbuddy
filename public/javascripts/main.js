let events = [{
    id: 1,
    sport: "tennis",
    date: "16.10.2018",
    requestedBuddies: "2",
    location: {
        lat: "47.480581532215325",
        long: "9.05"
    }

}, {
    id: 2,
    sport: "football",
    date: "22.10.2018",
    requestedBuddies: "10",
    location: {
        lat: "47.473736163992214",
        long: "9.03977394104004"
    }
}, {
    id: 3,
    sport: "football",
    date: "22.10.2018",
    requestedBuddies: "10",
    location: {
        lat: "47.48714416287697",
        long: "9.016513824462892"
    }
}];

let myEvents = [];


let sports = ["tennis", "football", "squash", "badmintion"];


$().ready(() => {
    // when the user visits the site, check geodata
    minDate();
    loadSportOptionsForCreateEventForm();


});

// loading the options of sports for the create Event Form
function loadSportOptionsForCreateEventForm() {
    $("#sportEventForm").empty();
    for (const i of sports) {
        let option = `<option value="${i}">${i}</option>`;
        $("#sportEventForm").append(option);
    }

}

// set minimum date for create Event Form
function minDate() {
    let now = new Date(),
        minDate = now.toISOString().substring(0, 10);

    $('#dateEventForm').prop('min', minDate).prop('value', minDate);
}