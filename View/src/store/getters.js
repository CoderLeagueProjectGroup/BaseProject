import { CURRENT_LOGIN_TOKEN, CURRENT_LOGIN_USERNAME } from '../config/constant';
import { getStore } from '../config/mUtils';
import { getUsername } from '../service/getData';

export default {
    //获取首页
    homePage: state => {
        // console.log(!getStore(CURRENT_LOGIN_TOKEN));
        if (!getStore(CURRENT_LOGIN_TOKEN)) {
            state.homePage = 'Login';
        } else {
            state.homePage = 'Home';
        }
        return state.homePage;
    },
    //获取登陆用户名称
    username: (state) => {
        if (!state.username) {
            let username = getStore(CURRENT_LOGIN_USERNAME);
            state.username = username;
        }
        return state.username;
    }
}