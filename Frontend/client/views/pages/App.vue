<template>
   <div v-cloak :class="layoutType === 'side' ? 'dashboard-wrap' : 'layout-wrap'">
      <Header v-show="isLogin" :className="layoutType" @updateIsLogin="isLogin = $event" />
      <SideMenu v-show="isLogin" v-if="layoutType === 'side'" />
      <TopMenu v-show="isLogin" v-else />
      <div class="main">
         <PageNavigation v-if="layoutType === 'side'" />
         <router-view @updateIsLogin="isLogin = $event" />
      </div>
   </div>
   <AlertModal id="cmmnAlert" ref="Alert"></AlertModal>
</template>

<script>
import Header from '../layout/Header.vue';
import SideMenu from '../layout/SideMenu.vue';
import TopMenu from '../layout/TopMenu.vue';
import PageNavigation from '../component/PageNavigation.vue';
import AlertModal from '../component/AlertModal.vue';

const App = {
   data: () => {
      return {
         // side혹은 top 둘중 한개 타입
         layoutType: "top",
         isLogin: false,
      }
   },
   methods: {
      updateIsLogin: function () {
         if ($cookies.get("JSESSIONID") != null) {
             this.isLogin = true;
         }
         else {
             this.isLogin = false;
         }
      },
   },
   watch: {
   },
   computed: {
   },
   components: {
      'Header': Header,
      'SideMenu': SideMenu,
      'TopMenu': TopMenu,
      'PageNavigation': PageNavigation,
      'AlertModal' : AlertModal,
   },
   mounted: function() {
      console.log('Vue mounted');
      this.updateIsLogin();
      this.$setAlertRef(this.$refs.Alert);
   }
}

export default App;
</script>

<style scoped>
[v-cloak] {
   display: none;
}

.main-warp {
   border: 1px solid #000;
   margin: 20px 0px;
}
</style>
