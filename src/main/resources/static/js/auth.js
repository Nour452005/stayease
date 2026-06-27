function saveAuth(data) {
    localStorage.setItem('token', data.token);
    localStorage.setItem('role', data.role);
    localStorage.setItem('email', data.email);
}

function clearAuth() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('email');
}

function isLoggedIn() {
    return !!localStorage.getItem('token');
}

function isAdmin() {
    return localStorage.getItem('role') === 'ADMIN';
}

function getCurrentEmail() {
    return localStorage.getItem('email');
}

function requireAuth() {
    if (!isLoggedIn()) {
        window.location.href = '/login.html';
    }
}

function requireAdmin() {
    if (!isLoggedIn() || !isAdmin()) {
        window.location.href = '/index.html';
    }
}

function logout() {
    clearAuth();
    window.location.href = '/index.html';
}

function updateNavbar() {
    const loggedIn = isLoggedIn();
    const admin = isAdmin();

    const navLogin = document.getElementById('nav-login');
    const navRegister = document.getElementById('nav-register');
    const navDashboard = document.getElementById('nav-dashboard');
    const navAdmin = document.getElementById('nav-admin');
    const navLogout = document.getElementById('nav-logout');
    const navEmail = document.getElementById('nav-email');

    if (loggedIn) {
        if (navLogin) navLogin.style.display = 'none';
        if (navRegister) navRegister.style.display = 'none';
        if (navDashboard) navDashboard.style.display = 'block';
        if (navAdmin && admin) navAdmin.style.display = 'block';
        if (navLogout) navLogout.style.display = 'block';
        if (navEmail) navEmail.textContent = getCurrentEmail();
    } else {
        if (navLogin) navLogin.style.display = 'block';
        if (navRegister) navRegister.style.display = 'block';
        if (navDashboard) navDashboard.style.display = 'none';
        if (navAdmin) navAdmin.style.display = 'none';
        if (navLogout) navLogout.style.display = 'none';
    }
}