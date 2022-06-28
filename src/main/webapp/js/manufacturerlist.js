/**
 * view-controller for manufacturerlist.html
 * @author Nick Camenisch
 */
let delayTimer;

document.addEventListener("DOMContentLoaded", () => {
    readManufacturers("","");

    document.getElementById("search").addEventListener("keyup", searchManufacturers);
});

/**
 * reads all manufacturers
 * @param field   the attribute to be used as a filter or sort
 * @param filter  the value to be filtered by
 * @param sort    the sort order
 */
function readManufacturers(field, filter, sort) {
    let url = "./resource/manufacturer/list";
    if (field !== "" && filter !== "") {
        url += "?field=" + field;
        url += "&filter=" + filter;
    }
    fetch(url, {
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
            showManufacturerList(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * look up the search-fields and create the filter
 * @param event
 */
function searchManufacturers(event) {
    const element = event.target;
    const field = element.id;
    let filter = "";
    filter = document.getElementById("manufacturerName").value;

    clearTimeout(delayTimer);
    delayTimer = setTimeout(() => {
        readManufacturers(field, filter);
    }, 500);
}
/**
 * shows the manufacturers as a table
 * @param data  the manufacturers
 */
function showManufacturerList(data) {
    const userRole = getCookie("userRole");
    let tBody = document.getElementById("manufacturerlist");
    tBody.innerHTML = "";
    data.forEach(manufacturer => {
        let row = tBody.insertRow(-1);

        let button = document.createElement("button");
        if (userRole === "user" || userRole === "admin")
            button.innerHTML = "&#9998;";
        else
            button.innerHTML = "&#128065;";

        button.type = "button";
        button.name = "editPC";
        button.setAttribute("data-manufacturerid", manufacturer.id);
        button.addEventListener("click", editManufacturer);
        row.insertCell(-1).appendChild(button);

        row.insertCell(-1).innerHTML = manufacturer.name;


        if (userRole === "admin") {
            button = document.createElement("button");
            button.innerHTML = "&#128465;";
            button.type = "button";
            button.name = "deletePC";
            button.setAttribute("data-manufacturerid", manufacturer.id);
            button.addEventListener("click", deleteManufacturer);
            row.insertCell(-1).appendChild(button);
        }

    });

    if (userRole === "user" || userRole === "admin") {
        document.getElementById("addButton").innerHTML = "<a href='./manufactureredit.html'>New Manufacturer</a>";
    }
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editManufacturer(event) {
    const button = event.target;
    const manufacturerID = button.getAttribute("data-manufacturerid");
    window.location.href = "./manufactureredit.html?id=" + manufacturerID;
}

/**
 * deletes a manufacturer
 * @param event  the click-event
 */
function deleteManufacturer(event) {
    const button = event.target;
    const manufacturerID = button.getAttribute("data-manufacturerid");

    fetch("./resource/manufacturer/delete?id=" + manufacturerID,
        {
            method: "DELETE",
            headers: { "Authorization": "Bearer " + readStorage("token")}
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./manufacturerlist.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}