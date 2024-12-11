document.getElementById("saveImageButton").addEventListener("click", function () {
    const latexInput = document.getElementById("latexInput").value;

    fetch("/api/save-image", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ latex: latexInput }),
    })
        .then(response => {
            if (response.ok) {
                alert("Изображение успешно сохранено!");
            } else {
                alert("Ошибка при сохранении изображения.");
            }
        })
        .catch(error => console.error("Ошибка:", error));
});