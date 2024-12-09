document.addEventListener("DOMContentLoaded", function () {
    // Функция рендеринга LaTeX в реальном времени
    const latexInput = document.getElementById('latexInput');
    const latexOutput = document.getElementById('latexOutput');

    // Слушаем изменения в textarea
    latexInput.addEventListener('input', function () {
        const latexCode = latexInput.value;
        latexOutput.innerHTML = '$$' + latexCode + '$$';

        // Рендерим LaTeX формулу с помощью KaTeX
        renderMathInElement(latexOutput, {
            delimiters: [
                {left: "$$", right: "$$", display: true},
                {left: "\\(", right: "\\)", display: false}
            ]
        });
    });
});
