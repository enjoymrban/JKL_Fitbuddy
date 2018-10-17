let events = [{
    id: 1,
    description: "bin Anfänger suche Begleitung",
    sport: "tennis",
    date: "16.10.2018",
    requestedBuddies: "2",
    location: {
        lat: "47.480581532215325",
        long: "9.05"
    },
    interested: ["me", "max", "peter"],
    creator: "max"

}, {
    id: 2,
    description: "bin Anfänger suche Begleitung",
    sport: "football",
    date: "22.10.2018",
    requestedBuddies: "10",
    location: {
        lat: "47.473736163992214",
        long: "9.03977394104004"
    },
    interested: ["me", "max", "peter"],
    creator: "max"
}, {
    id: 3,
    description: "bin Anfänger suche Begleitung",
    sport: "football",
    date: "22.10.2018",
    requestedBuddies: "10",
    location: {
        lat: "47.48714416287697",
        long: "9.016513824462892"
    },
    interested: [ "max", "peter"],
    creator: "me"
},{
    id:4 ,
    description: "um 17:00 bei der Box",
    sport: "squash",
    date: "22.10.2018",
    requestedBuddies: "10",
    location: {
        lat: "47.49714416287697",
        long: "9.016513824462892"
    },
    interested: ["max", "peter"],
    creator: "me"
},{
    id: 5,
    description: "bin Anfänger suche Begleitung",
    sport: "badminton",
    date: "22.10.2018",
    requestedBuddies: "10",
    location: {
        lat: "47.58714416287697",
        long: "9.016513824462892"
    },
    interested: ["me", "max", "peter"],
    creator: "max"
}];

let sports = ["tennis", "football", "squash", "badminton"];


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