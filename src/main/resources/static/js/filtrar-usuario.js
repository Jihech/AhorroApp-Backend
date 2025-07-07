    document.addEventListener("DOMContentLoaded", function () {
        const filtro = document.getElementById("filtroUsuario");
        const filas = document.querySelectorAll("#tablaMovimientos tbody tr");

        filtro.addEventListener("change", () => {
            const usuarioSeleccionado = filtro.value;
            filas.forEach(fila => {
                const usuarioId = fila.getAttribute("data-usuario-id");
                fila.style.display = (usuarioSeleccionado === "todos" || usuarioSeleccionado === usuarioId) ? "" : "none";
            });
        });
    });