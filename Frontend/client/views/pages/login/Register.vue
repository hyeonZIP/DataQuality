<template>
    <div class="user_register content">
        <p class="register_title">회원 가입</p>
        <form v-on:submit="registerUser" class="userRegister_form">
            <input type="text" placeholder="아이디" v-model="id" style="margin-left: 5px" />
            <input type="text" placeholder="이름" v-model="nick_name" />
            <input type="password" placeholder="비밀번호" v-model="pwd" />
            <input type="password" placeholder="비밀번호 재입력" v-model="check_pwd" />
            <input type="email" placeholder="이메일" v-model="email" />
            <button class="userRegister_btn" @click="cancel()">취소 하기</button>
            <button class="userRegister_btn">회원 가입</button>
        </form>
    </div>
</template>
<script>
import axios from 'axios';
export default {
    data() {
        return {
            id: '',
            pwd: '',
            check_pwd: '',
            nick_name: '',
            email: '',
        };
    },
    methods: {
        registerUser(event) {
            const vm = this;
            event.preventDefault();
            // 입력값이 비어 있는지 확인
            if (!vm.id || !vm.nick_name || !vm.pwd || !vm.check_pwd || !vm.email) {
                alert('모든 칸을 입력하세요.');
                return;
            }
            // 아이디 검사
            if (vm.onlyNumberAndEnglish(vm.id) == false) {
                vm.$showAlert('error', '아이디를 입력하실때 영어 또는 숫자만 가능합니다.');
                return;
            }
            // 비밀번호 검사
            if (vm.strongPassword(vm.pwd) == false) {
                vm.$showAlert(
                    'error',
                    '비밀번호 입력시 최소 8글자 이상이면서, 알파벳과 숫자 및 특수문자(@$!%*#?&)를 하나 이상 입력해주시기 바랍니다.'
                );
                return;
            }

            //비밀 번호 확인 -> 일치해야 전송
            if (vm.pwd === vm.check_pwd) {
                const user_data = {
                    user_id: vm.id,
                    user_pw: vm.pwd,
                    user_nm: vm.nick_name,
                    user_email: vm.email,
                };
                axios({
                    url: '/insertUsers.json',
                    method: 'post',
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8',
                    },
                    data: user_data,
                })
                    .then((res) => {
                        this.$router.push({ path: '/', query: {} });
                    })
                    .catch((err) => {
                        vm.$showAlert('error', '회원가입 실패');
                    });
            } else {
                vm.$showAlert('error', '비밀번호가 일치하지 않습니다.');
            }
            vm.id = '';
            vm.pwd = '';
            vm.check_pwd = '';
            vm.nick_name = '';
            vm.email = '';
        },
        // 취소 하기
        cancel(event) {
            this.$router.push({ path: '/', query: {} });
        },

        // 특수문자 검증
        onlyNumberAndEnglish(id) {
            return /^[A-Za-z0-9][A-Za-z0-9]*$/.test(id);
        },

        // 비밀번호 검증(8글자 이상, 영문, 숫자, 특수문자 사용)
        strongPassword(password) {
            return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(password);
        },
    },
    mounted() {
        //쿠키 존재 유무 확인
        if ($cookies.get('JSESSIONID') != null) {
            this.$router.push('/main.page');
        } else {
            this.$router.push('/register.page');
        }
    },
};
</script>
<style>
.user_register {
    display: flex;
    flex-direction: column;
    width: 30%;
    margin-left: calc(50% - 15% + 10px);
    margin-top: 10%;
}
.register_title {
    font-size: 20px;
    margin: 30px 0px 25px 50px;
    font-weight: bold;
    width: 100%;
}
.userRegister_form {
    margin-left: 50px;
    margin-right: 50px;
    text-align: center;
    width: calc(100% - 100px);
}
.userRegister_form input {
    width: 100%;
    padding: 10px;
    margin: 10px 5px;
}
.userRegister_form .userRegister_btn,
.userRegister_btn {
    padding: 10px 40px;
    text-align: center;
    color: #ffffff;
    background-color: #213f98;
    border-radius: 15px;
    margin: 30px;
    cursor: pointer;
}
</style>
