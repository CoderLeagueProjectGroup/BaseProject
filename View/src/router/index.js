import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/page/Login'
import Home from '@/page/Home';

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login,
      meta: {
        title: "登陆",
        public: true
      }
    },
    {
      path: '/',
      name: 'Home',
      component: Home,
      meta: {
        title: '管理系統'
      }
    }
  ]
})
