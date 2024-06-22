document.getElementById('updateForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const token = localStorage.getItem('token');
    const url = this.action;

    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {

        document.getElementById('updateMessage').textContent = 'Требования обновлены';
    })
        .catch(error => {

        console.error('Ошибка при обновлении требований:', error);
        document.getElementById('updateMessage').textContent = 'Ошибка при обновлении требований';
    });
});


const tableBody = document.querySelector('tbody');
const rows = tableBody.querySelectorAll('tr');

rows.forEach((row, index) => {
    const counterCell = document.createElement('td');
    counterCell.textContent = index + 1;
    row.insertBefore(counterCell, row.firstChild);

});


const tableData = document.querySelectorAll('tbody tr');
const skills = [];
for (let i = 0; i < 40; i++) {
    const skillName = tableData[i].querySelector('td:nth-child(2)').textContent;
    const skillCount = tableData[i].querySelector('td:nth-child(3)').textContent;

    skills.push({
        name: skillName,
        count: skillCount
    });
}

// Create a new chart object
const chart = new Chart(document.getElementById('myChart'), {
    type: 'pie',
    data: {
        labels: skills.map(skill => skill.name),
        datasets: [{
            data: skills.map(skill => skill.count),
            backgroundColor: ['#3366cc', '#dc3912', '#ff9900', '#109618',
                '#990099', '#0099c6', '#dd4477', '#66aa00', '#b82e2e',
                '#316395', '#994499', '#22aa99', '#aaaa11', '#6633cc',
                '#e67300', '#8b0707', '#651067', '#329262', '#5574a6',
                '#3b3eac', '#b77322', '#16d620', '#b91383', '#f4359e',
                '#9c5935', '#a9c413', '#2a778d', '#668d1c', '#bea413',
                '#0c5922']
        }]
    },
    options: {
        title: {
            display: true,
            text: 'Требуемые навыки'
        },
        legend: {
            position: 'bottom'
        }
    }
});