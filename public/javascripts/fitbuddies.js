$().ready(() => {
    fillBuddyTable();
    randomBuddy();

    $('#addBuddy').click(() => {
        let buddyToAdd = $("#buddyName").text();

        if (buddyToAdd !== "NO FURTHER OPTIONS") {
            addBuddyToList(buddyToAdd);
            randomBuddy();

        }
    });

    $('#skipBuddy').click(() => {
        randomBuddy();
    });


});

function fillBuddyTable() {
    $('#myBuddyTable').empty();
    $.ajax({
        url: url + "/api/user/" + myId,
        type: "GET",
        dataType: "json"
    }).done(me => {
        $.each(me.buddies, (key, value) => {
            addBuddyToList(value);
        });

    });
}

function addBuddyToList(buddy) {
    let rowCount = $("#myBuddyTable > tr").length + 1;
    let tableTemplate = `<tr><th scope="row">${rowCount}</th><td id="buddy${rowCount}">${buddy.fullName}</td><td><i id="remove${buddy.id}" class="fa fa-times"></i></td></tr>`;
    $("#myBuddyTable").append(tableTemplate);

    $(`#remove${buddy.id}`).click(() => {
        $.ajax({
            url: url + "/api/user/" + myId,
            type: "GET",
            dataType: "json"
        }).done(me => {
            $.each(me.buddies, (key, value) => {
                if (buddy.id === value.id) {
                    me.buddies.splice(key, 1);
                }
                $.ajax({
                    url: url + "/api/user/" + myId,
                    type: "PUT",
                    data: JSON.stringify(me),
                    contentType: "application/json",
                    dataType: 'json'
                }).done(me => {

                    fillBuddyTable();
                    randomBuddy();
                });


            });
        });
    });

}





function randomBuddy(buddy = "me") {

    try {
        if (mybuddies.indexOf(buddy) === -1 && buddy !== "me") {
            $("#buddyName").text(buddy);
            return;

        } else {
            let randomIndex = Math.floor((Math.random() * buddies.length));
            return randomBuddy(buddies[randomIndex]);
        }
    } catch (e) {
        $("#buddyName").text("NO FURTHER OPTIONS");
        return;
    }

}
