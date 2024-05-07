<template>
  <div>
      <div class="but-header" >
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
          <article v-for="post in posts" class="Headline" @click="openPost(post)">
              {{post.headline}}
          </article>
      </div>
      <el-pagination
              @current-change="handlePageChange"
              :current-page="currentPage"
              :page-size="pageSize"
              layout="prev, pager, next"
              :total="23"
              class="pagination"
      ></el-pagination>
  </div>
</template>

<script>
import axios from 'axios';

export default {
    name: "HomeView",
    components: {
    // HelloWorld,
    },
    data(){
        return{
              posts: [
                  // {'template':"What is a relational database?", 'url':'/content'},
                  // {'template':"Introduction to Relational Databases", 'url':'/content'},
                  // {'template':"Relational Database Design Basics", 'url':'/content'},
                  // {'template':"Understanding SQL and Relational Databases", 'url':'/content'},
                  // {'template':"The Advantages of Relational Databases", 'url':'/content'},
                  // {'template':"Common Challenges in Managing Relational Databases", 'url':'/content'},
                  // {'template':"Optimizing Performance in Relational Databases", 'url':'/content'},
                  // {'template':"Migrating to a Relational Database: Best Practices", 'url':'/content'},
                  // {'template':"Security Considerations for Relational Databases", 'url':'/content'},
                  // {'template':"Relational Databases vs. Non-Relational Databases: Pros and Cons", 'url':'/content'},
                  // {'template':"Graph Databases: An Alternative to Relational Databases", 'url':'/content'},
                  // {'template':"Real-World Examples of Relational Databases", 'url':'/content'},
                  // {'template':"Scaling Relational Databases for Large Enterprises", 'url':'/content'},
                  // {"template": "Relational Databases in the Cloud: Best Practices", "url": "/content"},
                  // {"template": "Relational Databases for Web Applications", "url": "/content"},
                  // {"template": "Choosing the Right Relational Database Management System", "url": "/content"},
                  // {"template": "Data Modeling for Relational Databases", "url": "/content"},
                  // {"template": "Data Integrity in Relational Databases", "url": "/content"},
                  // {"template": "Relational Databases and Business Intelligence", "url": "/content"},
                  // {"template": "Relational Databases in the Age of Big Data", "url": "/content"},
                  // {"template": "The Future of Relational Databases", "url": "/content"},
                  // {"template": "Common Myths About Relational Databases", "url": "/content"},
              ],
              options: [{
                          value: 'Topics',
                          label: 'Topics'
                          }, {
                          value: 'Date',
                          label: 'Date'
                          }, {
                          value: 'Rating',
                          label: 'Rating'
                          }, {
                          value: 'Author',
                          label: 'Author'
                          }, {
                          value: 'Type',
                          label: 'Type'
                      }],
              FilterValue: '',
              currentPage: 1,
              pageSize: 7,
            }
    },
    methods:{
        getPostsRecommendations() {
            // console.log(this.$store.getters.getUserId);
            // console.log(this.$store.getters.getNickname);
            axios.get('http://localhost:8081/get-posts-recommendations', {
                params: {
                    page: this.currentPage,
                    size: this.pageSize
                }
            })
            .then(response => {
                this.posts = response.data;
            })
            .catch(error => {
                console.error("There was an error!", error);
            });
        },
        handlePageChange(newPage) {
            this.currentPage = newPage;
            this.getPostsRecommendations();
            console.log(this.currentPage)
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
    computed: {
      currentTheme() {
        return this.$store.getters.currentTheme;
      },
      myElementStyle() {
        return {
          backgroundColor: this.currentTheme.backgroundColor,
          color: this.currentTheme.textColor,
        };
      },
    },
    mounted() {
        this.getPostsRecommendations();
    },
    watch: {
        currentPage(newPage, oldPage) {
            if (newPage !== oldPage) {
                this.getPostsRecommendations();
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

