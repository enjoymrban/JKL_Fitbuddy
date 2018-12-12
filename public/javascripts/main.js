let url = "https://jklfitbuddy.herokuapp.com";

let myId;

$().ready(() => {
    if (window.location.pathname === "/auth/login") {
        $('#loginModal').modal('toggle')
    }
    userAware();
    minDate();
    loadSportOptionsForCreateEventForm();
    //loadUser();


});

// loading the options of sports for the create Event Form
// ToDo  sort alphabetically
function loadSportOptionsForCreateEventForm() {
    $("#sportEventForm").empty();
    $.ajax({
        url: url + "/api/category",
        type: "GET",
        dataType: "json"
    }).done((json) => {
        $.each(json, (key, value) => {
            let option = `<option value="${value.id}">${value.title}</option>`;
            $("#sportEventForm").append(option);
        });
    });

}

// set minimum date for create Event Form
function minDate() {
    let now = new Date(),
        minDate = now.toISOString().substring(0, 10);

    $('#dateEventForm').prop('min', minDate).prop('value', minDate);
}


function loadUser() {
    $("#sportEventForm").empty();
    getUser(myId).done((json) => {
        $("#userLastName").text(json.lastName);
        $("#userFullname").text(json.fullName);
        $("#profilePicture").attr('src', json.avatarUrl);

    });

}


function getUser(userId) {
    return $.ajax({
        url: url + "/api/user/" + userId,
        type: "GET",
        dataType: "json"
    }).catch(err => console.log(err))
}

function deleteEvent(eventId) {
    return $.ajax({
        url: url + "/api/event/" + eventId,
        type: "DELETE",
    }).done((json) => {
        console.log("Event Deleted")
    }).catch(err => console.log(err));
}


function updateEvent(eventUpdated) {
    return $.ajax({
        url: url + "/api/event/" + eventUpdated.id,
        type: "PUT",
        data: JSON.stringify(eventUpdated),
        contentType: "application/json",

    }).done(info => {
        console.log("Event Updated");
    }).catch(err => console.log(err));
}


function getEvent(eventId) {
    return $.ajax({
        url: url + "/api/event/" + eventId,
        type: "GET",
        dataType: "json"
    }).done((event) => {
        console.log("Event fetched");
    }).catch(err => console.log(err))
}


function getEvents() {
    return $.ajax({
        url: url + "/api/event",
        type: "GET",
        dataType: "json"
    }).done(() => {
        console.log("Events feched");
    }).catch(err => console.log(err))
}

function getUsers() {
    return $.ajax({
        url: url + "/api/user",
        type: "GET",
        dataType: "json"
    }).done(() => {
        console.log("Users feched");
    }).catch(err => console.log(err))
}


function updateUser(myId, updatedUser) {
    return $.ajax({

        type: "PUT",
        url: url + "/api/user/" + myId,
        data: JSON.stringify(updatedUser),
        contentType: "application/json",
    }).catch(err => console.log(err))
}


function userAware() {
    return $.ajax({
        type: "Get",
        url: url + "/userAware",
        dataType: 'json',
        success: function (data) {
            console.log("you are logged in as: " + data.fullName + " and id: " + data.id);
            $("#navbar-notloggedin").hide();
            $("#userLastName").text(data.lastName);
            $("#userFullname").text(data.fullName);
            $("#profilePicture").attr('src', data.avatarUrl);
            $("#navbar-loggedin").show();

            if (typeof(Storage) !== "undefined") {
                // Code for localStorage/sessionStorage.
                sessionStorage.setItem("myId", data.id);
                myId = sessionStorage.getItem("myId");
            } else {
                // Sorry! No Web Storage support..
            }


        },
        error: function (error) {
            $("#navbar-notloggedin").show();
            $("#navbar-loggedin").hide();
            console.log(error.responseText);
            sessionStorage.setItem("myId", null);
            myId = sessionStorage.getItem("myId");
        }
    })
}


function imInterested(eventId){
    return $.ajax({
        type: "GET",
        url: url + "/api/joinEvent/" + eventId
    }).done(msg => {
        console.log("I'm interested in this event");
    }).catch(err => {
        console.log(err);
    });
}



