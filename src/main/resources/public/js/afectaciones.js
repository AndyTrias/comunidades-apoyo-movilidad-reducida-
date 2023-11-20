const switches = document.querySelectorAll('input[type="checkbox"]');
switches.forEach((sw) => {
    const switchHandle = sw.nextElementSibling.querySelector('div');
    const switchLabelObserver = sw.parentNode.previousElementSibling;
    const switchLabelAffected = sw.parentNode.nextElementSibling;
    // Function to update label styles based on checkbox state
    const updateLabels = () => {
        if (sw.checked) {
            switchLabelObserver.classList.add('line-through');
            switchLabelAffected.classList.remove('line-through');
            switchHandle.style.transform = 'translateX(8px)';
        } else {
            switchLabelObserver.classList.remove('line-through');
            switchLabelAffected.classList.add('line-through');
            switchHandle.style.transform = 'translateX(0)';
        }
    };
    // Update label styles when checkbox state changes
    sw.addEventListener('change', updateLabels);
    // Set initial label styles based on checkbox state
    updateLabels();
});
