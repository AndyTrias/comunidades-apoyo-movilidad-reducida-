document.querySelectorAll(".incidente").forEach((incidente) => {
    incidente.addEventListener("mouseover", (e) => {
        const iframe = document.getElementById("mapa");

        const coordenadas = ["10", "-10"]

        iframe.src = "https://www.google.com/maps/embed?pb=!1m14!1m12!1m3!1d13136.639585903875!2d" + coordenadas[0] + "!3d" + coordenadas[1] +"!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!5e0!3m2!1ses-419!2sar!4v1695827727275!5m2!1ses-419!2sar"
    });

    incidente.addEventListener("mouseout", (e) => {
        const iframe = document.getElementById("mapa");

        const coordenadasIniciales = ["-58.423643724035635", "-34.60011802207687"]

        iframe.src = "https://www.google.com/maps/embed?pb=!1m14!1m12!1m3!1d13136.639585903875!2d" + coordenadasIniciales[0] + "!3d" + coordenadasIniciales[1] +"!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!5e0!3m2!1ses-419!2sar!4v1695827727275!5m2!1ses-419!2sar"
    });
});