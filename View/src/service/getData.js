import Axios from 'axios'
import { servBaseUrl } from '../config/env';
import { getStore, setStore } from '../config/mUtils';
import '../plugins/md5.min.js';
import { Message } from 'element-ui';
import { CURRENT_LOGIN_USERNAME, CURRENT_LOGIN_TOKEN, CURRENT_LOGIN_USERID } from '../config/constant';
import { router } from '../main';

const getBaseAxios = () => {
    var instance = Axios.create({
        baseURL: servBaseUrl
    });
    instance.defaults.headers.common['Authorization'] = getAuthorizationHeader();
    // 添加响应拦截器
    instance.interceptors.response.use(function (response) {
        return response;
    }, function (error) {
        // 对响应错误做点什么
        console.log("interceptor:" + error);
        console.log(error.response.status);
        if (error.response.status == 401) {
            // top.location.href = "login.html";
            router.push({name:'Login'});
            Message.error('登陆过期或没有权限！');
        } else {
            Message.error(error.response.data.msg || "网络错误！");
            // layer.msg(error.response.data.msg || "网络错误！");
        }
        return Promise.reject(error);
    });
    return instance;
}

const getAuthorizationHeader = () => {
    var userId = getStore(CURRENT_LOGIN_USERID) || '';
    var timestamp = new Date().getTime();
    var token = md5.hex((getStore(CURRENT_LOGIN_TOKEN) || '') + timestamp);
    return "userId=" + userId + "&token=" + token + "&timestamp=" + timestamp;
}

/**
 * 获取菜单
 */
export const getMenu = () => {
    var axiosInstance = getBaseAxios();

}

/**
 * 获取用户名
 */
export const getUsername = async() => {
    return new Promise((resolve, reject) => {
        let axiosInstance = getBaseAxios();
        axiosInstance.get('/user/user/username')
            .then(function (response) {
                setStore(CURRENT_LOGIN_USERNAME, response.data.data);
                resolve(response.data.data);
            }).catch(error => {
                reject(error);
            });
    });
}