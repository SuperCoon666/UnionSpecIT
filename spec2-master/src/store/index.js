import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomePageView.vue";

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
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
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
];

const router = new VueRouter({
  routes,
});

export default router;
