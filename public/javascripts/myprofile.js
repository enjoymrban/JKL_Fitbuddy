// For multiselect
// https://developer.snapappointments.com/bootstrap-select/

let fullName = $('#fullName');
let email = $('#email');
let descriptionToUpdate = $('#description');
let selectFavoriteSports = $('#selectFavoriteSports');
let sendProfileChangesB = $('#sendProfileChanges');
let myFavorites = [];
let myProfile = "";



$().ready(() => {
    fillProfileForm();




    sendProfileChangesB.on('click', ()=>{
        let categoriesToUpdate = [];

        for(const c of selectFavoriteSports.val()){
            categoriesToUpdate.push({id:Number(c)})

        }

        let userToUpdate = {
            description: descriptionToUpdate.val(),
            firstName: myProfile.firstName,
            lastName: myProfile.lastName,
            fullName: myProfile.fullName,
            email: myProfile.email,
            avatarUrl: myProfile.avatarUrl,
            categories: categoriesToUpdate,
            buddies: myProfile.buddies


        };

        $.ajax({

            type: "PUT",
            url: url+"/api/user/"+myId,
            data: JSON.stringify(userToUpdate),
            contentType: "application/json",

        }).done(msg =>{
            $("#sentSuccess").show();
            $("#sentDanger").hide();

        }).catch(err =>{
            $("#sentSuccess").hide();
            $("#sentDanger").show();
            console.log(err);

        });

    })


});


function fillFavSportsSelect(){
    selectFavoriteSports.empty();
    $.ajax({
        url: url + "/api/category",
        type: "GET",
        dataType: "json"
    }).done((json) => {
        $.each(json, (key, value) => {
            selectFavoriteSports.append($('<option>', {

                value: value.id,
                text: value.title
            }));

            selectFavoriteSports.val(myFavorites);
            selectFavoriteSports.selectpicker("refresh");

        });

    });
}


function fillProfileForm(){
    $.ajax({
        url: url + "/api/user/"+myId,
        type: "GET",
        dataType: "json"
    }).done((json) => {
        myProfile = json;
        fullName.attr('placeholder',json.fullName);
        email.attr('placeholder',json.email);
        descriptionToUpdate.text(json.description);

        for(const c of json.categories){
            myFavorites.push(c.id);
        }
        fillFavSportsSelect();
    });
}

