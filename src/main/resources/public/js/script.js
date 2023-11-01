document.addEventListener("DOMContentLoaded", () => {
    const bartender = document.createElement("div");
    bartender.classList.add("bartender");
    const bar = document.createElement("div");
    bar.classList.add("bar");
    bartender.appendChild(bar);
    document.querySelector("main").appendChild(bartender);


    const a = document.querySelectorAll("a");
    a.forEach((link) => {
        link.addEventListener("click", handleClick);
    });

});

function handleClick(e) {
    e.preventDefault();
    const href = e.currentTarget.href;
    const bar = document.querySelector(".bar");
    bar.style.animationPlayState = "running";
    bar.addEventListener("animationend", () => {
        window.location = href;
    });
}

function showLocalidadField(provinciaValue) {
    document.getElementById("municipioField").style.display = "block";

    var currentURL = window.location.href;

    var newURL = currentURL + (currentURL.includes('?') ? '&' : '?') + 'provincia=' + provinciaValue;

    window.location.href = newURL;
}

function showMunicipioField(localidadValue) {
    document.getElementById("localidadField").style.display = "block";

    var currentURL = window.location.href;

    var newURL = currentURL + (currentURL.includes('?') ? '&' : '?') + 'municipio=' + localidadValue;

    window.location.href = newURL;
}
