<template>
    <header>
        <div :class="className === 'side' ? 'flex-between' : ''">
            <div class="logo" :style="logoStyle">
                <a href="/">
                    <img src="../../resources/img/logo.png" alt="BI MANAGER LOGO">
                </a>
            </div>
            <div class="user-info flex justify-end align-center">
                <div class="sms">
                    <svg-icon type="mdi" :path="mdiEmail" color="#213f99"></svg-icon>
                </div>
                <div class="user flex align-center">
                    <div class="user-img">
                        <svg-icon type="mdi" :path="mdiAccountCircle" color="#213f99"></svg-icon>
                    </div>
                    <span class="user-name" v-show="cookieExist">{{ userName }}님</span>
                </div>
                <button class="logout-btn" @click="logout()" v-show="cookieExist">로그아웃</button>
                <button class="logout-btn" @click="login()" v-show="!cookieExist">로그인</button>
            </div>
        </div>
    </header>
</template>

<script>
import SvgIcon from '@jamescoyle/vue-icon'
import { mdiEmail, mdiAccountCircle } from '@mdi/js'
import axios from 'axios'

export default {
    props: {
        className: String
    },
    data() {
        return {
            mdiEmail: mdiEmail,
            mdiAccountCircle: mdiAccountCircle,
            cookieExist: null, // 쿠키 존재 여부
            userName: $cookies.get("USER"), // 사용자 이름
        }
    },
    methods: {
        // 로그아웃
        logout: function () {
            const vm = this;
            axios({
                url: '/logout.json',
                method: 'post',
                headers: { "Content-Type": "application/json; charset=UTF-8" },
                data: {},
            })
                .then(function (response) {
                    $cookies.remove("USER");
                    $cookies.remove("JSESSIONID");
                    $cookies.remove("USER_ID");
                    vm.cookieExist = false;
                    window.location.reload("/");
                })
                .catch(function (error) {
                    vm.$showAlert('error', '로그아웃에 오류가 발생하였습니다.');
                });
        },

        //로그인 버튼 클릭시 login.page로 이동
        login: function () {
            this.$router.push('/');
        },
    },
    watch: {},
    computed: {
        logoStyle() {
            if (this.className === 'top') {
                return {
                    position: 'absolute',
                    top:'50%',
                    left:'50%',
                    transform: 'translate(-50%,-50%)'
                }
            } else {
                return {}
            }
        },
    },
    components: {
        'SvgIcon': SvgIcon
    },
    mounted() {
        //쿠키 존재 유무 확인
         if ($cookies.get("JSESSIONID") != null) {
             this.cookieExist = true;
         }
         else {
             // 없으면 로그인페이지로 이동
             this.cookieExist = false;
             this.$router.push('/');
         }
    }
}
</script>