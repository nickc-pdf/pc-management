/**
 * view-controller for manufactureredit.html
 * @author Nick Camenisch
 */
document.addEventListener("DOMContentLoaded", () => {
    readManufacturer();

    document.getElementById("manufacturereditForm").addEventListener("submit", saveManufacturer);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of a publisher
 */
function saveManufacturer(event) {
    event.preventDefault();
    showMessage("", "info");

    const manufacturerForm = document.getElementById("manufacturereditForm");
    const formData = new FormData(manufacturerForm);
    const data = new URLSearchParams(formData);

    let method;
    let url = "./resource/manufacturer/";
    const manufacturerID = getQueryParam("id");
    if (manufacturerID == null) {
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
                showMessage("Saving error", "error");
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
 * reads a manufacturer
 */
function readManufacturer() {
    const manufacturerID = getQueryParam("id");
    fetch("./resource/publisher/read?id=" + manufacturerID, {
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
            showManufacturer(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of a manufacturer
 * @param data  the manufacturer-data
 */
function showManufacturer(data) {
    const userRole = getCookie("userRole");
    document.getElementById("id").value = data.id;
    document.getElementById("name").value = data.name;

    const locked =  !(userRole === "user" || userRole === "admin");
    lockForm("manufacturereditForm", locked);
}

/**
 * redirects to the manufacturerlist
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./manufacturerlist.html";
}
