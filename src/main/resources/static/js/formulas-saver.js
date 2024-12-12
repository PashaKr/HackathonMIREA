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
        // Проверяем статус ответа от сервера
        if (response.ok) { // Если статус 2xx
            return response.text(); // Получаем текст ответа
        } else {
            return response.text().then(text => {
                // Если не 2xx, возвращаем ошибку с сообщением
                throw new Error(text);
            });
        }
    }).then(message => {
        // Вставляем текстовое сообщение от сервера
        alert(message);
    }).catch(error => {
        // Обработка ошибок
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
                formulaElement.innerHTML = `$$${formula}$$`;
                formulasList.appendChild(formulaElement);

                // Добавляем обработчик клика на формулу
                formulaElement.addEventListener('click', function () {
                    // Вставляем формулу в textarea
                    const latexInput = document.getElementById("latexInput");
                    latexInput.value += formula; // Добавляем формулу в конец текста в textarea

                    // Обновляем вывод LaTeX немедленно
                    updateLatexOutput();
                });
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
