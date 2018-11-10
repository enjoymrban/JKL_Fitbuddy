$().ready(() => {
    fillEventTables();


});

function fillEventTables() {

    $("#myEventsTBody").empty();
    $("#interestedTBody").empty();
    $("#joinedTBody").empty();
    $("#buddieTBody").empty();

    $.ajax({
        url: url + "/api/event",
        type: "GET",
        dataType: "json"
    }).done((events) => {
        $.each(events, (key, event) => {

            $.ajax({
                url: url + "/api/event/" + event.id,
                type: "GET",
                dataType: "json"
            }).done((event) => {

                let {id, description, category, nrOfPlayers, date, creator, interested, participants} = event;
                let {title} = category;

                if (creator.id === myId) {
                    let tableRow = (tableRowTemplateMyEvents(id, description, title, nrOfPlayers, date, interested, participants));

                    $("#myEventsTBody").append(tableRow);
                    $(`#interested${id}`).click(() => {
                        console.log("show Interested Users");
                        buildInterestedTBodyModal(event);
                    });

                    $(`#trash${id}`).click(() => {
                        console.log("Delete Event");

                        deleteEvent(id).done(() => {
                            $(`#tableRow${id}`).remove();
                        });


                    });

                } else if (interested.indexOf(myId) !== -1) {
                    addEventToInterested(event);

                } else if (participants.indexOf(myId) !== -1) {
                    let tableRow = (tableRowTemplate(id, description, title, nrOfPlayers, date, participants));
                    $("#joinedTBody").append(tableRow);
                    $(`#trash${id}`).click(() => {
                        console.log("update event, remove me from participating");
                        participants.splice(participants.indexOf(myId), 1);
                        updateEvent(event).done(() => {
                            $(`#tableRow${id}`).remove();
                        });
                    });
                } else {
                    addEventToEventOfMyBuddies(event);

                }
            }).catch(err => console.log(err));
        })
    }).catch(err => console.log(err));
}

function addEventToInterested(event) {
    let {id, description, category, nrOfPlayers, date, creator, interested, participants} = event;
    let {title} = category;

    let tableRow = (tableRowTemplate(id, description, title, nrOfPlayers, date, participants));
    $("#interestedTBody").append(tableRow);
    $(`#trash${id}`).click(() => {
        console.log("update event, remove me from interested");
        interested.splice(interested.indexOf(myId), 1);
        $(`#tableRow${id}`).remove();
        updateEvent(event).done(() => {
                // We need to check whether the removed event was one of our buddies, in that case it needs to be but back to the events of my buddies table
                addEventToEventOfMyBuddies(event);

            }
        );


    });
}

function addEventToEventOfMyBuddies(event) {
    let {id, description, category, nrOfPlayers, date, creator, interested, participants} = event;
    let {title} = category;

    return $.ajax({
        url: url + "/api/user/" + myId,
        type: "GET",
        dataType: "json"
    }).done((json) => {

        for (const mb  of json.buddies) {
            if (mb === creator.id) {
                let tableRow = (tableRowTemplateBuddies(id, description, title, nrOfPlayers, date, participants));
                $("#buddieTBody").append(tableRow);
                $(`#interestedinevent${id}`).click(() => {
                    console.log("I'm interested in this event!");
                    interested.push(myId);
                    updateEvent(event).done(() => {
                        $(`#tableRow${id}`).remove();
                    }).done(() => {
                        addEventToInterested(event);
                    });
                });
            }
        }
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

/*for (const e of events) {
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

            /!*sessionStorage*!/
            saveEvents(events);
        });
    } else if (interested.indexOf("me") !== -1) {
        let tableRow = (tableRowTemplate(id, description, sport, requestedBuddies, date, participants));
        $("#interestedTBody").append(tableRow);
        $(`#trash${id}`).click(() => {
            interested.splice(interested.indexOf("me"),1);
            fillEventTables();

            /!*sessionStorage*!/
            saveEvents(events);
        });
    } else if(participants.indexOf("me") !== -1){
        let tableRow = (tableRowTemplate(id, description, sport, requestedBuddies, date, participants));
        $("#joinedTBody").append(tableRow);
        $(`#trash${id}`).click(() => {
            participants.splice(participants.indexOf("me"),1);
            fillEventTables();

            /!*sessionStorage*!/
            saveEvents(events);
        });
    } else {
        for(const mb  of mybuddies){
            if(mb === e.creator){
                let tableRow = (tableRowTemplateBuddies(id, description, sport, requestedBuddies, date, participants));
                $("#buddieTBody").append(tableRow);
                $(`#interestedinevent${id}`).click(()=>{
                   e.interested.push("me");
                   fillEventTables();
                });
            }
        }
    }


}*/


function buildInterestedTBodyModal(event) {
    let {interested, participants} = event;
    $('#interestedTBodyModal').empty();
    for (const id of interested) {
        getUser(id).done((json) => {
            let {fullName} = json;
            let tableRow = `<tr id="tableRowInterestedModal${id}">
                        <td>${fullName}</td>
                        <td><a id="decline${id}" href="#"><i class="fa fa-times"></i></a></td>
                        <td><a id="accept${id}" href="#"><i class="fa fa-check"></i></a></td>
                        </tr>`;
            $("#interestedTBodyModal").append(tableRow);
            $(`#decline${id}`).click(() => {
                interested.splice(interested.indexOf(id), 1);
                updateEvent(event).done(() => {
                    buildInterestedTBodyModal(event);
                    fillEventTables();
                    closeInterestedModal();
                });

            });
            $(`#accept${id}`).click(() => {
                interested.splice(interested.indexOf(id), 1);
                participants.push(id);
                updateEvent(event).done(() => {
                    buildInterestedTBodyModal(event);
                    fillEventTables();
                    closeInterestedModal();
                });
            })
        });
    }
}

function closeInterestedModal() {
    let rowCount = $("#interestedTBodyModal > tr").length;
    if (rowCount === 0) {
        $('#interestedModal').modal('toggle')
    }
}


function getUser(id) {
    return $.ajax({
        url: url + "/api/user/" + id,
        type: "GET",
        dataType: "json"
    }).catch(err => console.log(err))
}

function tableRowTemplateMyEvents(id, description, sport, nrOfPlayers, date, interested, participants) {
    let tableRow = `<tr id="tableRow${id}">
                        <td>${description}</td>
                        <td><a id="interested${id}" href="#" data-toggle="modal" data-target="#interestedModal">(${interested.length}) Show </a></td>
                        <td>${sport}</td>
                        <td>${participants.length} /${nrOfPlayers}</td>
                        <td>${date}</td>
                        <td><a id="trash${id}" href="#"><i class="fa fa-trash"></i></a></td>
                        </tr>`;


    return tableRow;
}

function tableRowTemplate(id, description, sport, nrOfPlayers, date, participants) {
    let tableRow = `<tr id="tableRow${id}">
                        <td>${description}</td>
                        <td>${sport}</td>
                        <td>${participants.length} /${nrOfPlayers}</td>
                        <td>${date}</td>
                        <td><a id="trash${id}" href="#"><i class="fa fa-trash"></i></a></td>
                        </tr>`;
    return tableRow;
}


function tableRowTemplateBuddies(id, description, sport, nrOfPlayers, date, participants) {
    let tableRow = `<tr id="tableRow${id}">
                        <td>${description}</td>
                        <td>${sport}</td>
                        <td>${participants.length} /${nrOfPlayers}</td>
                        <td>${date}</td>
                        <td><button id="interestedinevent${id}">I'm interested</button></td>
                        </tr>`;
    return tableRow;
}