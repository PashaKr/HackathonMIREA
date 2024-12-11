document.getElementById("saveImageButton").addEventListener("click", function () {
    const latexInput = document.getElementById("latexInput").value;

    fetch("/api/save-image", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ latex: latexInput }),
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text); });
            }
            return response.text();
        })
        .then(message => alert(message))
        .catch(error => alert("Ошибка: " + error.message));

});