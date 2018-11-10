var app={
    //获取登陆用户名
    getUsername:function (fn) {
        var instance = util.getAxios();
        instance.get("/user/user/username")
            .then(function (response) {
                fn&&fn(response.data.data);
            })
            .catch(function (error) {
                // console.log(error);
                // layer&&layer.msg("网络错误！");
            });
    },
    //获取菜单
    getMenu:function (fn) {
        var axs=util.getAxios();
        axs.get("/user/role/menu")
            .then(function (response) {
                fn&&fn(response.data.data);
            });
    }
};