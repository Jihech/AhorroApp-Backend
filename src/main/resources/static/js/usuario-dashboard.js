document.addEventListener("DOMContentLoaded", function () {
    const animarNumero = (el, valorFinal) => {
        let valor = 0;
        let incremento = valorFinal / 60;
        const formato = (v) => 'S/. ' + v.toFixed(2);

        const actualizar = () => {
            valor += incremento;
            if (valor >= valorFinal) {
                el.textContent = formato(valorFinal);
            } else {
                el.textContent = formato(valor);
                requestAnimationFrame(actualizar);
            }
        };
        actualizar();
    };

    document.querySelectorAll('.animated-number').forEach(el => {
        const valor = parseFloat(el.getAttribute('data-valor')) || 0;
        animarNumero(el, valor);
    });
});
