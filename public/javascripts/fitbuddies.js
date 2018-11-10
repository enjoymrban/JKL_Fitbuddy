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
        console.log(me);
        $.each(me.buddies, (key, value) => {
            $.ajax({
                url: url+"/api/user/"+value,
                type: "GET",
                dataType: "json"
            }).done((json) => {
                    addBuddyToList(json);

            }).catch(err => console.log(err));
        });
    });
}

function addBuddyToList(buddy) {
    let {fullName} = buddy;

    let rowCount = $("#myBuddyTable > tr").length + 1;
    let tableTemplate = `<tr><th scope="row">${rowCount}</th><td id="buddy${rowCount}">${fullName}</td><td><i id="remove${buddy.id}" class="fa fa-times clickable"></i></td></tr>`;
    $("#myBuddyTable").append(tableTemplate);

    $(`#remove${buddy.id}`).click(() => {
        $.ajax({
            url: url + "/api/user/" + myId,
            type: "GET",

        }).done(me => {
            $.each(me.buddies, (key, value) => {
                if (buddy.id === value) {
                    me.buddies.splice(key, 1);

                    $.ajax({
                        url: url + "/api/user/" + myId,
                        type: "PUT",
                        data: JSON.stringify(me),
                        contentType: "application/json",

                    }).done(info => {
                        console.log(info);
                        fillBuddyTable();

                        //randomBuddy();
                    }).catch(err => console.log(err));
                }

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
