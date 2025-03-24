document.getElementById("login_form").addEventListener('submit', async function(e) {
    e.preventDefault();
    const login = document.getElementById('login').value;
    const password = document.getElementById('password').value;

    try {
        const loginData = {
            username: login,
            password: password
        };
        const response = await fetch('/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData)
        });

        if (response.ok) {
            window.location.href = '/home';
        } else {
            const errorData = await response.json();
            console.error('Ошибка входа:', errorData);
            alert('Ошибка входа. Проверьте логин и пароль.');
        }
    } catch (error) {
        console.error('Ошибка сети:', error);
        alert('Произошла ошибка при попытке входа');
    }
});