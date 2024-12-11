document.addEventListener("DOMContentLoaded", function () {
    const latexInput = document.getElementById('latexInput');
    const latexOutput = document.getElementById('latexOutput');

    // Функция для обновления формулы
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

    latexInput.addEventListener('input', updateLatexOutput);

    // Открытие модального окна (с использованием Bootstrap)
    window.openModal = function (modalId) {
        const modal = new bootstrap.Modal(document.getElementById(modalId)); // Используем Bootstrap Modal
        modal.show();

        // Добавление обработчика для закрытия при клике вне модального окна
        const modalElement = document.getElementById(modalId);
        modalElement.addEventListener('click', function (event) {
            const modalContent = modalElement.querySelector('.modal-content');
            // Проверяем, был ли клик за пределами модального контента
            if (!modalContent.contains(event.target)) {
                modal.hide(); // Закрыть модальное окно
            }
        });
    };

    // Закрытие модального окна (с использованием Bootstrap)
    window.closeModal = function (modalId) {
        const modal = new bootstrap.Modal(document.getElementById(modalId)); // Используем Bootstrap Modal
        modal.hide();
    };

    // Добавление символов в textarea
    window.addSymbol = function (symbol) {
        const cursorPos = latexInput.selectionStart;
        const textBefore = latexInput.value.substring(0, cursorPos);
        const textAfter = latexInput.value.substring(cursorPos);
        latexInput.value = textBefore + symbol + textAfter;

        latexInput.selectionStart = latexInput.selectionEnd = cursorPos + symbol.length;
        updateLatexOutput();
    };
});
