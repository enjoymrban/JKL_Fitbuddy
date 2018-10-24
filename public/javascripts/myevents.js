let  events = getEvents();


$().ready(() => {
    fillEventTables();


});

function fillEventTables() {

    $("#myEventsTBody").empty();
    $("#interestedTBody").empty();
    $("#joinedTBody").empty();
    $("#buddieTBody").empty();
    for (const e of events) {
        let {id, description, sport, requestedBuddies, date, creator, interested, participants} = e;
        if (creator === "me") {
            let tableRow = (tableRowTemplateMyEvents(id, description, sport, requestedBuddies, date, interested, participants));
            $("#myEventsTBody").append(tableRow);

            $(`#interested${id}`).click(() => {
                buildInterestedTBodyModal(interested, participants);
            });
            $(`#trash${id}`).click(() => {
                events.splice(events.indexOf(e),1);
                fillEventTables();

                /*sessionStorage*/
                saveEvents(events);
            });
        } else if (interested.indexOf("me") !== -1) {
            let tableRow = (tableRowTemplate(id, description, sport, requestedBuddies, date, participants));
            $("#interestedTBody").append(tableRow);
            $(`#trash${id}`).click(() => {
                interested.splice(interested.indexOf("me"),1);
                fillEventTables();

                /*sessionStorage*/
                saveEvents(events);
            });
        } else if(participants.indexOf("me") !== -1){
            let tableRow = (tableRowTemplate(id, description, sport, requestedBuddies, date, participants));
            $("#joinedTBody").append(tableRow);
            $(`#trash${id}`).click(() => {
                participants.splice(participants.indexOf("me"),1);
                fillEventTables();

                /*sessionStorage*/
                saveEvents(events);
            });
        }


    }
}

function buildInterestedTBodyModal(interested, participants) {
    $('#interestedTBodyModal').empty();
    for (const i of interested) {
        let indexOfI = interested.indexOf(i);
        let tableRow = `<tr>
                        <td>${i}</td>
                        <td><a id="decline${indexOfI}" href="#"><i class="fa fa-times"></i></a></td>
                        <td><a id="accept${indexOfI}" href="#"><i class="fa fa-check"></i></a></td>
                        </tr>`;
        $("#interestedTBodyModal").append(tableRow);
        $(`#decline${indexOfI}`).click(() => {
            interested.splice(indexOfI, 1);
            buildInterestedTBodyModal(interested, participants);
            fillEventTables();

            /*sessionStorage*/
            saveEvents(events);
        });
        $(`#accept${indexOfI}`).click(() => {
            interested.splice(indexOfI, 1);
            participants.push(i);
            buildInterestedTBodyModal(interested, participants);
            fillEventTables();

            /*sessionStorage*/
            saveEvents(events);

        })

    }
}

function tableRowTemplateMyEvents(id, description, sport, requestedBuddies, date, interested, participants) {
    let tableRow = `<tr>
                        <td>${description}</td>
                        <td><a id="interested${id}" href="#" data-toggle="modal" data-target="#interestedModal">(${interested.length}) Show </a></td>
                        <td>${sport}</td>
                        <td>${participants.length} /${requestedBuddies}</td>
                        <td>${date}</td>
                        <td><a id="trash${id}" href="#"><i class="fa fa-trash"></i></a></td>
                        </tr>`;


    return tableRow;
}

function tableRowTemplate(id, description, sport, requestedBuddies, date, participants) {
    let tableRow = `<tr>
                        <td>${description}</td>
                        <td>${sport}</td>
                        <td>${participants.length} /${requestedBuddies}</td>
                        <td>${date}</td>
                        <td><a id="trash${id}" href="#"><i class="fa fa-trash"></i></a></td>
                        </tr>`;
    return tableRow;
}