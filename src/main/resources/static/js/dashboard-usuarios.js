document.addEventListener("DOMContentLoaded", function () {
    const ctx = document.getElementById("graficoUsuarios");

    if (!ctx) return;

    const labels = window.labelsUsuarios || [];
    const ingresos = window.ingresosUsuarios || [];
    const gastos = window.gastosUsuarios || [];

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Ingresos',
                    data: ingresos,
                    backgroundColor: 'rgba(54, 162, 235, 0.6)'
                },
                {
                    label: 'Gastos',
                    data: gastos,
                    backgroundColor: 'rgba(255, 99, 132, 0.6)'
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Ingresos vs Gastos por Usuario'
                },
                legend: {
                    position: 'top'
                }
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
});
