document.addEventListener("DOMContentLoaded", function () {
    const fechaInput = document.getElementById("fechaLimiteInput");
    const hoy = new Date().toISOString().split("T")[0];
    if (fechaInput) {
        fechaInput.setAttribute("min", hoy);
    }
});