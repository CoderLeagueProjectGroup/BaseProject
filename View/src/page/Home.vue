<template>
    <el-container>
        <el-header>
           <el-menu mode='horizontal' background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
                <el-row type='flex'>
                    <el-col :span='2' class="title">
                        管理后台
                    </el-col>
                    <el-col :push='19' :span='3'>
                        <el-submenu index='1'>
                            <template slot='title'><span v-text="username"></span></template>
                            <el-menu-item index='1-1'>修改密码</el-menu-item>
                            <el-menu-item index='1-2'>退出登录</el-menu-item>
                        </el-submenu>
                    </el-col>
                </el-row>
            </el-menu>
        </el-header>
        <el-container>
            <el-row>
                <el-col :span='6' style="height:100%">
                    <el-menu default-active="1-1" style="height:100%" class="el-menu-vertical-demo">
                        <el-submenu index="1">
                            <template slot="title">
                                <i class="el-icon-location"></i>
                                <span>导航一</span>
                            </template>
                            <el-menu-item index="1-1">选项1</el-menu-item>
                            <el-menu-item index="1-2">选项2</el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-col>
            </el-row>
        </el-container>
    </el-container>
</template>

<script>
import { mapGetters } from "vuex";
import { getUsername } from "../service/getData";

export default {
  name: "Home",
  computed: {
    ...mapGetters(["username"])
  },
  created: function() {
      getUsername();
  }
};
</script>

<style scoped>
.el-header {
  padding: 0;
}
.el-container {
  height: 100%;
}
.title {
  color: #fff;
  text-align: center;
  line-height: 52px;
}
.el-container > .el-row {
  width: 100%;
}
.el-header {
  height: auto !important;
}
</style>


