/**
 * view-controller for componentedit.html
 * @author Nick Camenisch
 */
document.addEventListener("DOMContentLoaded", () => {
    readComponent();

    document.getElementById("componentForm").addEventListener("submit", saveAuthor);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of a component
 */
function saveAuthor(event) {
    event.preventDefault();
    showMessage("", "info");

    const componentForm = document.getElementById("componenteditForm");
    const formData = new FormData(componentForm);
    const data = new URLSearchParams(formData);

    let method;
    let url = "./resource/component/";
    const componentID = getQueryParam("id");
    if (componentID == null) {
        method = "POST";
        url += "create";
    } else {
        method = "PUT";
        url += "update";
    }

    fetch(url,
        {
            method: method,
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "Authorization": "Bearer " + readStorage("token")
            },
            body: data
        })
        .then(function (response) {
            if (!response.ok) {
                showMessage("Saving Error", "error");
                console.log(response);
            } else {
                showMessage("Pc saved", "info");
                return response;}
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * reads a component
 */
function readComponent() {
    const componentID = getQueryParam("id");
    fetch("./resource/component/read?id=" + componentID, {
        headers: { "Authorization": "Bearer " + readStorage("token")}
    })
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showComponent(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of a component
 * @param data  the component-data
 */
function showComponent(data) {
    const userRole = getCookie("userRole");
    document.getElementById("id").value = data.id;
    document.getElementById("name").value = data.name;

    const locked =  !(userRole === "user" || userRole === "admin");
    lockForm("componenteditForm", locked);
}

/**
 * redirects to the componentlist
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./componentlist.html";
}