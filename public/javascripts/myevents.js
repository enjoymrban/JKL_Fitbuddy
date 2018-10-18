$().ready(() => {
    fillEventTables();


});


function fillEventTables() {

    $("#myEventsTBody").empty();
    $("#interestedTBody").empty();
    for (const e of events) {
        let {id, description, sport, requestedBuddies, date, creator, interested} = e;
        if (creator === "me") {
            let tableRow = (tableRowTemplateMyEvents(id, description, sport, requestedBuddies, date, interested));
            $("#myEventsTBody").append(tableRow);

            $(`#interested${id}`).click(() => {
                buildInterestedTBodyModal(interested);
            })
        } else if (interested.indexOf("me") !== -1) {
            let tableRow = (tableRowTemplate(description, sport, requestedBuddies, date));
            $("#interestedTBody").append(tableRow);
        }
    }
}

function buildInterestedTBodyModal(interested) {
    $('#interestedTBodyModal').empty();
    for (const i of interested) {
        let indexOfI = interested.indexOf(i);
        let tableRow = `<tr>
                        <td>${i}</td>
                        <td><a id="decline${indexOfI}" href="#"><i class="fa fa-times"></i></a></td>
                        <td><i class="fa fa-check"></i></td>
                        </tr>`;
        $("#interestedTBodyModal").append(tableRow);
        $(`#decline${indexOfI}`).click(() => {
            interested.splice(indexOfI, 1);
            console.log("declined");
            buildInterestedTBodyModal(interested);
        })
    }
}

function tableRowTemplateMyEvents(id, description, sport, requestedBuddies, date, interested) {
    let tableRow = `<tr>
                        <td>${description}</td>
                        <td><a id="interested${id}" href="#" data-toggle="modal" data-target="#interestedModal">(${interested.length}) Show </a></td>
                        <td>${sport}</td>
                        <td>${requestedBuddies}</td>
                        <td>${date}</td>
                        <td><i class="fa fa-trash"></i></td>
                        </tr>`;

    return tableRow;
}

function tableRowTemplate(description, sport, requestedBuddies, date) {
    let tableRow = `<tr>
                        <td>${description}</td>
                        <td>${sport}</td>
                        <td>${requestedBuddies}</td>
                        <td>${date}</td>
                        <td><i class="fa fa-trash"></i></td>
                        </tr>`;
    return tableRow;
}