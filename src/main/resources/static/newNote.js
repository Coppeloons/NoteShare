const form = document.getElementById("form");

form.addEventListener("submit", (e) => {
  e.preventDefault();

  const body = packData();

  fetch("http://localhost:8080/notes", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(body)
  })
          .then((res) => console.log(res))
          .catch((err) => console.log(err));
});

function packData() {
  const title = document.getElementById("note_title");
  const text = document.getElementById("note_text");

  return {
    title: title.value,
    text: text.value
  };
}
