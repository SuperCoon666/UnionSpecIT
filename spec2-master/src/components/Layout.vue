<template>
  <div class="personal-area-layout" :style="myElementStyle">
      <div class="personal-area-layout-header">
          <div class="personal-area-layout-header-left">
              <router-link :to="{ path: '/'}" class="logo">
                  <div class="logo small-logo">
                      DataBaseWiki
                  </div>
              </router-link>
              <router-link class="layout-content-but small-my-page" v-if="isUserLoginned()" :to="{ path: '/personal'}">
                  <div>MyPage</div>
              </router-link>
              <el-autocomplete
                  v-model="state"
                  :fetch-suggestions="querySearchAsync"
                  placeholder="Search"
                  @select="handleSelect"
                  class="layout-content-but layout-search small-search"
              ></el-autocomplete>
          </div>
          <div class="personal-area-layout-header-right">
            <el-dropdown class="small-menu">
                <el-button class="layout-content-menu">
                    Menu
                    <i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu slot="dropdown" class="layout-content-menu">
                    <el-dropdown-item class="layout-content-menu">
                        <router-link :to="{ path: '/login'}" class="layout-content-menu layout-content-menu-opacity">
                            Login
                        </router-link>
                    </el-dropdown-item>
                    <el-dropdown-item class="layout-content-menu">
                        <router-link :to="{ path: '/about'}" class="layout-content-menu layout-content-menu-opacity">
                            Info
                        </router-link>
                    </el-dropdown-item>
                    <el-dropdown-item class="layout-content-menu" @click.native="openSettingsDialog">
                      Settings
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
          </div>
          <div>
              <el-dropdown class="mini-menu">
                  <el-button icon="el-icon-setting" circle></el-button>
                  <el-dropdown-menu slot="dropdown" class="layout-content-menu">
                    <el-dropdown-item class="layout-content-menu">
                        <router-link :to="{ path: '/login'}" class="layout-content-menu layout-content-menu-opacity">
                            Login
                        </router-link>
                    </el-dropdown-item>
                    <el-dropdown-item class="layout-content-menu">
                        <router-link :to="{ path: '/about'}" class="layout-content-menu layout-content-menu-opacity">
                            Info
                        </router-link>
                    </el-dropdown-item>
                    <el-dropdown-item style="visibility: hidden;" class="layout-content-menu">
                        <router-link v-if="isUserLoginned()" :to="{ path: '/personal'}" class="mini-my-page">
                            MyPage
                        </router-link>
                    </el-dropdown-item>
                    <el-dropdown-item class="layout-content-menu" @click.native="openSettingsDialog">
                      Settings
                    </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
          </div>
      </div>
  </div>
</template>

<script>
export default {
    name: "HomeView",
    components: {
    },
    data(){
        return{
            links: [],
            state: '',
            timeout:  null
        }
    },
    methods: {
      loadAll() {
        return [
          { "value": "vue", "link": "https://github.com/vuejs/vue" },
          { "value": "element", "link": "https://github.com/ElemeFE/element" },
          { "value": "cooking", "link": "https://github.com/ElemeFE/cooking" },
          { "value": "mint-ui", "link": "https://github.com/ElemeFE/mint-ui" },
          { "value": "vuex", "link": "https://github.com/vuejs/vuex" },
          { "value": "vue-router", "link": "https://github.com/vuejs/vue-router" },
          { "value": "babel", "link": "https://github.com/babel/babel" }
         ];
      },
      openSettingsDialog() {
        this.$emit('open-settings-dialog');
      },
      querySearchAsync(queryString, cb) {
        let links = this.links;
        let results = queryString ? links.filter(this.createFilter(queryString)) : links;

        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          cb(results);
        }, 500);
      },
      createFilter(queryString) {
        return (link) => {
          return (link.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      handleSelect(item) {
        window.open(item.link)
        console.log(item);
      },
      isUserLoginned(){
          return this.$store.getters.getUserId !== null
      }
    },
    mounted() {
      this.links = this.loadAll();
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
};
</script>

<style lang="scss">
//  * {
//      outline: 1px solid red !important;
//    }
    .popper__arrow::after{
        border-bottom-color: #485656 !important;
    }
    .logo {
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: larger;
        font-weight: bold;
        letter-spacing: 2px;
        padding-right: 20px;
        height: 100%;
        color: #42b983;
        text-decoration: none !important;
    }
    .layout-content-but{
        display: flex;
        justify-content: center;
        align-items: center;
        text-decoration: none;
        color: #42b983;
        font-size: x-large;
        margin-left: 10px;
        margin-right: 10px;
    }
    .layout-search{
        height: 10px;
        width: 300px;
        margin-top: 20px !important;
        margin-left: 15px !important;
    }
    .layout-content-menu{
        background-color: #485656 !important;
        border: 0 !important;
        color: #42b983 !important;
        font-size: medium;
        text-decoration: none !important;
        margin-top: 17px !important;
    }
    .layout-content-menu-opacity{
        opacity: .7;
    }
    .personal-area-layout{
        display: flex;
        flex-direction: column;
        width: 100%;
        position: fixed !important;
        top: 0;

        .personal-area-layout-header {
            display: flex;
            background-color: #212828;
            opacity: .9;
            z-index: 3000;
            padding: 0 50px;
            height: 80px;

            .personal-area-layout-header-left {
                display: flex;
                flex-grow: 1;
            }

            .personal-area-layout-header-right {
                display: flex;
            }
        }
    }
    .mini-menu{
        display: flex !important;
        position: fixed !important;
        top: 90%;
        right: 5%;
        visibility: hidden;
    }
    .mini-my-page{
       text-decoration: none !important;
        color: #42b983;
        visibility: hidden !important;
    }

@media (max-width: 800px) {
  .small-screen {
    width: 100% !important;
    position: sticky !important;
  }
  .small-search {
    width: 150px;
    right: 10px !important;
  }
  .personal-area-layout {
      .personal-area-layout-header {
          padding: 0 20px;
      }
  }
}

@media (max-width: 650px) {
  .small-search {
    width: 120px;
    right: 25px !important;
  }
  .small-my-page {
    padding-right: 20px;
  }
  .small-menu {
    visibility: hidden;
  }
  .small-logo {
    padding-right: 0;
  }
  .mini-menu {
    visibility: visible;
  }

  .small-search {
      width: 120px;
      right: 10px !important;
  }
}

@media (max-width: 470px) {
  .small-my-page {
    visibility: hidden;
  }
  .mini-my-page {
    visibility: visible !important;
  }
  .small-search {
    width: 100px;
    right: 140px !important;
  }
}


</style>
