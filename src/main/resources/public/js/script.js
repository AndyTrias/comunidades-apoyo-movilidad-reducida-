document.addEventListener("DOMContentLoaded", () => {
    const bartender = document.createElement("div");
    bartender.classList.add("bartender");
    for (let i = 0; i < 5; i++) {
        const bar = document.createElement("div");
        bar.classList.add("bar");
        bartender.appendChild(bar);
    }
    document.querySelector("main").appendChild(bartender);

    const a = document.querySelectorAll("a");
    a.forEach((link) => {
        link.addEventListener("click", handleClick);
    });

});

function handleClick(e) {
    e.preventDefault();
    const bars = document.querySelectorAll(".bar");
    bars.forEach((bar) => {
        bar.style.animationPlayState = "running";
    });
    const lastBar = bars[bars.length - 1];
    lastBar.addEventListener("animationend", () => {
        setTimeout(() => {
            window.location = e.target.href;
        }, 500);
    });
}
