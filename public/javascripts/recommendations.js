let recomId = 0;
let recomPolygons = [];
myId = sessionStorage.getItem('myId');
let sportData;

let recommendations = L.layerGroup();

let recommendationLayers = [];

$('#carouselExample').on('slide.bs.carousel', function (e) {


    let $e = $(e.relatedTarget);
    let idx = $e.index();
    let itemsPerSlide = 4;
    let totalItems = $('.carousel-item').length;
    console.log('hallo');

    if (idx >= totalItems - (itemsPerSlide - 1)) {
        let it = itemsPerSlide - (totalItems - idx);
        for (let i = 0; i < it; i++) {
            // append slides to end
            if (e.direction == "left") {
                $('.carousel-item').eq(i).appendTo('.carousel-inner');
            }
            else {
                $('.carousel-item').eq(0).appendTo('.carousel-inner');
            }
        }
    }
});


$().ready(() => {

    window.onresize = function () {
        if ($(window).width() < 500) {

            $('#recomInfoDiv').hide();
            $('#recomInfo').hide();
        } else {
            $('#recomInfoDiv').show();
            $('#recomInfo').show();
        }
    };

    // Query that loads only nodes
    let nodesOnly = "http://overpass-api.de/api/interpreter?data=%2F*%0AThis%20has%20been%20generated%20by%20the%20overpass-turbo%20wizard.%0AThe%20original%20search%20was%3A%0A%E2%80%9Csport%3D*%20and%20country%3DSwitzerland%E2%80%9D%0A*%2F%0A%0A%5Bout%3Ajson%5D%5Bmaxsize%3A1073741824%5D%3B%0Aarea%283600051701%29-%3E.searchArea%3B%28node%5B%22sport%22%5D%28area.searchArea%29%3B%3E%3B%29%3Bout%20bb%3B";
    // Query to load everything sport=* from Switzerland
    let allData = "http://overpass-api.de/api/interpreter?data=%2F*%0AThis%20has%20been%20generated%20by%20the%20overpass-turbo%20wizard.%0AThe%20original%20search%20was%3A%0A%E2%80%9Csport%3D*%20and%20country%3D%22Switzerland%22%E2%80%9D%0A*%2F%0A%0A%5Bout%3Ajson%5D%5Bmaxsize%3A1073741824%5D%3B%0Aarea%283600051701%29-%3E.searchArea%3B%28node%5B%22sport%22%5D%28area.searchArea%29%3B%3E%3Bway%5B%22sport%22%5D%28area.searchArea%29%3B%3E%3Brelation%5B%22sport%22%5D%28area.searchArea%29%3B%3E%3B%29%3Bout%20bb%3B";
    // Local file with all data sport=* loads much faster!
    let localData = "/assets/recomData/export.json";
    $.ajax({
        url: localData,
        type: "GET",
        dataType: "json"
    }).done((json) => {


        userAware().done(() => {
            sportData = json;
            buildRecommendations();
            $('#recomInfo').text("Enjoy your recommendations!");
            if ($(window).width() < 500) {
                recommendations.clearLayers();
                $('#carouselExample').hide();
                $('#recomInfo').hide();
            }
            map.on('zoomend moveend', function () {
                if (map.getZoom() <= 12 || $(window).width() < 500) {
                    $('#recomInfo').text("Zoom closer for recommendations!");
                    recommendations.clearLayers();
                    $('#carouselExample').hide();
                } else {
                    $('#carouselExample').show();
                    $('#recomInfo').text("Enjoy your recommendations!");
                    buildRecommendations();
                }
            });


            // If the user is not logged in hide carousel and show
        }).catch(err => {
            console.log("You're not logged in!", err);
            $('#recomInfo').text("Log in for recommendations!");
            $('#carouselExample').hide();
        });
    }).catch(err => console.log(err));


})
;


function buildRecommendations() {
    recomId = 0;
    recomPolygons = [];
    $("#recommendationDiv").empty();
    getUser(myId).done((user) => {
        let categoriesForRecom = [];
        for (const c of user.categories) {
            categoriesForRecom.push(c.title);
        }
        loadSportLocations(categoriesForRecom);
    })
}


