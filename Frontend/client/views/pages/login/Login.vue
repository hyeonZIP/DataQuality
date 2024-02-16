<template>
    <div class="login-page">
        <div class="login-wrap flex-column justify-center align-center">
            <div class="login-content">
                <div class="logo">
                    <img src="../../../resources/img/logo.png" alt="" />
                </div>
                <div class="login-zone">
                    <div class="input-wrap">
                        <div class="inputContainer">
                            <input
                                required=""
                                class="customInput"
                                type="text"
                                v-model="userLogin.user_id"
                                @keyup.enter="handleEnterKey"
                            />
                            <label class="inputLabel">ID</label>
                            <div class="inputUnderline"></div>
                        </div>
                        <div class="inputContainer">
                            <input
                                required=""
                                class="customInput"
                                type="password"
                                v-model="userLogin.user_password"
                                @keyup.enter="handleEnterKey"
                            />
                            <label class="inputLabel">PASSWORD</label>
                            <div class="inputUnderline"></div>
                        </div>
                    </div>
                    <div class="found-zone flex justify-center">
                        <router-link to="">아이디찾기</router-link>
                        <router-link to="">비밀번호 찾기</router-link>
                        <router-link to="/join.page">회원가입</router-link>
                    </div>
                </div>
                <button class="blue-btn large-btn" @click="login()">로그인</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import vueCookie from 'vue-cookies';

export default {
    data() {
        return {
            userLogin: {
                user_id: null,
                user_password: null,
            },
        };
    },
    methods: {
        // 로그인
        login: function () {
            const vm = this;

            // 특수 문자 검사
            const special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;

            // 아이디를 입력하지 않았을 경우
            // 공백만 입력하였을 경우
            // 특수문자가 입력되었을 경우
            if (
                vm.userLogin.user_id == null ||
                vm.userLogin.user_id.replace(/\s/gi, '') == '' ||
                special_pattern.test(vm.userLogin.user_id) == true
            ) {
                vm.$showAlert('알림', '아이디를 입력해주시기 바랍니다.');
                return;
            }

            // 비밀번호를 입력하지 않았을 경우
            // 공백만 입력하였을 경우
            if (vm.userLogin.user_password == null || vm.userLogin.user_password.replace(/\s/gi, '') == '') {
                vm.$showAlert('알림', '비밀번호를 입력해주시기 바랍니다.');
                return;
            }

            axios({
                url: '/login.json',
                method: 'post',
                headers: { 'Content-Type': 'application/json; charset=UTF-8' },
                data: vm.userLogin,
            })
                .then(function (response) {
                    if (response.data > 0) {
                        vm.$router.go();
                        vm.$router.push({ path: '/main.page', query: {} });
                    } else {
                        vm.$showAlert('로그인 실패', '아이디와 비밀번호를 다시 입력해주시기 바랍니다.');
                        return;
                    }
                })
                .catch(function (error) {
                    vm.$showAlert('error', '로그인에 오류가 발생하였습니다.');
                });
        },
        handleEnterKey: function () {
            this.login();
        },
    },
    watch: {},
    computed: {},
    components: {},
    mounted() {
        // 쿠키에 JSESSIONID 키 값이 존재한다면 메인페이지로 이동
        if (vueCookie.get('JSESSIONID') != null) {
            this.$router.push({ path: '/main.page', query: {} });
        }

        window.onpopstate = function () {
            history.go(1);
        };
    },
};
</script>

<style scoped>
.login-page {
    width: 100%;
}

.login-content {
    min-width: 400px;
    max-width: 400px;
    padding: 30px;
    border-radius: 10px;
    background-color: #fff;
    box-shadow: 0 0 20px #ddd;
}

.logo {
    width: 100%;
    margin-bottom: 50px;
}

.logo > img {
    display: block;
    margin: 0 auto;
}
.inputContainer {
    position: relative;
    margin-bottom: 40px;
}

.inputContainer:last-child {
    margin-bottom: 0;
}

.customInput {
    width: 100%;
    padding: 10px;
    font-size: 13px;
    background-color: transparent;
    border: none;
    outline: none;
    transition: border-color 0.3s ease;
}

.inputLabel {
    position: absolute;
    top: 0;
    left: 0;
    pointer-events: none;
    padding: 5px;
    font-size: 13px;
    color: #eee;
    transform: translateY(-80%);
}

.inputUnderline {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 2px;
    background-color: #eee;
}

.found-zone {
    padding: 20px 0;
}

.found-zone > a {
    font-size: 1.4rem;
    padding: 0 10px;
}

.login-page .blue-btn {
    margin-left: 0;
    padding: 10px 0;
}
</style>
