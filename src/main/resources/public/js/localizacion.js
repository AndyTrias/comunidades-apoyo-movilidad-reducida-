document.addEventListener("DOMContentLoaded", () => {

    const longitud = document.getElementById("longitud").innerHTML
    const latitud = document.getElementById("latitud").innerHTML

    mapboxgl.accessToken = 'pk.eyJ1IjoiZ3JpY2NlbGxpIiwiYSI6ImNsbm02enBteTI1bTYybW1zcm42eWU5OXYifQ.LyEGBVsVDCzzhzNbdD32tg'; // Nuestro Token de acceso
    var map = new mapboxgl.Map({
        container: 'map', // id del contenedor
        style: 'mapbox://styles/mapbox/streets-v9', // localización del mapa de estilo
        center: [longitud,latitud], // Posición inicial
        zoom: 14 // Zoom inicial
    });

    map.dragPan.disable();

    var marker = new mapboxgl.Marker({
        color: "#9878e0", // Color del marcador (en hexadecimal)
    }) // Inicializamos el marcador
        .setLngLat({lng:longitud,lat:latitud}) // Le asignamos la posición
        .addTo(map); // Añadimos el marcador al mapa
});
