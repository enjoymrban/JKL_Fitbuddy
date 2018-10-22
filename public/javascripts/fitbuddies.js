$().ready(() => {
    fillBuddyTable();
    randomBuddy();

    $('#addBuddy').click(() => {
        let buddyToAdd = $("#buddyName").text();

        if(buddyToAdd !== "NO FURTHER OPTIONS") {
            mybuddies.push(buddyToAdd);
            addBuddyToList(buddyToAdd);
            randomBuddy()
        }
    });

    $('#skipBuddy').click(() => {
        randomBuddy();
    });


});

function fillBuddyTable() {
    $('#myBuddyTable').empty();
    for (const b of mybuddies) {
        addBuddyToList(b);
    }
}

function addBuddyToList(buddy) {
    let rowCount = $("#myBuddyTable > tr").length + 1;
    let tableTemplate = `<tr><th scope="row">${rowCount}</th><td id="buddy${rowCount}">${buddy}</td><td><i id="remove${rowCount}" class="fa fa-times"></i></td></tr>`;
    $("#myBuddyTable").append(tableTemplate);

    $(`#remove${rowCount}`).click(() => {
        mybuddies.splice(mybuddies.indexOf($(`#buddy${rowCount}`).text()), 1);
        fillBuddyTable();
        randomBuddy();

    })
}
function test() {
   console.log(Math.floor((Math.random() * buddies.length)));
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
    }catch(e){
        $("#buddyName").text("NO FURTHER OPTIONS");
        return;
    }
}

