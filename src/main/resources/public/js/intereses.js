function guardarCambios() {
    const interesesSeleccionados = document.querySelectorAll('input[name="intereses"]:checked');
    const bañosSeleccionados = document.querySelectorAll('.circle.selected');

    console.log("Intereses Seleccionados:");
    interesesSeleccionados.forEach((interes) => {
        console.log(interes.value);
    });

    console.log("Baños Seleccionados:");
    bañosSeleccionados.forEach((baño) => {
        console.log("Baño");
    });
}

const circles = document.querySelectorAll('.circle');

circles.forEach((circle) => {
    circle.addEventListener('click', () => {
        circle.classList.toggle('selected');
    });
});
