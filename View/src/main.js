// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import { getStore } from './config/mUtils';
import { CURRENT_LOGIN_TOKEN, CURRENT_LOGIN_USERID } from './config/constant';

Vue.config.productionTip = false

Vue.use(ElementUI);

router.beforeEach(function (to, from, next) {
  if (to.meta.public == undefined || to.meta.public == false) {
    if (!getStore(CURRENT_LOGIN_TOKEN)) {
      next('/login');
      return;
    }
  }
  /* 路由发生变化修改页面title */
  console.log(to);
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})

export {
  router
}