var constant = {
    host: "http://localhost:8080",
    userId: "userId",
    token: "token"
};

var util={
    //获取axios对象
    getAxios: function () {
        var instance = axios.create({
            baseURL: constant.host
        });
        instance.defaults.headers.common['Authorization'] = this.getAuthorizationHeader();
        // 添加响应拦截器
        instance.interceptors.response.use(function (response) {
            return response;
        }, function (error) {
            // 对响应错误做点什么
            console.log("interceptor:"+error);
            console.log(error.response.status);
            if(error.response.status==401){
                top.location.href="login.html";
            }else{
                layer.msg(error.response.data.msg||"网络错误！");
            }
            return Promise.reject(error);
        });
        return instance;
    },
    //
    getAuthorizationHeader:function () {
        var userId=localStorage.getItem(constant.userId)||'';
        var timestamp=new Date().getTime();
        var token=md5.hex((localStorage.getItem(constant.token)||'')+timestamp);
        return "userId="+userId+"&token="+token+"&timestamp="+timestamp;
    }
};

//字符串左右去空
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}