let sportTranslation = [{
    cat: "Fussball", eng: "soccer", icon: "fa fa-futbol"
}
    , {cat: "Basketball", eng: "basketball", icon: "fa fa-basketball-ball"}
    , {cat: "Tennis", eng: "tennis", icon: "fa fa-question"}
    , {cat: "Jogging", eng: "athletics", icon: "fas fa-walking"}];


function loadSportLocations(categories) {

    let bounds = map.getBounds();
    console.log(bounds);
    let sports = [];

    for (let i = 0; i < sportTranslation.length; i++) {
        if (categories.includes(sportTranslation[i].cat)) {
            sports.push(sportTranslation[i]);
        }
    }

    // sports that needs to be extracted from the data
    let sportFilter = [];
    for (const s of sports) {
        sportFilter.push(s.eng);
    }


    recommendations.clearLayers();
    recommendationLayers = [];
    $('#carouselBody').empty();
    $.each(sportData.elements, (key, value) => {
        let {tags, type} = value;
        if (tags !== undefined && value !== undefined) {
            if (type === "node") {
                let {lat, lon} = value;
                if (sportFilter.includes(tags.sport) && bounds._southWest.lat < lat && bounds._northEast.lat > lat && bounds._southWest.lng < lon && bounds._northEast.lng > lon) {

                    let icon = sports[(sportFilter.indexOf(tags.sport))].icon;
                    let circle = L.circle([lat, lon], {radius: 25, color: 'red'});
                    recommendationLayers.push({id: value.id, layer: circle, tags: tags});
                    recommendations.addLayer(circle).bindPopup(`<div id="recomPopup${value.id}"><p><i class="${icon}"></i>   <b>${tags.sport}</b></p><div><p id="recomTags${value.id}"></p></div><button id="createEventInRecom" class="btn btn-default" type="button">Create event here!</button></div>`, {
                        maxWidth: "auto", minWidth: 135
                    });
                    $('#carouselBody').append(createCarouselSlide(tags.sport));
                    circle.on('click', () => {
                        let keys = Object.keys(tags);
                        for (const k in keys) {
                            if (keys[k] === "name" || keys[k] === "operator" || keys[k] === "opening_hours") {
                                $(`#recomTags${value.id}`).append(`<b>${keys[k]}:</b>${tags[keys[k]]}<br>`);
                            }
                        }

                    });


                }
            } else if (type === "way") {
                if (sportFilter.includes(tags.sport) && bounds._southWest.lat < value.bounds.minlat && bounds._northEast.lat > value.bounds.maxlat && bounds._southWest.lng < value.bounds.minlon && bounds._northEast.lng > value.bounds.maxlon) {
                    let latlngs = [[value.bounds.minlat, value.bounds.minlon], [value.bounds.maxlat, value.bounds.maxlon]];

                    let icon = sports[(sportFilter.indexOf(tags.sport))].icon;
                    let polygon = L.rectangle(latlngs, {color: 'red'}).bindPopup(`<div id="recomPopup${value.id}"><p><i class="${icon}"></i>   <b>${tags.sport}</b></p><div ><p id="recomTags${value.id}"></p></div><button id="createEventInRecom" class="btn btn-default" type="button">Create event here!</button></div>`, {
                        maxWidth: "auto", minWidth: 135
                    });
                    recommendationLayers.push({id: value.id, layer: polygon, tags: tags});
                    recommendations.addLayer(polygon);
                    $('#carouselBody').append(createCarouselSlide(tags.sport));
                    polygon.on('click', () => {
                        let keys = Object.keys(tags);
                        for (const k in keys) {
                            // if(keys[k] === "name" || keys[k] === "operator" || keys[k] === "opening_hours" ){
                            $(`#recomTags${value.id}`).append(`<b>${keys[k]}:</b>${tags[keys[k]]}<br>`);
                            // }
                        }

                    });
                }
            }

        }

    });

    if ($("#carouselBody").children().length === 0) {

        $('#recomInfo').text("No recommendatons found! Add more favorite sports to your profile!");
        $('#carouselExample').hide();
    }

    $("#carouselBody div:first-child").addClass("active");
    map.addLayer(recommendations);


}

function createCarouselSlide(sport) {
    let template = `<div class="carousel-item col-3"><div class="panel panel-default"><div class="panel-thumbnail"><a href="#" title="image 1" class="thumb"><img class="img-fluid mx-auto d-block" src="/assets/images/recommendations/${sport}.jpg" alt="slide 1"></a></div></div></div>`;
    return template;
}

