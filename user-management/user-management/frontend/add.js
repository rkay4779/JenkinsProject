const API_URL = "http://localhost:8080/users";

document.getElementById("add-user-form").addEventListener("submit", function (e) {
    e.preventDefault();

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;

    const newUser = { name, email };

    fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newUser),
    })
        .then(() => {
            alert("Utilisateur ajouté avec succès !");
            window.location.href = "index.html"; // Retourner à la page principale
        })
        .catch(err => console.error("Erreur lors de l'ajout de l'utilisateur :", err));
});

// Retourner à la page principale
function goToUsers() {
    window.location.href = "index.html";
}
