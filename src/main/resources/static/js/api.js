const API_BASE = 'http://localhost:8080/api';

function getToken() {
    return localStorage.getItem('token');
}

function getHeaders(requiresAuth = true) {
    const headers = { 'Content-Type': 'application/json' };
    if (requiresAuth) {
        headers['Authorization'] = `Bearer ${getToken()}`;
    }
    return headers;
}

// AUTH
async function register(name, email, password) {
    const res = await fetch(`${API_BASE}/auth/register`, {
        method: 'POST',
        headers: getHeaders(false),
        body: JSON.stringify({ name, email, password })
    });
    return res.json();
}

async function login(email, password) {
    const res = await fetch(`${API_BASE}/auth/login`, {
        method: 'POST',
        headers: getHeaders(false),
        body: JSON.stringify({ email, password })
    });
    return res.json();
}

// ROOMS
async function getAllRooms() {
    const res = await fetch(`${API_BASE}/rooms`, {
        headers: getHeaders(false)
    });
    return res.json();
}

async function getRoomById(id) {
    const res = await fetch(`${API_BASE}/rooms/${id}`, {
        headers: getHeaders(false)
    });
    return res.json();
}

async function createRoom(roomData) {
    const res = await fetch(`${API_BASE}/rooms`, {
        method: 'POST',
        headers: getHeaders(true),
        body: JSON.stringify(roomData)
    });
    return res.json();
}

async function updateRoom(id, roomData) {
    const res = await fetch(`${API_BASE}/rooms/${id}`, {
        method: 'PUT',
        headers: getHeaders(true),
        body: JSON.stringify(roomData)
    });
    return res.json();
}

async function deleteRoom(id) {
    await fetch(`${API_BASE}/rooms/${id}`, {
        method: 'DELETE',
        headers: getHeaders(true)
    });
}

// RESERVATIONS
async function createReservation(roomId, checkIn, checkOut) {
    const res = await fetch(`${API_BASE}/reservations`, {
        method: 'POST',
        headers: getHeaders(true),
        body: JSON.stringify({ roomId, checkIn, checkOut })
    });
    return res.json();
}

async function getMyReservations() {
    const res = await fetch(`${API_BASE}/reservations/my`, {
        headers: getHeaders(true)
    });
    return res.json();
}

async function cancelReservation(id) {
    const res = await fetch(`${API_BASE}/reservations/${id}/cancel`, {
        method: 'PUT',
        headers: getHeaders(true)
    });
    return res.json();
}

async function getAllReservations() {
    const res = await fetch(`${API_BASE}/reservations`, {
        headers: getHeaders(true)
    });
    return res.json();
}

async function updateReservationStatus(id, status) {
    const res = await fetch(`${API_BASE}/reservations/${id}/status?status=${status}`, {
        method: 'PUT',
        headers: getHeaders(true)
    });
    return res.json();
}

// REVIEWS
async function getReviewsByRoom(roomId) {
    const res = await fetch(`${API_BASE}/rooms/${roomId}/reviews`, {
        headers: getHeaders(false)
    });
    return res.json();
}

async function createReview(roomId, rating, comment) {
    const res = await fetch(`${API_BASE}/rooms/${roomId}/reviews?rating=${rating}&comment=${encodeURIComponent(comment)}`, {
        method: 'POST',
        headers: getHeaders(true)
    });
    return res.json();
}