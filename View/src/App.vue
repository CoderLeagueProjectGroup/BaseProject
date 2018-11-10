<template>
  <div id="app">
    <transition name="router-fade" mode="out-in">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
import { mapState, mapMutations,mapGetters } from "vuex";
import Login from '@/page/Login';
import Home from '@/page/Home';

export default {
  name: "App",
  computed:{
    ...mapGetters(['homePage'])
  },
  components:{
    'Login':Login,
    'Home':Home
  }
};
</script>

<style>
html,
body,
#app {
  margin: 0;
  height: 100%;
}
.router-fade-enter-active,
.router-fade-leave-active {
  transition: opacity 0.3s;
}
.router-fade-enter,
.router-fade-leave-active {
  opacity: 0;
}
</style>
