 document.addEventListener('DOMContentLoaded', function () {
        const tabla = document.querySelectorAll("#tabla-movimientos tr");

        document.querySelectorAll('#usuario, #tipo, #fechaInicio, #fechaFin').forEach(input => {
            input.addEventListener('input', filtrar);
        });

        function filtrar() {
            const usuarioId = document.getElementById('usuario').value;
            const tipo = document.getElementById('tipo').value;
            const fechaInicio = document.getElementById('fechaInicio').value;
            const fechaFin = document.getElementById('fechaFin').value;

            tabla.forEach(row => {
                const cells = row.children;
                const usuario = cells[0].innerText;
                const tipoMov = cells[1].innerText;
                const fecha = cells[4].innerText.split('/').reverse().join('-'); // yyyy-MM-dd

                let mostrar = true;

                if (usuarioId && !row.innerHTML.includes('value="' + usuarioId + '"')) mostrar = false;
                if (tipo && tipo !== tipoMov) mostrar = false;
                if (fechaInicio && fecha < fechaInicio) mostrar = false;
                if (fechaFin && fecha > fechaFin) mostrar = false;

                row.style.display = mostrar ? '' : 'none';
            });
        }
    });