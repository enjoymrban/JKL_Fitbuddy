let recomId = 0;
let recomPolygons = [];

$().ready(() => {
    recomId = 0;
    recomPolygons = [];
    if (myId < 0) {
        console.log('No User signed in!')
    } else {
        $('#showRecomB').click(() => {
            buildRecommendations();
        })

    }


});

map.on('zoomend moveend', function () {
    // buildRecommendations();
});

function buildRecommendations() {
    recomId = 0;
    recomPolygons = [];
    $("#recommendationDiv").empty();
    getUser(myId).done((user) => {
        for (const cat of user.categories) {
            console.log(cat.title);
            loadSportLocations(cat.title);
        }
    })
}

function buildOverpassApiUrl(map, overpassQuery) {
    let bounds = map.getBounds().getSouth() + ',' + map.getBounds().getWest() + ',' + map.getBounds().getNorth() + ',' + map.getBounds().getEast();
    let nodeQuery = 'node[sport= ' + overpassQuery + '](' + bounds + ');';
    let wayQuery = 'way[sport=' + overpassQuery + '](' + bounds + ');';
    let relationQuery = 'relation[sport=' + overpassQuery + '](' + bounds + ');';
    let query = '?data=[out:json][timeout:2][maxsize:1000000];(' + nodeQuery + wayQuery + relationQuery + ');out body geom;';
    return query;

}

let sportIcons = [{cat: "Fussball", eng: "soccer", icon: "fa fa-futbol"}, {
    cat: "Basketball",
    eng: "basketball",
    icon: "fa fa-basketball-ball"
}, {cat: "Tennis", eng: "tennis", icon: "fa fa-question"}, {cat: "Jogging", eng: "athletics", icon: "fas fa-walking"}];


function loadSportLocations(category) {

    let sport = "";
    let icon = "";
    for (let i = 0; i < sportIcons.length; i++) {
        if (category === sportIcons[i].cat) {
            sport = sportIcons[i].eng;
            icon = sportIcons[i].icon;
            break;
        } else {
            if (i === sportIcons.length - 1) {
                return "Sport translation failed";
            }
        }
    }


    let query = buildOverpassApiUrl(map, sport);
    let url = 'http://overpass-api.de/api/interpreter';
    $.ajax({
        url: url + query,
        type: "GET",
        dataType: "json"
    }).done((json) => {
        console.log(json);
        if (json.elements.length > 0) {
            $.each(json.elements, (key, value) => {
                let recomDiv = `<div id="recom${recomId}" class="sportRecommendation"><i class="${icon}"></i></div>`;

                $("#recommendationDiv").append(recomDiv);

                let latlngs = value.geometry;
                let polygon = L.polygon(latlngs, {color: 'red'});
                recomPolygons.push({id: recomId, polygon: polygon});

                $(`#recom${recomId}`).hover((target) => {
                    console.log(target.currentTarget.id);
                    for (const poly of recomPolygons) {
                        if ("recom" + poly.id === target.currentTarget.id) {
                            poly.polygon.addTo(map);
                        }
                    }
                }, (target) => {
                    for (const poly of recomPolygons) {
                        if ("recom" + poly.id === target.currentTarget.id) {
                            poly.polygon.remove();
                        }
                    }
                });
                recomId++;

                return key < 2;


            });

        }


    });


}

