$().ready(() => {
    userAware().done(()=>{
        fillEventTables();
    });

});

function fillEventTables() {

    $("#myEventsTBody").empty();
    $("#interestedTBody").empty();
    $("#joinedTBody").empty();
    $("#buddieTBody").empty();


    getEvents().done((events) => {
        $.each(events, (key, event) => {

            getEvent(event.id).done((event) => {

                let {id, description, category, nrOfPlayers, date, creator, interested, participants} = event;
                let {title} = category;

                if (creator.id === Number(myId)) {
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

                } else if (interested.indexOf(Number(myId)) !== -1) {
                    addEventToInterested(event);

                } else if (participants.indexOf(Number(myId)) !== -1) {
                    let tableRow = (tableRowTemplate(id, description, title, nrOfPlayers, date, participants));
                    $("#joinedTBody").append(tableRow);
                    $(`#trash${id}`).click(() => {
                        console.log("update event, remove me from participating");
                        participants.splice(participants.indexOf(Number(myId)), 1);
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
        $.ajax({
            type: "GET",
            url: url + "/api/leaveEvent/" + id
        }).done(msg => {
            console.log("I'm not interested in this event anymore");
            $(`#tableRow${id}`).remove();
            // We need to check whether this event belongs to a fitbuddy of mine, if so after removel but it in the table of mybuddies
            addEventToEventOfMyBuddies(event);
        }).catch(err => {
            console.log(err);
        });
    });
}

function addEventToEventOfMyBuddies(event) {
    let {id, description, category, nrOfPlayers, date, creator, interested, participants} = event;
    let {title} = category;

    return getUser(Number(myId)).done((json) => {
        for (const mb  of json.buddies) {
            if (mb === creator.id) {
                let tableRow = (tableRowTemplateBuddies(id, description, title, nrOfPlayers, date, participants));
                $("#buddieTBody").append(tableRow);
                $(`#interestedinevent${id}`).click(() => {
                    console.log("I'm interested in this event!");
                    imInterested(id).done(() => {
                        $(`#tableRow${id}`).remove();
                    }).done(() => {
                        addEventToInterested(event);
                    });
                });
            }
        }
    }).catch(err => console.log(err))
}


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