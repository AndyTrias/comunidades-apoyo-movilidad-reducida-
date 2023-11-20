document.addEventListener("DOMContentLoaded", function () {
    var input = document.querySelector("#telefono");
    var iti = window.intlTelInput(input, {
        utilsScript: "https://cdn.jsdelivr.net/npm/intl-tel-input@18.2.1/build/js/utils.js",
    });

    var form = document.querySelector("form");
    form.addEventListener("submit", function (e) {
        e.preventDefault();
        var phoneNumber = iti.getNumber();
        input.value = phoneNumber;
        form.submit();
    });
});



