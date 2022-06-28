/**
 * view-controller for pcedit.html
 * @author Nick Camenisch
 */
document.addEventListener("DOMContentLoaded", () => {
    readComponents();
    readManufacturers();
    readPCs();

    document.getElementById("pceditForm").addEventListener("submit", savepc);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of a pc
 */
function savepc(event) {
    event.preventDefault();
    showMessage("", "info");

    const pcForm = document.getElementById("pceditForm");
    const formData = new FormData(pcForm);
    const data = new URLSearchParams(formData);

    let method;
    let url = "./resource/pc/";
    const pcID = getQueryParam("id");
    if (pcID == null) {
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
 * reads a pc
 */
function readPCs() {
    const pcID = getQueryParam("id");
    fetch("./resource/pc/read?id=" + pcID, {
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
            showPC(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of a pc
 * @param data  the pc-data
 */
function showPC(data) {
    const userRole = getCookie("userRole");
    document.getElementById("id").value = data.id;
    document.getElementById("name").value = data.title;
    document.getElementById("component").value = data.component;
    document.getElementById("price").value = data.price;

    selectComponent(data.componentlist);
    const locked =  !(userRole === "user" || userRole === "admin");
    lockForm("pceditForm", locked);
}

/**
 * reads all manufacturers as an array
 */
function readManufacturers() {

    fetch("./resource/manufacturer/list", {
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
            showManufacturers(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows all manufacturer as a dropdown
 * @param data
 */
function showManufacturers(data) {
    let dropdown = document.getElementById("manufacturer");
    data.forEach(manufacturer => {
        let option = document.createElement("option");
        option.text = manufacturer.name;
        option.value = manufacturer.id;
        dropdown.add(option);
    })
}

/**
 * reads all components as an array
 */
function readComponents() {

    fetch("./resource/component/list", {
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
            showComponents(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows all components as a dropdown
 * @param data
 */
function showComponents(data) {
    let dropdown = document.getElementById("component");
    data.forEach(component => {
        let option = document.createElement("option");
        option.id = component.id;
        option.text = component.name;
        option.value = component.id;
        dropdown.add(option);
    })
}

function selectComponent(componentList) {
    componentList.forEach(component => {
        document.getElementById(component.id).selected = true;
    })
}
/**
 * redirects to the pcmanagement
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./pcmanagement.html";
}