const delete_btn = document.getElementById("delete_button");
const note_id_element = document.getElementById("note_id");

delete_btn.addEventListener("click", (e) => {
    e.preventDefault();

    const url = "http://localhost:8080/api/notes/" + note_id_element.value;

    fetch(url, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then((res) => {
                alert("Note deleted");
                history.go(-1);
            }
        ).catch((err) => console.log(err));
});
