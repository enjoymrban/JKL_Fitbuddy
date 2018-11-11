$().ready(() => {
    fillBuddyTable();
    randomBuddy();

    $('#addBuddy').click(() => {
        let buddyToAddId = $(".card").attr("id");

        if (buddyToAddId !== "NO_FURTHER_OPTIONS") {
            getUser(buddyToAddId).done((buddy)=>{
                addBuddyToList(buddy);
                randomBuddy();
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

        getUser(myId).done((me)=>{
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


function randomBuddy(buddyId = myId) {

    getUser(myId).done((me)=>{
        getUsers().done((users)=>{
            try {
                if (me.buddies.indexOf(buddyId) === -1 && buddyId !== myId) {
                    for(const u of users){
                        if(u.id === buddyId){
                            getUser(buddyId).done((buddy)=>{
                                $("#buddyName").text(buddy.fullName);

                                // TODO id of the random Buddy is placed as the id of the card.. change eventually
                                $(".card").attr("id",buddy.id);
                                $("#buddyDescription").text(buddy.description);
                                $("#buddyImg").attr("src",`${buddy.avatarUrl}`);
                                $("#buddyImg").on('error',()=>{
                                    $("#buddyImg").attr("src",`/assets/images/whitesmile.png`);
                                });
                                return;
                            });


                        }
                    }

                } else {
                    let randomIndex = Math.floor((Math.random() * users.length));
                    return randomBuddy(users[randomIndex].id);
                }
            } catch (e) {
                $(".card").attr("id","NO_FURTHER_OPTIONS");
                $("#buddyDescription").text();
                $("#buddyImg").attr("src",`NO_FURTHER_OPTIONS`);
                return;
            }
        })
    });





}
