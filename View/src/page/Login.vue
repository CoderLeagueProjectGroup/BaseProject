<template>
  <div id="particles-js">
    <div class="login">
        <div class="login-top">
            登录
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img">
                <img src="../assets/images/login/name.png">
            </div>
            <div class="login-center-input">
                <input type="text" name="" id="username" value="" placeholder="请输入您的用户名" onfocus="this.placeholder=''"
                       @blur="inputBlur">
                <div class="login-center-input-text">用户名</div>
            </div>
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img">
                <img src="../assets/images/login/password.png">
            </div>
            <div class="login-center-input">
                <input type="password" name="" id="password" value="" placeholder="请输入您的密码"
                       onfocus="this.placeholder=''" @blur="inputBlur">
                <div class="login-center-input-text">密码</div>
            </div>
        </div>
        <div class="login-button" @click="login">
            登陆
        </div>
    </div>
    <div class="sk-rotating-plane"></div>
    <canvas class="particles-js-canvas-el" width="906" height="740" style="width: 100%; height: 100%;"></canvas>
  </div>
</template>

<script>
// import '../assets/js/login/particles.min.js';
// import '../assets/js/login/app.js';
import Axios from "axios";
import { servBaseUrl } from "../config/env";
import { setStore } from "../config/mUtils";
import { CURRENT_LOGIN_TOKEN, CURRENT_LOGIN_USERID } from "../config/constant";

export default {
  name: "Login",
  data() {
    return {
      msg: "Welcome to Your Vue.js App"
    };
  },
  methods: {
    login(e) {
      //登陆验证
      var usernameDom = document.getElementById("username");
      var passwordDom = document.getElementById("password");
      if (usernameDom.value.trim() == "") {
        usernameDom.focus();
        addClass(usernameDom.parentNode, "error");
        return;
      }
      if (passwordDom.value.trim() == "") {
        passwordDom.focus();
        addClass(passwordDom.parentNode, "error");
        return;
      }

      let that = this;
      //登陆请求
      var axios = Axios.create({
        baseURL: servBaseUrl
      });
      axios
        .post("/user/user/login", {
          username: usernameDom.value,
          password: passwordDom.value
        })
        .then(function(response) {
          // console.log(response);
          if (response.data.code == 401) {
            that.$message.error(response.data.msg);
          } else {
            //登陆成功
            setStore(CURRENT_LOGIN_USERID, response.data.data.id);
            setStore(CURRENT_LOGIN_TOKEN, response.data.data.token);
            console.log('登陆成功，跳转页面...');
            that.$router.push({name:'Home'});
          }
          // this.disabled = false;
        })
        .catch(function(error) {
          that.$message.error("网络发生错误！");
          console.log(error);
          // this.disabled = false;
        });
    },
    inputBlur(e) {
      removeClass(e.currentTarget.parentNode, "error");
      if (e.currentTarget.id == "username") {
        e.currentTarget.placeholder = "请输入您的用户名";
      } else {
        e.currentTarget.placeholder = "请输入您的密码";
      }
    }
  }
};

function hasClass(elem, cls) {
  cls = cls || "";
  if (cls.replace(/\s/g, "").length == 0) return false; //当cls没有参数时，返回false
  return new RegExp(" " + cls + " ").test(" " + elem.className + " ");
}

function addClass(ele, cls) {
  if (!hasClass(ele, cls)) {
    ele.className = ele.className == "" ? cls : ele.className + " " + cls;
  }
}

function removeClass(ele, cls) {
  if (hasClass(ele, cls)) {
    var newClass = " " + ele.className.replace(/[\t\r\n]/g, "") + " ";
    while (newClass.indexOf(" " + cls + " ") >= 0) {
      newClass = newClass.replace(" " + cls + " ", " ");
    }
    ele.className = newClass.replace(/^\s+|\s+$/g, "");
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "../assets/css/login/style.css";
@import "../assets/css/login/reset.css";
.login-center-input.error input:focus {
  border: 1px solid #ff5722;
}

.login-center-input.error input:focus ~ .login-center-input-text {
  color: #ff5722;
}
</style>
