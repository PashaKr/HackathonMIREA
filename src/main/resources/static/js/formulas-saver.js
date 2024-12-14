// Обработчик для сохранения формулы
document.getElementById("saveFormulaButton").addEventListener("click", () => {
    const formula = document.getElementById("latexInput").value;
    fetch("/formulas/saveFormula", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ formula: formula }),
    }).then(response => {
        if (response.ok) { // Если статус 2xx
            return response.text();
        } else {
            return response.text().then(text => {
                throw new Error(text);
            });
        }
    }).then(message => {
        alert(message);
    }).catch(error => {
        alert("Ошибка: " + error.message);
    });
});

// Обработчик для загрузки сохранённых формул
document.getElementById("loadFormulasButton").addEventListener("click", () => {
    fetch("/formulas/getFormulas")
        .then(response => response.json())
        .then(data => {
            const formulasList = document.getElementById("formulasList");
            formulasList.innerHTML = ''; // Очистить список перед добавлением новых формул

            data.forEach(formula => {
                const formulaElement = document.createElement("div");
                formulaElement.classList.add("formula-item");

                // Убираем LaTeX разметку KaTeX, чтобы вернуть чистый LaTeX
                const cleanFormula = formula.replace(/<[^>]*>/g, '');  // Убираем HTML теги, оставляем только текст

                formulaElement.innerHTML = `$$${cleanFormula}$$`; // Оборачиваем формулу в $$ для корректного отображения в LaTeX
                formulasList.appendChild(formulaElement);
            });

            // Рендеринг формул с использованием KaTeX
            renderMathInElement(formulasList, {
                delimiters: [
                    { left: "$$", right: "$$", display: true },
                    { left: "\\(", right: "\\)", display: false }
                ]
            });

            // Добавляем обработчики кликов
            setTimeout(() => {
                const formulaItems = document.querySelectorAll('.formula-item');
                formulaItems.forEach(formulaElement => {
                    formulaElement.addEventListener('click', function() {
                        const latexInput = document.getElementById("latexInput");

                        // Извлекаем чистый LaTeX из элемента
                        const formulaText = extractLatexFromHTML(formulaElement.innerHTML);  // Функция извлечения LaTeX

                        // Добавляем формулу в input
                        latexInput.value += formulaText;

                        // Обновляем LaTeX вывод
                        updateLatexOutput();
                    });
                });
                console.log("Обработчики кликов добавлены");
            }, 100);  // Немного задержки для рендеринга

            // Открыть модальное окно с формулами
            const modal = new bootstrap.Modal(document.getElementById("formulasModal"));
            modal.show();
        })
        .catch(() => {
            alert("Ошибка при загрузке формул.");
        });
});

// Функция для извлечения LaTeX из HTML
function extractLatexFromHTML(html) {
    // Используем регулярные выражения для извлечения LaTeX-кода из атрибута annotation
    const match = html.match(/<annotation encoding="application\/x-tex">([^<]+)<\/annotation>/);
    if (match) {
        return match[1];  // Возвращаем LaTeX-код
    }
    return '';  // Если LaTeX не найден, возвращаем пустую строку
}

// Функция для обновления формулы
function updateLatexOutput() {
    const latexInput = document.getElementById('latexInput');
    const latexOutput = document.getElementById('latexOutput');
    const latexCode = latexInput.value;
    latexOutput.innerHTML = '$$' + latexCode + '$$'; // Добавление формулы в LaTeX

    renderMathInElement(latexOutput, {
        delimiters: [
            { left: "$$", right: "$$", display: true },
            { left: "\\(", right: "\\)", display: false }
        ]
    });
}
