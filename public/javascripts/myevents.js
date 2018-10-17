$().ready(() => {
    fillEventTables();


});


function fillEventTables() {

    $("#myEventsTBody").empty();
    $("#interestedTBody").empty();
    for (const e of events) {
        let {description, sport, requestedBuddies, date, creator, interested} = e;
        let tableRow = `<tr>
                        <td>${description}</td>
                        <td>${sport}</td>
                        <td>${requestedBuddies}</td>
                        <td>${date}</td>
                        <td><i class="fa fa-trash"></i></td>
                        </tr>`;

        if(creator === "me"){
            $("#myEventsTBody").append(tableRow);
        }else if(interested.indexOf("me") === 0){
            $("#interestedTBody").append(tableRow);
        }

    }


}