const delete_btn = document.getElementById("delete_button");
const note_id_element = document.getElementById("note_id");
const text = document.getElementById("note_text");
const add_user_btn = document.getElementById("add_button");
const save_btn = document.getElementById("save_button");
const add_user_input = document.getElementById("add_user");

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

save_btn.addEventListener("click", (e) => {
    e.preventDefault();

    const body = {text: text.value};

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

add_user_btn.addEventListener("click", (e) => {
    e.preventDefault();

    if (!add_user_input.value) return;

    fetch(url, {
        method: "PATCH",
        headers: {
            "Content-Type": "text/plain"
        },
        body: add_user_input.value
    })
        .then((res) => {
                alert("User added");
                history.go();
            }
        ).catch((err) => console.log(err));
});
