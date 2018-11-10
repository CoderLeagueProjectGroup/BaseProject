import Vue from 'vue';
import Vuex from 'vuex';
import mutations from './mutations';
import actions from './actions';
import getters from './getters';

Vue.use(Vuex);

const state = {
    menu:null,//菜单
    username:'',//用户名
    homePage:'Login'//首页
}

export default new Vuex.Store({
    state,
    getters,
    actions,
    mutations
});