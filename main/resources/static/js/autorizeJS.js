function showRegisterForm() {
    document.getElementById('loginSection').style.display = 'none';
    document.getElementById('registerSection').style.display = 'block';
}

function showLoginForm() {
    document.getElementById('registerSection').style.display = 'none';
    document.getElementById('loginSection').style.display = 'block';
}

function setToken(token) {
    localStorage.setItem('token', token);
}

function getToken() {
    return localStorage.getItem('token');
}


function addTokenToHeaders(headers) {
    const token = getToken();
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }
}

// Функция для отправки данных в формате JSON
function sendJsonData(event, form) {
    event.preventDefault();
    const formData = new FormData(form);
    const data = {
        email: formData.get('email'),
        password: formData.get('password')
    };

    const headers = new Headers({
        'Content-Type': 'application/json',
    });

    addTokenToHeaders(headers);

    const url = 'http://localhost:8081/api/v1/auth/authenticate';
    fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(data)
    })
        .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    })
        .then(data => {
        onSuccessfulLogin(data);
    })
        .catch((error) => {
        console.error('Error:', error);
    });
}

function onSuccessfulLogin(data) {
    setToken(data.token);
    window.location.href = '/api/v1/tasks';
}

function sendJsonDataRegist(event, form) {
    event.preventDefault();
    const formData = new FormData(form);
    const data = {
        email: formData.get('email'),
        username: formData.get('username'),
        password: formData.get('password'),
        confirmPassword: formData.get('confirmPassword')
    };

    const headers = new Headers({
        'Content-Type': 'application/json',
    });

    addTokenToHeaders(headers);

    const url = 'http://localhost:8081/api/v1/auth/register';

    fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(data)
    })
        .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    })
        .then(data => {
        onSuccessfulLogin(data);
    })
        .catch(error => {
        console.error('Error:', error);
    });
}