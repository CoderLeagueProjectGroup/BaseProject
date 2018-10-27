var constant = {
    host: "http://localhost:8080",
    userId: "userId",
    token: "token"
};

var app={
    //获取axios对象
    getAxios: function () {
        return axios.create({
            baseURL: constant.host
        });
    }
};

//字符串左右去空
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}