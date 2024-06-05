module.exports = {
    devServer: {
        https: true,
        proxy: {
            '/api': {
                target: 'https://super-coon-666.ru:1303',
                changeOrigin: true,
                secure: true,
                pathRewrite: { '^/api': '' }
            }
        },
        port: 8080
    }
};
