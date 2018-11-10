let url = "http://localhost:9000";

let myId = 4;

$().ready(() => {
     minDate();
    loadSportOptionsForCreateEventForm();
    loadUser();


});

// loading the options of sports for the create Event Form
function loadSportOptionsForCreateEventForm() {
    $("#sportEventForm").empty();
    $.ajax({
        url: url+"/api/category",
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
    $.ajax({
        url: url+"/api/user/"+myId,
        type: "GET",
        dataType: "json"
    }).done((json) => {
        $("#userLastName").text(json.lastName);
        $("#userFullname").text(json.fullName);
    });

}


