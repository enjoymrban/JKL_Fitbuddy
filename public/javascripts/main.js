let buddies = ["me", "peter", "paul", "hans", "max"];
let sports = ["tennis", "football", "squash", "badminton", "volleyball", "swimming", "billiard"];
let url = "localhost:9000";

$().ready(() => {
    $('#loadData').click(() => {
        loadData();
    });
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


/*sessionStorage*/
function loadData() {
    let eventsStorage = [{
        id: 1,
        description: "Suche Trainingspartner",
        sport: "tennis",
        date: "30.10.2018",
        requestedBuddies: "1",
        location: {
            lat: "47.480581532215325",
            long: "9.05"
        },
        interested: [],
        participants: [],
        creator: "max"

    }, {
        id: 2,
        description: "Plauschspiel auf dem Roten Platz",
        sport: "football",
        date: "27.10.2018",
        requestedBuddies: "9",
        location: {
            lat: "47.473736163992214",
            long: "9.03977394104004"
        },
        interested: ["me", "peter"],
        participants: [],
        creator: "max"
    }, {
        id: 3,
        description: "suche 3 Hobbyspieler für ein 2 vs 2",
        sport: "volleyball",
        date: "22.12.2018",
        requestedBuddies: "3",
        location: {
            lat: "47.48714416287697",
            long: "9.016513824462892"
        },
        interested: ["max", "peter"],
        participants: [],
        creator: "me"
    }, {
        id: 4,
        description: "um 17:00 bei der Box",
        sport: "squash",
        date: "5.12.2018",
        requestedBuddies: "1",
        location: {
            lat: "47.49714416287697",
            long: "9.016513824462892"
        },
        interested: ["max", "peter"],
        participants: [],
        creator: "me"
    }, {
        id: 5,
        description: "Suche Hobbyspieler",
        sport: "badminton",
        date: "1.11.2018",
        requestedBuddies: "1",
        location: {
            lat: "47.58714416287697",
            long: "9.016513824462892"
        },
        interested: ["max", "peter"],
        participants: ["me"],
        creator: "max"
    }, {
        id: 6,
        description: "Bahnen schwimmen",
        sport: "swimming",
        date: "25.10.2018",
        requestedBuddies: "1",
        location: {
            lat: "46.85874261906014",
            long: "9.505915045738222"
        },
        interested: ["max"],
        participants: [],
        creator: "peter"
    }, {
        id: 7,
        description: "Billiard Spielen",
        sport: "billiard",
        date: "24.10.2018",
        requestedBuddies: "1",
        location: {
            lat: "46.85371702642054",
            long: "9.514557123184204"
        },
        interested: ["peter"],
        participants: [],
        creator: "me"
    }];


    let mybuddiesStorage = ["peter", "paul"];

    sessionStorage.setItem("eventsStorage", JSON.stringify(eventsStorage));
    sessionStorage.setItem("mybuddiesStorage", JSON.stringify(mybuddiesStorage));

}


function saveEvents(saveEvents) {
    sessionStorage.setItem("eventsStorage", JSON.stringify(saveEvents));

}

function getEvents() {
    let eventsFromStorage = sessionStorage.getItem("eventsStorage");
    return JSON.parse(eventsFromStorage);
}


function savemyBuddies(savemybuddies) {
    sessionStorage.setItem("mybuddiesStorage", JSON.stringify(savemybuddies));

}

function getmybuddies(){
    return JSON.parse(sessionStorage.getItem("mybuddiesStorage"));

}