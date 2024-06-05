import axios from 'axios';

const instance = axios.create({
    baseURL: '/api', // направлять все запросы через прокси-сервер
    headers: {
        'Content-Type': 'application/json',
    },
});

export default instance;
