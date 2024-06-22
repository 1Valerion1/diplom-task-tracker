let currentPage = 1;
const questionsPerPage = 50;

// Глобальная переменная для хранения всех вопросов
let allQuestions = [];

function nextPage() {
    currentPage++;
    displayQuestions();
}

function previousPage() {
    if (currentPage > 1) {
        currentPage--;
        displayQuestions();
    }
}

function displayQuestions() {
    const startIndex = (currentPage - 1) * questionsPerPage;
    const endIndex = Math.min(startIndex + questionsPerPage, allQuestions.length);
    const paginatedQuestions = allQuestions.slice(startIndex, endIndex);
    updateTable(paginatedQuestions);

    // Обновление состояния кнопок
    document.querySelector('#pagination button[onclick="previousPage()"]').disabled = currentPage === 1;
    document.querySelector('#pagination button[onclick="nextPage()"]').disabled = endIndex >= allQuestions.length;
}

function showUpdateForm(id) {
    const question = allQuestions.find(q => q.id === id);

    document.getElementById('updateId').value = question.id;
    document.getElementById('updateTitle').value = question.titleQuestions;
    document.getElementById('updateAnswer').value = question.answer;
    document.querySelector('.themeSelect').value = question.themeId;
    document.getElementById('updateLinks').value = question.links;

    document.getElementById('updateForm').style.display = 'block';
}
function showDeleteForm(id) {
    const confirmDelete = confirm('Вы уверены, что хотите удалить этот вопрос?');

    if (confirmDelete) {
        deleteQuestion(id);
    }
}
function deleteQuestion(id) {

    fetch(`http://localhost:8081/api/v1/questions/delete/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
        if (response.ok) {

            fetchQuestionsByTheme();
        } else {
            console.error('Ошибка при удалении вопроса');
        }
    })
        .catch(error => console.error('Ошибка:', error));
}
function updateTable(questions) {
    const tableBody = document.querySelector('table tbody');
    tableBody.innerHTML = '';

    if (!questions.length) {
        tableBody.innerHTML = '<tr><td colspan="3">Вопросы не найдены.</td></tr>';
    } else {
        questions.forEach(question => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${question.nameThemes}</td>
                <td>${question.titleQuestions}</td>

                <td>${question.links}</td>
            `;
            tableBody.appendChild(row);
        });
    }

    document.getElementById('page-number').textContent = currentPage;
}


function populateQuestions(questions) {
    allQuestions = [];
    allQuestions = questions;
    displayQuestions();
}

document.addEventListener('DOMContentLoaded', function() {
    fetchThemes();
});

function fetchThemes() {
    const selectElements = document.querySelectorAll('.themeSelect');
    selectElements.forEach(selectElement => {
        fetch(`http://localhost:8081/api/v1/themes/getALL`)
            .then(response => response.json())
            .then(themes => {
            themes.forEach(theme => {
                const option = document.createElement('option');
                option.value = theme.id;
                option.textContent = theme.title;
                selectElement.appendChild(option);
            });
        })
            .catch(error => console.error('Error:', error));
    });
}

function fetchQuestionsByTheme() {
    const themeIdString = document.querySelectorAll('.themeSelect');
    themeSelectElements.forEach(themeSelect => {
        const themeIdString = themeSelect.value;
        let url = 'http://localhost:8081/api/v1/questions/';

        if (themeIdString === 'all') {
            url += 'getAll';
        } else {
            url += `getQuestionsThemes?themeId=${themeIdString}`;
        }

        fetch(url)
            .then(response => response.json())
            .then(questions => populateQuestions(questions))
            .catch(error => console.error('Error:', error));
    });
}


function populateQuestions(questions) {
    const tableBody = document.querySelector('table tbody');
    tableBody.innerHTML = '';
    questions.forEach(question => {
        const row = `<tr>
            <td>${question.nameThemes}</td>
            <td>${question.titleQuestions}</td>
             <td>${question.links}</td>
        </tr>`;
        tableBody.innerHTML += row;
    });
}