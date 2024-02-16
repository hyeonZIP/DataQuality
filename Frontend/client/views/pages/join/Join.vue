<template>
    <div class="join-view container">
        <div class="flex align-center flex-column">
            <div class="view-wrap">
                <h1 class="main-title page-titleZone">회원가입</h1>
                <ul class="content-wrap">
                    <li class="flex-column">
                        <a>ID</a>
                        <input class="flex70" type="text" v-model="id" />
                    </li>
                    <li class="flex-column">
                        <a>PW</a>
                        <input type="password" v-model="pwd" />
                    </li>
                    <li class="flex-column">
                        <a>PW CHECK</a>
                        <input type="password" v-model="pwd_check" />
                    </li>
                    <li class="flex-column">
                        <a>NAME</a>
                        <input type="text" v-model="name" />
                    </li>
                    <li class="flex-column">
                        <a>E-MAIL</a>
                        <input type="text" v-model="email" />
                    </li>
                </ul>
                <button class="large-btn blue-btn join-button" @click="joinUser">회원가입</button>
            </div>
        </div>
        <div class="join-modal modal-wrapper" v-if="joinModal">
            <div class="modal-fillter flex-column align-center justify-between">
                <h1>회원가입이 완료되었습니다.로그인 페이지로 넘어갑니다.</h1>
                <button class="blue-btn small-btn" @click="closeModal">확인</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import vueCookie from 'vue-cookies';
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiAccountOutline, mdiKey, mdiCardAccountDetails } from '@mdi/js';
export default {
    data() {
        return {
            mdiAccountOutline: mdiAccountOutline,
            mdiKey: mdiKey,
            mdiCardAccountDetails: mdiCardAccountDetails,
            // joinbtn: true,
            joinModal: false,
            closeModal: false,

            id: '',
            pwd: '',
            pwd_check: '',
            name: '',
            email: '',
        };
    },
    methods: {
        joinUser() {
            const vm = this;
            if (!vm.id || !vm.pwd || !vm.pwd_check || !vm.name || !vm.email) {
                this.$showAlert('error', '모든 칸을 입력하세요.');
                return;
            }

            //아이디 검사
            /*if (vm.onlyNumberAndEnglish(vm.id) == false) {
                this.$showAlert('error', '아이디를 입력하실 때 영어 또는 숫자만 가능합니다.');
                return;
            }*/

            //비밀번호 검사
            /*if (vm.strongPassword(vm.pwd) == false) {
                this.$showAlert('error','비밀번호 입력시 최소 8글자 이상이면서, 알파벳과 숫자 및 특수문자(@$!%*#?&)를 하나 이상 입력해주시기 바랍니다.');
                return;
            }*/

            //비밀번호 일치 확인
            if (vm.pwd === vm.pwd_check) {
                const user_info = {
                    user_id: vm.id,
                    user_pw: vm.pwd,
                    user_nm: vm.name,
                    user_email: vm.email,
                };
                axios({
                    url: '/insertUsers.json',
                    method: 'post',
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8',
                    },
                    data: user_info,
                })
                    .then((res) => {
                        console.log('회원가입 응답 : ', res);
                        vm.joinbtn();
                    })
                    .catch((err) => {
                        console.log('회원가입 에러 : ', err);
                        this.$showAlert('error', '회원가입 실패');
                    });
            } else {
                this.$showAlert('error', '비밀번호가 일치하지 않습니다.');
            }
            vm.id = '';
            vm.pwd = '';
            vm.pwd_check = '';
            vm.name = '';
            vm.email = '';
        },
        joinbtn() {
            this.joinModal = !this.joinModal;
        },
        closeModal() {
            this.joinModal = !this.joinModal;
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
    watch: {},
    computed: {},
    components: {
        SvgIcon,
    },
    mounted() {
        //쿠키 존재 유무 확인
        if ($cookies.get('JSESSIONID') != null) {
            this.$router.push('/main.page');
        } else {
            this.$router.push('/join.page');
        }
    },
};
</script>

<style scoped>
.join-view {
    width: 100%;
    padding: 6rem 0;
    position: relative;
}

.view-wrap {
    background-color: var(--color-white);
    width: 30%;
    border-radius: 2rem;
    padding: 50px 60px;
    box-shadow: 5px 5px 10px #33333336;
}

.content-wrap .container {
    flex-direction: row;
}

.content-wrap > li {
    padding: 2rem 0;
}

.content-wrap > li > a {
    padding: 0 0 1rem 0;
    font-size: 1.5rem;
}

input[type='password'] {
    min-width: 100px;
    padding: 5px 10px;
    border: 1px solid #eee;
    border-radius: 5px;
    font-size: 1.3rem;
}
input[type='text'] {
    margin-left: 0px;
}

.join-button {
    padding: 1rem 0;
    margin-left: 0px;
}
.join-button.active {
    display: block;
}
.join-modal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 100%;
    height: 100%;
    padding: 0px !important;
    background-color: rgba(0, 0, 0, 0.256);
}

.modal-fillter {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 30%;
    height: 30%;
    background-color: var(--color-white);
    box-shadow: 5px 5px 10px #33333336;
    text-align: center;
    padding: 4rem;
    border-radius: 2rem;
}
</style>
