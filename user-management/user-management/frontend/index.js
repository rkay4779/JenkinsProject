const API_URL = "users.json"; // Pointage vers le fichier JSON

function loadUsers() {
    fetch(API_URL)
        .then((response) => {
            if (!response.ok) {
                throw new Error("Erreur lors de la récupération des utilisateurs.");
            }
            return response.json();
        })
        .then((data) => {
            const tableBody = document.getElementById("users-table");
            tableBody.innerHTML = ""; // Vider le tableau avant d'ajouter de nouvelles données

            if (data.length === 0) {
                tableBody.innerHTML = "<tr><td colspan='3'>Aucun utilisateur trouvé</td></tr>";
            } else {
                data.forEach((user) => {
                    const row = document.createElement("tr");

                    row.innerHTML = `
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td>
                            <div class="action-buttons">
                                <button class="add-button" onclick="editUser(${user.id})">Update</button>
                                <button class="delete-button" onclick="deleteUser(${user.id})">Delete</button>
                            </div>
                        </td>
                    `;
                    tableBody.appendChild(row);
                });
            }
        })
        .catch((err) => console.error("Erreur lors du chargement des utilisateurs :", err));
}

window.onload = loadUsers;
