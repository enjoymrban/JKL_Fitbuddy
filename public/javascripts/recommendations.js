let recomId = 0;
let recomPolygons = [];
myId = sessionStorage.getItem('myId');
let sportData;

$('#carouselExample').on('slide.bs.carousel', function (e) {


    let $e = $(e.relatedTarget);
    let idx = $e.index();
    let itemsPerSlide = 4;
    let totalItems = $('.carousel-item').length;

    if (idx >= totalItems-(itemsPerSlide-1)) {
        let it = itemsPerSlide - (totalItems - idx);
        for (let i=0; i<it; i++) {
            // append slides to end
            if (e.direction=="left") {
                $('.carousel-item').eq(i).appendTo('.carousel-inner');
            }
            else {
                $('.carousel-item').eq(0).appendTo('.carousel-inner');
            }
        }
    }
});





$().ready(() => {
    /* show lightbox when clicking a thumbnail */
    $('a.thumb').click(function(event){
        event.preventDefault();
        let content = $('.modal-body');
        content.empty();
        let title = $(this).attr("title");
        $('.modal-title').html(title);
        content.html($(this).html());
        $(".modal-profile").modal({show:true});
    });

    // Load all Nodes that contain a sport within Switzerland
    let query = "http://overpass-api.de/api/interpreter?data=%2F*%0AThis%20has%20been%20generated%20by%20the%20overpass-turbo%20wizard.%0AThe%20original%20search%20was%3A%0A%E2%80%9Csport%3D*%20and%20country%3DSwitzerland%E2%80%9D%0A*%2F%0A%0A%5Bout%3Ajson%5D%5Bmaxsize%3A1073741824%5D%3B%0Aarea%283600051701%29-%3E.searchArea%3B%28node%5B%22sport%22%5D%28area.searchArea%29%3B%3E%3B%29%3Bout%20bb%3B";
    let tooMuchData = "http://overpass-api.de/api/interpreter?data=%2F*%0AThis%20has%20been%20generated%20by%20the%20overpass-turbo%20wizard.%0AThe%20original%20search%20was%3A%0A%E2%80%9Csport%3D*%20and%20country%3D%22Switzerland%22%E2%80%9D%0A*%2F%0A%0A%5Bout%3Ajson%5D%5Bmaxsize%3A1073741824%5D%3B%0Aarea%283600051701%29-%3E.searchArea%3B%28node%5B%22sport%22%5D%28area.searchArea%29%3B%3E%3Bway%5B%22sport%22%5D%28area.searchArea%29%3B%3E%3Brelation%5B%22sport%22%5D%28area.searchArea%29%3B%3E%3B%29%3Bout%20bb%3B";
    let localData = "/assets/recomData/export.json";
    $.ajax({
        url: localData,
        type: "GET",
        dataType: "json"
    }).done((json) => {
        userAware().done(() => {
            sportData = json;
            buildRecommendations();

        }).catch(err => console.log("Your not logged in!", err));
    }).catch(err => console.log(err));


})
;

map.on('zoomend moveend', function () {
    // buildRecommendations();
});

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

    $.each(sportData.elements, (key, value) => {
        let {tags, type} = value;
        if (tags !== undefined && value !== undefined) {
            if (type == "node") {
                let {lat, lon} = value;
                if (sportFilter.includes(tags.sport) && bounds._southWest.lat < lat && bounds._northEast.lat > lat && bounds._southWest.lng < lon && bounds._northEast.lng > lon) {

                    let circle = L.circle([lat, lon], {radius: 25, color: 'red'}).addTo(map);

                }
            } else if (type == "way") {
                if (sportFilter.includes(tags.sport) && bounds._southWest.lat < value.bounds.minlat && bounds._northEast.lat > value.bounds.maxlat && bounds._southWest.lng < value.bounds.minlon && bounds._northEast.lng > value.bounds.maxlon) {
                    let latlngs = [[value.bounds.minlat, value.bounds.minlon], [value.bounds.maxlat, value.bounds.maxlon]];
                    let polygon = L.rectangle(latlngs, {color: 'red'}).bindPopup().addTo(map);

                }
            }

        }
        /* let recomDiv = `<div id="recom${recomId}" class="sportRecommendation"><i class="${icon}"></i></div>`;

         $("#recommendationDiv").append(recomDiv);
 */
    });


    /* $.each(json.elements, (key, value) => {
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
*/


}

