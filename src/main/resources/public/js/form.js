document.addEventListener("DOMContentLoaded", function () {
    // Initialize the intlTelInput
    var input = document.querySelector("#telefono");
    var iti = window.intlTelInput(input, {
        utilsScript: "https://your-cdn-url.com/path/to/utils.js", // Replace with the actual CDN URL for utils.js
    });

    // Get the formatted phone number when the form is submitted
    var form = document.querySelector("form");
    form.addEventListener("submit", function (e) {
        e.preventDefault();
        var phoneNumber = iti.getNumber();
        var selectedCountry = iti.getSelectedCountryData().iso2; // Get the selected country code
        // Add the selected country code to the phone number
        input.value = `+${selectedCountry}${phoneNumber}`;
        form.submit();
    });
});



