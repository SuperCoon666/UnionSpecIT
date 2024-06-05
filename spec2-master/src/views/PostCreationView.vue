<template>
    <div class="content-page-header">
        <input class="Headline" type="text" v-model="headline" placeholder="Enter title" />
        <textarea class="MainContent" v-model="content" @input="autoExpand" placeholder="Enter post content"></textarea>
        <div class="container-buttons">
            <button class="review-but" @click="submitPost">Publish</button>
            <button v-if="idUserCreated !== null" class="review-but" @click="deletePost">Delete</button>
        </div>
    </div>
</template>

<script>
import axios from '../axios-config';

export default {
    name: "PostCreationView",
    data() {
        return {
            headline: this.$route.params.headline || "",
            content: this.$route.params.content || "",
            idUserCreated: this.$route.params.idUserCreated || null,
            idPost: this.$route.params.idPost || null,
        };
    },
    computed: {
        // Ваши computed свойства
    },
    methods: {
        submitPost() {
            // Проверяем, что мы редактируем существующий пост
            if (this.idUserCreated !== null && this.idPost !== null) {
                axios.put(`/edit-post/${this.idPost}`, {
                    id: this.idPost,
                    headline: this.headline,
                    content: this.content,
                    idUserCreated: this.idUserCreated,
                    url: "/content"
                })
                .then(response => {
                    console.log('Post updated:', response.data);
                    this.$router.push({
                        name: 'home'
                    });
                })
                .catch(error => {
                    console.error('Error updating post:', error);
                    // Обработка ошибок
                });
            } else if (this.idPost === null && this.$store.getters.getUserId !== null) {
                // Создаем новый пост
                axios.post('/create-post', {
                    headline: this.headline,
                    content: this.content,
                    idUserCreated: this.$store.getters.getUserId, // Обращаемся к геттеру как к свойству
                    url: "/content"
                })
                .then(response => {
                    console.log('New post created:', response.data);
                    this.$router.push({
                        name: 'home'
                    });
                })
                .catch(error => {
                    console.error('Error creating post:', error);
                    // Обработка ошибок
                });
            } else {
                console.log("No valid ID for user - cannot create/update post");
            }
        },
        deletePost() {
            if (this.idPost !== null) {
                axios.delete(`/delete-post/${this.idPost}`)
                .then(response => {
                    console.log('Post deleted:', response.data);
                    this.$router.push({
                        name: 'home'
                    });
                })
                .catch(error => {
                    console.error('Error deleting post:', error);
                    // Обработка ошибок
                });
            } else {
                console.log("No valid ID for post - cannot delete post");
            }
        },
        autoExpand(event) {
            const textarea = event.target;
            textarea.style.height = 'auto';
            textarea.style.height = textarea.scrollHeight + 'px';
        },
    },
};
</script>


<style scoped>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

.container-buttons {
    display: flex;
    justify-content: space-around;
}

.review-but {
    position: relative;
    display: flex;
    margin-top: 20px;
    margin-bottom: 20px;
    color: #42b983 !important;
    background-color: #485656 !important;
    border-color: #485656 !important;

    padding: 13px;
    font-size: 18px;
    border-radius: 13px;
    z-index: 10;
    cursor: pointer;
}

.content-page-header {
    position: relative;
    max-width: 800px;
    margin: 0 auto;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 0 15px;
    box-sizing: border-box;
    padding-top: 40px;
    padding-left: 15px;
    padding-right: 15px;
    padding-bottom: 40px;
}

.Headline {
    font-size: 38px;
    font-weight: 600;
    line-height: 52px;
    letter-spacing: -0.015em;
    word-break: break-word;
    color: #42b983;
    padding: 32px 36px;
    border-radius: 18px;
    outline: 1px solid #252525;
    background-color: #204969;
    margin-bottom: 20px;
    text-align: center;
    width: 100%;
    max-width: 800px;
}

.MainContent {
    position: relative;
    color: #eef2f1;
    border-radius: 18px;
    outline: 1px solid #252525;
    background-color: #181d1c;
    transition: background-color .2s ease-in-out;
    fill: #eef2f1;
    font-family: "Inter", Helvetica, Arial, sans-serif;
    font-size: 18px;
    font-weight: 400;
    line-height: 28px;
    letter-spacing: 0.05em;
    padding: 32px 36px;
    text-align: left;
    width: 100%;
    max-width: 800px;

    overflow-y: hidden; /* Убрать вертикальный скролл */
    resize: none; /* Запретить изменение размера пользователем */
}
@media (max-width: 800px) {
    .content-page-header {
        max-width: 100%;
        padding: 50px 15px;
        box-sizing: border-box;
        padding-top: 30px;
    }
    .Headline {
        font-size: 35px;
        padding: 32px 16px;
        padding-left: 20px;
        padding-right: 20px;
    }
    .MainContent {
        font-size: 35px;
        padding: 32px 16px;
        padding-left: 20px;
        padding-right: 20px;
    }
}

@media (max-width: 450px) {
    .Headline {
        font-size: 30px;
        padding: 32px 8px;
    }
    .MainContent {
        font-size: 16px;
        padding: 32px 8px;
    }
}

@media (max-width: 300px) {
    .Headline {
        font-size: 24px;
        padding: 32px 4px;
    }
    .MainContent {
        font-size: 14px;
        padding: 32px 4px;
    }
}
</style>
