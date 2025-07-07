document.addEventListener('DOMContentLoaded', () => {
        const logoutBtn = document.querySelector('#logout-btn');

        if (logoutBtn) {
            logoutBtn.addEventListener('click', (event) => {
                event.preventDefault();

                Swal.fire({
                    title: '¿Cerrar sesión?',
                    text: 'Se cerrará tu sesión actual',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Sí, salir',
                    cancelButtonText: 'Cancelar'
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = '/logout';
                    }
                });
            });
        }
    });