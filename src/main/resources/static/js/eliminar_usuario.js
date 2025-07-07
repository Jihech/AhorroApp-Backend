document.addEventListener("DOMContentLoaded", function () {
    const botones = document.querySelectorAll(".btn-eliminar");

    botones.forEach(boton => {
        boton.addEventListener("click", function (e) {
            e.preventDefault();
            const id = this.getAttribute("data-id");
            const url = `/usuarios/eliminar/${id}`;

            Swal.fire({
                title: '¿Estás seguro?',
                text: "Esta acción no se puede deshacer.",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#e3342f',
                cancelButtonColor: '#6c757d',
                confirmButtonText: 'Sí, desactivar',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = url;
                }
            });
        });
    });
});
