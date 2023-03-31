const delete_btn = document.getElementById("delete_button");
const note_id_element = document.getElementById("note_id");
const form = document.getElementById("form");

const url = "http://localhost:8080/api/notes/" + note_id_element.value;

delete_btn.addEventListener("click", (e) => {
    e.preventDefault();

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

form.addEventListener("submit", (e) => {
    e.preventDefault();

    const body = packData();

    fetch(url, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    })
        .then((res) => {
                alert("Note saved");
                history.go(-1);
            }
        ).catch((err) => console.log(err));
});

function packData() {
    const text = document.getElementById("note_text");

    return {
        text: text.value
    };
}
