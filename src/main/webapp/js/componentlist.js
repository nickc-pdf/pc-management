/**
 * view-controller for component.html
 * @author Nick Camenisch
 */
let delayTimer;

document.addEventListener("DOMContentLoaded", () => {
    readComponents("","");

    document.getElementById("search").addEventListener("keyup", searchComponents);
});

/**
 * reads all components
 * @param field   the attribute to be used as a filter or sort
 * @param filter  the value to be filtered by
 * @param sort    the sort order
 */
function readComponents(field, filter, sort) {
    let url = "./resource/component/list";
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
            showComponentList(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * look up the search-fields and create the filter
 * @param event
 */
function searchComponents(event) {
    const element = event.target;
    const field = element.id;
    let filter = "";
    filter = document.getElementById("componentName").value;

    clearTimeout(delayTimer);
    delayTimer = setTimeout(() => {
        readComponents(field, filter);
    }, 500);
}
/**
 * shows the components as a table
 * @param data  the components
 */
function showComponentList(data) {
    const userRole = getCookie("userRole");
    let tBody = document.getElementById("componentlist");
    tBody.innerHTML = "";
    data.forEach(component => {
        let row = tBody.insertRow(-1);
        let button = document.createElement("button");
        if (userRole === "user" || userRole === "admin")
            button.innerHTML = "&#9998;";
        else
            button.innerHTML = "&#128065;";

        button.type = "button";
        button.name = "editPC";
        button.setAttribute("data-componentid", component.id);
        button.addEventListener("click", editComponent);
        row.insertCell(-1).appendChild(button);

        row.insertCell(-1).innerHTML = component.name;

        if (userRole === "admin") {
            button = document.createElement("button");
            button.innerHTML = "&#128465;";
            button.type = "button";
            button.name = "deletePC";
            button.setAttribute("data-componentid", component.id);
            button.addEventListener("click", deleteComponent);
            row.insertCell(-1).appendChild(button);
        }

    });

    if (userRole === "user" || userRole === "admin") {
        document.getElementById("addButton").innerHTML = "<a href='./componentedit.html'>New Component</a>";
    }
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editComponent(event) {
    const button = event.target;
    const componentID = button.getAttribute("data-componentid");
    window.location.href = "./componentedit.html?id=" + componentID;
}

/**
 * deletes a component
 * @param event  the click-event
 */
function deleteComponent(event) {
    const button = event.target;
    const componentID = button.getAttribute("data-componentid");

    fetch("./resource/component/delete?id=" + componentID,
        {
            method: "DELETE",
            headers: { "Authorization": "Bearer " + readStorage("token")}
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./componentlist.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}