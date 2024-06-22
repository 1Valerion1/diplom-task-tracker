document.addEventListener('DOMContentLoaded', function() {
    function fetchPlan(id) {
        const token = localStorage.getItem('token');

        console.log('Запрос к серверу с ID:', id);
        fetch(`http://localhost:8081/api/v1/plans/getOne?id=${id}`)
            .then(response => response.json())
            .then(data => {
            if (!data || !data.plans) {
                console.error('Не удалось получить данные или данные не содержат ожидаемых полей');
                return;
            }
            const planContainer = document.getElementById('plan-container');
            const planHtml = createPlanHtml(data);
            planContainer.innerHTML = planHtml;
        })
            .catch(error => {
            console.error('Ошибка при получении данных плана:', error);
        });
    }

    function createPlanHtml(plan) {
        const themesHtml = plan.theme.map(theme => `
                <li>
                    <div class="card">
                    <div class="card-header">
                    ${theme.title}

                   </div>
                    <div class="card-content">
                        <p>Описание:</p>
                        ${formatDetails(theme.details)}
                        <p>Ссылки: <a href="${theme.links}" class="card-link" target="_blank">Перейти</a></p>
                        <p>Вопросы:</p>
                        <ul>
                            ${theme.questionsList.map(question => `<li>${question.titleQuestions}</li>`).join('')}
                        </ul>
                    </div>
                    </div>
                </li>
            `).join('');

        return `
                <div class="card">
                    <div class="card-header">План изучения для разработчика: <span>${plan.developer}</span></div>
                    <div class="card-content">
                        <p>Темы:</p>
                        <ul>${themesHtml}</ul>
                    </div>
                </div>
            `;
    }
    window.editTheme = function(id, title, description, links) {
        document.getElementById('updateForm').style.display = 'block';
        document.getElementById('updateId').value = id;
        document.getElementById('updateTitle').value = title;
        document.getElementById('updateAnswer').value = description;
        document.getElementById('updateLinks').value = links;
    };

    window.updateTheme = function() {
        const id = document.getElementById('updateId').value;
        const title = document.getElementById('updateTitle').value;
        const description = document.getElementById('updateAnswer').value;
        const links = document.getElementById('updateLinks').value;

        fetch(`http://localhost:8081/api/v1/themes/update`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: id,
                title: title,
                details: description,
                links: links
            })
        })
            .then(response => response.json())
            .then(data => {
            document.getElementById('updateMessage').innerText = 'Обновление прошло успешно!';
            fetchPlan(2);  // обновить список планов
        })
            .catch(error => {
            console.error('Ошибка при обновлении темы:', error);
            document.getElementById('updateMessage').innerText = 'Ошибка при обновлении.';
        });
    };

    window.deleteTheme = function(id) {
        const confirmDelete = confirm('Вы действительно хотите удалить эту тему?');
        if (!confirmDelete)
        return;

        fetch(`http://localhost:8081/api/v1/themes/delete/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
            fetchPlan(2);  // обновить список планов
        })
            .catch(error => {
            console.error('Ошибка при удалении темы:', error);
        });
    };
});

function formatDetails(details) {
    const paragraphs = details.split(/(?<=\.|\?|\!|\:)\s/);
    const formattedParagraphs = paragraphs.map(paragraph => `<p>${paragraph.trim()}</p>`).join('');

    if (details.length > 10) {
        return `
            <div class="collapsible-content">
                <div class="summary">${formattedParagraphs.slice(0, 200)}</div>
                <div class="full-text hidden">${formattedParagraphs}</div>
                <button onclick="this.previousElementSibling.classList.toggle('hidden');
                        this.textContent = this.previousElementSibling.classList.contains('hidden') ?
                        'Показать больше' : 'Показать меньше';">Показать больше</button>
            </div>
        `;
    }
    return formattedParagraphs;
}

fetchPlan(2);
