let me;
let users;

let addBuddyButton = $('#addBuddy');
let skipBuddyButton = $('#skipBuddy');

$().ready(() => {
    userAware().done(() => {
        getUser(myId).done((myData) => {

            me = myData;

            getUsers().done((allUsers) => {
                users = allUsers;

                fillBuddyTable();
                randomBuddy();
            });
        });
    });


    addBuddyButton.click(() => {
        let buddyToAddId = $(".card").attr("id");
        addBuddyButton.prop("disabled", true);

        if (buddyToAddId !== "NO_FURTHER_OPTIONS") {
            getUser(buddyToAddId).done((buddy) => {

                addBuddyToList(buddy);

                me.buddies.push(buddy.id);
                updateUser(myId, me).done(() => {
                    console.log('Added User to my buddies');
                    randomBuddy();
                }).catch(err => console.log(err));

            });
        }
    });

    $('#skipBuddy').click(() => {
        randomBuddy();
    });


});

function fillBuddyTable() {

    $('#myBuddyTable').empty();
    getUser(myId).done((me) => {
        $.each(me.buddies, (key, value) => {
            getUser(value).done((json) => {
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
                    randomBuddy();
                }).catch(err => console.log(err));
            }

        });
    });
}

function randomBuddy(buddyId = myId) {
    skipBuddyButton.prop("disabled", false);
    if (me.buddies.indexOf(Number(buddyId)) === -1 && String(buddyId) !== myId) {
        for (const u of users) {
            if (u.id === buddyId) {
                getUser(buddyId).done((buddy) => {
                    $("#buddyName").text(buddy.fullName);

                    // ToDo id of the random Buddy is placed as the id of the card.. looking for a better solution
                    $(".card").attr("id", buddy.id);
                    $("#buddyDescription").text(buddy.description);
                    $("#buddyImg").attr("src", `${buddy.avatarUrl}`);
                    let sportsInCommonCount = sportsInCommon(buddy.categories);
                    $("#sportsInCommon").text(sportsInCommonCount);

                    $("#buddyImg").on('error', () => {
                        $("#buddyImg").attr("src", `/assets/images/whitesmile.png`);
                    });

                    addBuddyButton.prop("disabled", false);
                    if (me.buddies.length === users.length - 2) {
                        skipBuddyButton.prop("disabled", true);
                    }
                });
            }
        }

    } else {
        if (me.buddies.length === users.length - 1) {
            $("#buddyName").text("NO_FURTHER_OPTIONS");
            $(".card").attr("id", "NO_FURTHER_OPTIONS");
            $("#buddyDescription").text("");
            $("#buddyImg").attr("src", `/assets/images/whitesmile.png`);
            addBuddyButton.prop("disabled", true);
            skipBuddyButton.prop("disabled", true);
            return;
        }
        let randomIndex = Math.floor((Math.random() * users.length));
        return randomBuddy(users[randomIndex].id);

    }
}


function sportsInCommon(buddyCategories) {
    let myCategories = me.categories;
    let incommonCount = 0;
    if (buddyCategories.length === 0 || myCategories === 0) {
        return 0;
    } else {
        for (let c = 0; c < myCategories.length; c++) {
            for (let cb = 0; cb < buddyCategories.length; cb++) {
                if(myCategories[c].id === buddyCategories[cb].id){
                    incommonCount++;
                }
            }
        }
    }
    return incommonCount;
}
