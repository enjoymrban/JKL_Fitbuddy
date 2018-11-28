let url = "http://localhost:9000";

let myId = 5;

$().ready(() => {
    minDate();
    loadSportOptionsForCreateEventForm();
    loadUser();


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
    }).done(()=>{
        console.log("Events feched");
    }).catch(err => console.log(err))
}

function getUsers(){
    return $.ajax({
        url: url + "/api/user",
        type: "GET",
        dataType: "json"
    }).done(()=>{
        console.log("Users feched");
    }).catch(err => console.log(err))
}


function updateUser(myId,updatedUser){
     return $.ajax({

        type: "PUT",
        url: url+"/api/user/"+myId,
        data: JSON.stringify(updatedUser),
        contentType: "application/json",

    })
}

