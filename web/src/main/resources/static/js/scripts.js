
window.addEventListener('DOMContentLoaded', event => {

    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }
});

/**
 * Filter a list of given values and return only those that contain the given keystroke character
 * @param {*} keyStroke 
 * @param {*} data 
 * @returns An array of positions that contain the given keystroke
 */
const educatorFilterPositions = (keyStroke, data) => {
	let filteredData = [];
	for (let i = 0; i < data.length; i++) {
		keyStroke = keyStroke.toLowerCase();
        
		let id = data[i].id;
		let type = data[i].type.toLowerCase();
		let partnerName = data[i].partner.businessName.toLowerCase();
        let startDate = formatDate(data[i].startDate);
        let endDate = formatDate(data[i].endDate);
        let description = data[i].description;

		if (id == keyStroke || type.includes(keyStroke) || startDate.includes(keyStroke) || 
        endDate.includes(keyStroke) || partnerName.includes(keyStroke) || description.includes(keyStroke)) {
			filteredData.push(data[i]);
		}
	}
	return filteredData;
};

/**
 * Filter a list of given values and return only those that contain the given keystroke character
 * @param {*} keyStroke
 * @param {*} data
 * @returns An array of positions that contain the given keystroke
 */
const partnerFilterPositions = (keyStroke, data) => {
	let filteredData = [];
	for (let i = 0; i < data.length; i++) {
		keyStroke = keyStroke.toLowerCase();

		let id = data[i].id;
		let type = data[i].type.toLowerCase();
        let startDate = formatDate(data[i].startDate);
        let endDate = formatDate(data[i].endDate);
        let description = data[i].description.toLowerCase();

		if ( id == keyStroke || type.includes(keyStroke) || startDate.includes(keyStroke) ||
            endDate.includes(keyStroke) || description.includes(keyStroke) )  {
			filteredData.push(data[i]);
		}
	}
	return filteredData;
};

/**
 * Rebuild the given table using only the given data
 * @param {*} data 
 * @param {*} table 
 */
const rebuildEducatorPositionsTable = (data, table) => {
	table.innerHTML = ``;
	for (let i = 0; i < data.length; i++) {
		let row = `<tr>
                    <th scope="row">${data[i].id}</th>
                    <td>${data[i].type}</td>
                    <td>${data[i].partner.businessName}</td>
                    <td>${formatDate(data[i].startDate)}</td>
                    <td>${formatDate(data[i].endDate)}</td>
                    <td>${data[i].description}</td>
                    <td style="display: flex;">
                        <div class="mx-auto">
                            <span class="material-symbols-rounded btn-edit" title="Edit">edit</span>
                            <span class="material-symbols-rounded btn-delete">delete</span>
                        </div>
                    </td>
                   </tr>`;
		table.innerHTML += row;
	}
};

/**
 * Rebuild the given table using only the given data
 * @param {*} data
 * @param {*} table
 */
const rebuildPartnerPositionsTable = (data, table) => {
	table.innerHTML = ``;
	for (let i = 0; i < data.length; i++) {
		let row = `<tr>
                    <th scope="row">${data[i].id}</th>
                    <td>${data[i].type}</td>
                    <td>${formatDate(data[i].startDate)}</td>
                    <td>${formatDate(data[i].endDate)}</td>
                    <td>${data[i].description}</td>
                    <td>${data[i].approved? 'Approved':'Pending'}</td>
                    <td style="display: flex;">
                        <div class="mx-auto">
                            <span class="material-symbols-rounded btn-edit" title="Edit">edit</span>
                            <span class="material-symbols-rounded btn-delete">delete</span>
                        </div>
                    </td>
                   </tr>`;
		table.innerHTML += row;
	}
};

/**
 * Formats a given date as dd/mm/yyyy
 * @param {*} inputDate 
 * @returns A formatted String value of the given date
 */
const formatDate = (inputDate) => {
    let date = new Date(inputDate);
    let day = date.getDate();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();
    return [('0' + day).slice(-2), ('0' + month).slice(-2), year].join("/");
}

/**
 * Checks if the "pending approvals" list is null or empty, and hides the table if true
 */
const hidePositionsPendingTableIfEmpty = () => {
	document.getElementById("pending-approval").hidden = pendingPositions == null || pendingPositions.length === 0;
};