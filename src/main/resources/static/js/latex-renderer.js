document.addEventListener("DOMContentLoaded", function () {
    const latexInput = document.getElementById('latexInput');
    const latexOutput = document.getElementById('latexOutput');
    const saveImageButton = document.getElementById('saveImageButton');

    // Функция для обновления формулы
    const updateLatexOutput = function () {
        const latexCode = latexInput.value;
        latexOutput.innerHTML = '$$' + latexCode + '$$'; // Добавление формулы в LaTeX
        renderMathInElement(latexOutput, {
            delimiters: [
                { left: "$$", right: "$$", display: true },
                { left: "\\(", right: "\\)", display: false }
            ]
        });
    };

    latexInput.addEventListener('input', updateLatexOutput);

    // Открытие модального окна (с использованием Bootstrap)
    window.openModal = function (modalId) {
        const modal = new bootstrap.Modal(document.getElementById(modalId));
        modal.show();

        // Добавление обработчика для закрытия при клике вне модального окна
        const modalElement = document.getElementById(modalId);
        modalElement.addEventListener('click', function (event) {
            const modalContent = modalElement.querySelector('.modal-content');
            if (!modalContent.contains(event.target)) {
                modal.hide();
            }
        });
    };

    // Закрытие модального окна
    window.closeModal = function (modalId) {
        const modal = new bootstrap.Modal(document.getElementById(modalId));
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

    // Сохранение формулы в изображение
    saveImageButton.addEventListener('click', function () {
        const svg = latexOutput.querySelector('svg');
        if (svg) {
            const serializer = new XMLSerializer();
            const svgBlob = new Blob([serializer.serializeToString(svg)], { type: 'image/svg+xml;charset=utf-8' });
            const url = URL.createObjectURL(svgBlob);

            const downloadLink = document.createElement('a');
            downloadLink.href = url;
            downloadLink.download = 'formula.svg';
            document.body.appendChild(downloadLink);
            downloadLink.click();
            document.body.removeChild(downloadLink);

            URL.revokeObjectURL(url);
        } else {
            alert('Нет формулы для сохранения. Пожалуйста, введите корректный LaTeX.');
        }
    });

    // Загружаем сохраненные формулы и отображаем их в модальном окне
    document.getElementById("loadFormulasButton").addEventListener("click", () => {
        fetch("/formulas/getFormulas") // Получаем список формул с сервера
            .then(response => response.json())
            .then(data => {
                const formulasList = document.getElementById("formulasList");
                formulasList.innerHTML = ''; // Очистить список перед добавлением новых формул

                // Добавляем каждую формулу в модальное окно
                data.forEach(formula => {
                    const formulaElement = document.createElement("div");
                    formulaElement.classList.add("formula-item");
                    formulaElement.innerHTML = `$$${formula}$$`; // Оборачиваем формулу в $$ для корректного отображения в LaTeX
                    formulasList.appendChild(formulaElement);
                });

                // Рендеринг формул с использованием KaTeX
                renderMathInElement(formulasList, {
                    delimiters: [
                        { left: "$$", right: "$$", display: true },
                        { left: "\\(", right: "\\)", display: false }
                    ]
                });

                // Открыть модальное окно с формулами
                const modal = new bootstrap.Modal(document.getElementById("formulasModal"));
                modal.show();
            })
            .catch(() => {
                alert("Ошибка при загрузке формул.");
            });
    });
});
