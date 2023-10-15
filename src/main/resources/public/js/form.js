document.addEventListener("DOMContentLoaded", function () {
    // Initialize the intlTelInput
    var input = document.querySelector("#phone");
    var iti = window.intlTelInput(input, {
        utilsScript: "https://your-cdn-url.com/path/to/utils.js", // Replace with the actual CDN URL for utils.js
    });

    // Get the formatted phone number when the form is submitted
    var form = document.querySelector("form");
    form.addEventListener("submit", function (e) {
        e.preventDefault();
        var phoneNumber = document.querySelector("#phone")
        var selectedCountry = iti.getSelectedCountryData().iso2; // Get the selected country code
        // Add the selected country code to the phone number
        var phoneNumberWithCountryCode = `+${selectedCountry}${phoneNumber}`;
        // Assign the phone number with the country code back to the input field
        input.value = phoneNumberWithCountryCode;
        // Send the form with the updated input value
        form.submit();
    });
});



