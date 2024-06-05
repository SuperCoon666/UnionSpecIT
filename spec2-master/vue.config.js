module.exports = {
    devServer: {
        https: true, // Используйте HTTPS
        proxy: {
            '/api': {
                target: 'https://localhost:1303', // URL Spring Boot сервера
                changeOrigin: true, // Необходимо для корректной работы с виртуальными хостами
                secure: false, // позволяет использовать самоподписанные сертификаты
                pathRewrite: { '^/api': '' }, // Переписывает путь, чтобы удалить /api
            },
        },
        port: 8080, // Порт, на котором работает фронтенд
    },
};
