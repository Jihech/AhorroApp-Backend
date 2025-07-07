document.addEventListener("DOMContentLoaded", function () {
    const params = new URLSearchParams(window.location.search);

    if (params.has("eliminado")) {
        Swal.fire({
            icon: 'success',
            title: 'Eliminado',
            text: 'El usuario fue eliminado correctamente.',
            timer: 2000,
            showConfirmButton: false
        });
    }

    if (params.has("creado")) {
        Swal.fire({
            icon: 'success',
            title: 'Usuario registrado',
            text: 'El nuevo usuario fue guardado.',
            timer: 2000,
            showConfirmButton: false
        });
    }

    if (params.has("actualizado")) {
        Swal.fire({
            icon: 'success',
            title: 'Usuario actualizado',
            text: 'Los datos fueron actualizados correctamente.',
            timer: 2000,
            showConfirmButton: false
        });
    }
});
