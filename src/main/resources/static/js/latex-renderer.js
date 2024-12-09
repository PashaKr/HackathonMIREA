document.addEventListener("DOMContentLoaded", function () {
    const latexInput = document.getElementById('latexInput');
    const latexOutput = document.getElementById('latexOutput');

    // Функция добавления символов в textarea
    window.addSymbol = function (symbol) {
        const cursorPos = latexInput.selectionStart;
        const textBefore = latexInput.value.substring(0, cursorPos);
        const textAfter = latexInput.value.substring(cursorPos, latexInput.value.length);
        latexInput.value = textBefore + symbol + textAfter;

        // Перемещаем курсор в конец вставленного текста
        latexInput.selectionStart = latexInput.selectionEnd = cursorPos + symbol.length;

        // Обновляем отображение формулы
        updateLatexOutput();
    };

    // Функция обновления отображения формулы
    const updateLatexOutput = function () {
        const latexCode = latexInput.value;
        latexOutput.innerHTML = '$$' + latexCode + '$$';

        renderMathInElement(latexOutput, {
            delimiters: [
                {left: "$$", right: "$$", display: true},
                {left: "\\(", right: "\\)", display: false}
            ]
        });
    };

    // Обновляем отображение при вводе
    latexInput.addEventListener('input', updateLatexOutput);

    // Функции для открытия и закрытия модальных окон
    window.openModal = function(modalId) {
        document.getElementById(modalId).style.display = "block";
    };

    window.closeModal = function(modalId) {
        document.getElementById(modalId).style.display = "none";
    };
});
