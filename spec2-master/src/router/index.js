import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomePageView.vue";
import PostCreationView from '../views/PostCreationView.vue'

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/about",
    name: "about",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/AboutPageView.vue"),
  },
  {
    path: "/login",
    name: "login",
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/LoginPageView"),
  },
  {
    path: "/personal",
    name: "personal",
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/UserPageView"),
  },
  {
    path: "/content",
    name: "content",
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/ContentPageView"),
  },
  {
    path: '/create-post',
    name: 'CreatePost',
    component: PostCreationView,
    props: true
  },
];

const router = new VueRouter({
  routes,
});

export default router;
