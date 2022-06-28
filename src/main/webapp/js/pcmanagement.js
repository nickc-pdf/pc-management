/**
 * view-controller for pcmanagement.html
 * @author Nick Camenisch
 */
let delayTimer;

document.addEventListener("DOMContentLoaded", () => {
    showHeadings();
    readPCs();

    document.getElementById("search").addEventListener("keyup", searchPCs);
});

/**
 * reads all pcs
 */
function readPCs() {
    let url = "./resource/pc/list";
    let sorting = getSort();
    url += "?sortField=" + sorting[0];
    url += "&sort=" + sorting[1];

    let filter = getFilter();
    url += "&filterField=" + filter[0];
    url += "&filter=" + filter[1];

    fetch(url, {
        headers: {"Authorization": "Bearer " + readStorage("token")}
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
            showPcList(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * look up the search-fields and create the filter
 * @param event
 */
function searchPCs(event) {
    const searchFields = ["nameFilter", "componentFilter", "manufacturerFilter", "priceFilter"]
    const element = event.target;
    const field = element.id;
    let filter = "";

    searchFields.forEach(searchField => {
        let element = document.getElementById(searchField);
        if (searchField === field) {
            filter = element.value;
        } else {
            element.value = "";
        }

    });
    sessionStorage.setItem("filterField", field);
    sessionStorage.setItem("filterValue", filter);

    clearTimeout(delayTimer);
    delayTimer = setTimeout(() => {

        readPCs();
    }, 500);
}

/**
 * shows the pcList as a table
 * @param data  the pcs
 */
function showPcList(data) {
    const userRole = getCookie("userRole");
    showHeadings();
    let tBody = document.getElementById("pclist");
    tBody.innerHTML = "";
    data.forEach(pc => {
        let row = tBody.insertRow(-1);

        let button = document.createElement("button");
        if (userRole === "user" || userRole === "admin")
            button.innerHTML = "&#9998;";
        else
            button.innerHTML = "&#128065;";
        button.type = "button";
        button.name = "editPc";
        button.setAttribute("data-pcid", pc.id);
        button.addEventListener("click", editPc);
        row.insertCell(-1).appendChild(button);

        row.insertCell(-1).innerHTML = pc.name;
        row.insertCell(-1).innerHTML = pc.component;
        row.insertCell(-1).innerHTML = pc.manufacturer;
        row.insertCell(-1).innerHTML =
            pc.price.toLocaleString("de-CH", {
                style: "currency",
                currency: "CHF",
                maximumFractionDigits: 2,
                minimumFractionDigits: 2
            });
        row.insertCell(-1).innerHTML = pc.isbn;

        if (userRole === "admin") {
            button = document.createElement("button");
            button.innerHTML = "&#128465;";
            button.type = "button";
            button.name = "deletePc";
            button.setAttribute("data-pcid", pc.id);
            button.addEventListener("click", deletePc);
            row.insertCell(-1).appendChild(button);
        }

    });

    if (userRole === "user" || userRole === "admin") {
        document.getElementById("addButton").innerHTML = "<a href='./pcedit.html'>New PC</a>";
    }
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editPc(event) {
    const button = event.target;
    const pcID = button.getAttribute("data-pcid");
    window.location.href = "./pcedit.html?id=" + pcID;
}

/**
 * deletes a pc
 * @param event  the click-event
 */
function deletePc(event) {
    const button = event.target;
    const pcID = button.getAttribute("data-pcid");

    fetch("./resource/pc/delete?id=" + pcID,
        {
            method: "DELETE",
            headers: {"Authorization": "Bearer " + readStorage("token")}
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./pcmanagement.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * gets the sort field and order
 */
function getSort() {
    let sortField = readStorage("sortField");
    let sortOrder = readStorage("sortOrder");
    if (!sortField || sortField.length === 0) {
        sortField = "name";
        sortOrder = "ASC";
    }
    return [sortField, sortOrder];
}

function getFilter() {
    let filterField = readStorage("filterField");
    let filterOrder = readStorage("filterValue");
    if (!filterField || filterField.length === 0) {
        filterField = "";
        filterOrder = "";
    }
    return [filterField, filterOrder];
}
function showHeadings() {
    const sort = getSort();
    const ids = ["name", "components", "manufacturer", "price"];
    const labels = ["Name", "Comonents", "Manufacturer", "Price"];

    let row = document.getElementById("headings");
    row.innerText = "";
    row.insertCell(-1);
    for (let i=0; i<labels.length; i++) {
        let cell = row.insertCell(-1);
        if (ids[i] !== sort[0]) {
            cell.innerHTML = labels[i];
        } else if (sort[1] === "ASC") {
            cell.innerHTML = "&uarr;&nbsp;" + labels[i];
        } else {
            cell.innerHTML = labels[i] + "&darr;&nbsp;";
        }
        cell.id=ids[i];
        cell.addEventListener("click", setSort);
    }
}

/**
 * sets the field and order for sorting
 * @param event
 */
function setSort(event) {
    let sort = getSort();
    let field = event.target.id;
    let order = "ASC";
    if (field === sort[0]) {
        if (sort[1] === "ASC") {
            order = "DESC";
        }
    }
    sessionStorage.setItem("sortField", field);
    sessionStorage.setItem("sortOrder", order);
    readPCs();
}