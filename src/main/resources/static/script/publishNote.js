document.addEventListener("click", (e) => {

    if (!e.target.classList.contains("share_button")) return;
    const someArticle = e.target.parentElement;
    const someAnchorTag = someArticle.firstElementChild;

    const title = someAnchorTag.getElementsByClassName("note_title")[0].value;
    const text = someAnchorTag.getElementsByClassName("note_text")[0].value;
    const users = someAnchorTag.getElementsByClassName("note_users")[0].value;

    const body = {title, text, users};

    fetch("http://localhost:8080/api/noteHub", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    })
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
});
