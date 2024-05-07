// src/store.js
import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    darkMode: true,
    userId: null, // Добавляем новое состояние для хранения ID пользователя
    nickname: null,
    lightTheme: {
      backgroundColor: '#eef2f1',
      textColor: '#181d1c',
    },
    darkTheme: {
      backgroundColor: '#181d1c',
      textColor: '#eef2f1',
    },
  },
  mutations: {
    setDarkMode(state, payload) {
      state.darkMode = payload;
    },
    setUserId(state, userId) { // Добавляем мутацию для обновления ID пользователя
      state.userId = userId;
    },
    setUserNickname(state, nickname){
      state.nickname = nickname;
    }
  },
  getters: {
    currentTheme: (state) => {
      return state.darkMode ? state.darkTheme : state.lightTheme;
    },
    // Добавляем getter для получения ID пользователя
    getUserId: (state) => {
      return state.userId;
    },
    getNickname: (state) => {
      return state.nickname;
    },
  },
});
