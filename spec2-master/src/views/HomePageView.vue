<template>
    <div>
        <div class="but-header">
            <el-button class="place-but but-rec">Recommendations</el-button>
            <el-select v-model="FilterValue" placeholder="Filter" class="place-but but-filter">
                <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
            </el-select>
        </div>
        <div class="content-holder">
            <article v-for="post in filteredPosts" :key="post.id" class="Headline" @click="openPost(post)">
                {{ post.headline }}
            </article>
        </div>
        <el-pagination
            @current-change="handlePageChange"
            :current-page="currentPage"
            :page-size="pageSize"
            layout="prev, pager, next"
            :total="totalItems"
            :page-count="totalPages"
            class="pagination"
        ></el-pagination>
    </div>
</template>

<script>
import axios from '../axios-config';

export default {
    name: "HomeView",
    data() {
        return {
            posts: [],
            options: [
                { value: 'headline', label: 'Headline' },
                { value: 'id', label: 'ID' },
                { value: 'idUserCreated', label: 'User ID' }
            ],
            FilterValue: '',
            currentPage: 1,
            pageSize: 7,
            totalItems: 0,
            totalPages: 0,
        };
    },
    computed: {
        filteredPosts() {
            if (!this.FilterValue) {
                return this.posts;
            }

            switch (this.FilterValue) {
                case 'headline':
                    return this.posts.sort((a, b) => a.headline.localeCompare(b.headline));
                case 'id':
                    return this.posts.sort((a, b) => a.id - b.id);
                case 'idUserCreated':
                    return this.posts.sort((a, b) => a.idUserCreated - b.idUserCreated);
                default:
                    return this.posts;
            }
        }
    },
    methods: {
        getPostsRecommendations() {
            axios.get('/get-posts-recommendations', {
                params: {
                    page: this.currentPage,
                    size: this.pageSize,
                    filter: this.FilterValue
                }
            })
            .then(response => {
                this.posts = response.data.posts;
                this.currentPage = response.data.currentPage;
                this.totalItems = response.data.totalItems;
                this.totalPages = response.data.totalPages;
                console.log("TOTAL_P: ", response.data.totalPages)
                console.log("TOTAL_I: ", response.data.totalItems)
            })
            .catch(error => {
                console.error("There was an error!", error);
            });
        },
        handlePageChange(newPage) {
            this.currentPage = newPage;
            this.getPostsRecommendations();
        },
        openPost(post) {
            this.$router.push({
                name: 'content',
                params: {
                    postContent: post.content,
                    postHeadline: post.headline,
                    idUserCreated: post.idUserCreated,
                    idPost: post.id
                }
            });
        }
    },
    mounted() {
        this.getPostsRecommendations();
    },
    watch: {
        currentPage(newPage, oldPage) {
            if (newPage !== oldPage) {
                this.getPostsRecommendations();
            }
        },
        FilterValue(newValue, oldValue) {
            if (newValue !== oldValue) {
                this.getPostsRecommendations();
                console.log("FILTERED")
            }
        }
    }
};
</script>

<style lang="scss">
    .el-pager li.active{
      color: #409EFF !important;
    }
    .el-pager li {
      background-color: #204969 !important;
      color: #42b983 !important;
    }
    .el-pagination .btn-prev {
      background-color: #204969 !important;
      color: #42b983 !important;
    }
    .el-pagination .btn-next{
      background-color: #204969 !important;
      color: #42b983 !important;
    }
    .pagination{
      position: relative;

    }
    .li .number{
      color: #42b983 !important;
    }

    .content-holder {
        position: relative;
        top: 50px;
        padding-bottom: 40px;
    }
    .Headline {
        position: relative;
        max-width: 600px;
        width: 80%;
        margin: 20px auto;
        font-size: 48px;
        font-weight: 600;
        line-height: 52px;
        letter-spacing: -0.015em;
        word-break: break-word;
        color: #42b983;

        padding-top: 32px;
        padding-right: 36px;
        padding-bottom: 32px;
        padding-left: 36px;
        border-radius: 18px;
        outline: 1px solid #252525;
        background-color: #204969;
    }

    .but-header {
        position: relative;
        top: 40px;
        display: flex;
        justify-content: center;
        margin-bottom: 20px;
    }
    .place-but {
        width: 180px;
        margin: 0 5px;

        color: #42b983 !important;
        background-color: #212828 !important;
        border-color: #212828 !important;

        .el-input__inner {
            background-color: #212828 !important;
            border-color: #212828 !important;
        }
    }
    .el-select-dropdown__item {
        color: #42b983 !important;
        background-color: #212828 !important;
        border-color: #212828 !important;
        font-size: medium;
    }
    .el-select-dropdown {
        background-color: #212828 !important;
        border-color: #212828 !important;
    }
    @media (max-width: 1080px) {
        .Headline {
            font-size: 38px;
        }
    }
    @media (max-width: 700px) {
        .Headline {
            font-size: 30px;
            padding-left: 20px;
            padding-right: 20px;
        }
        .el-button{
            margin-bottom: 5px !important;
        }
    }
    @media (max-width: 600px) {
        .Headline {
            font-size: 25px;
        }
    }
    @media (max-width: 460px) {
        .Headline {
            font-size: 20px;
        }
        .but-header {
            flex-wrap: wrap;
        }
        .but-rec {
            display: none;
        }
    }
    @media (max-width: 300px) {
        .Headline {
            font-size: 16px;
        }
    }
    @media (min-width: 2300px) {
        .Headline {
            width: 50%;
            font-size: 55px;
        }
    }
    @media (min-width: 3000px) {
        .Headline {
            width: 40%;
            font-size: 60px;
        }
    }
</style>

