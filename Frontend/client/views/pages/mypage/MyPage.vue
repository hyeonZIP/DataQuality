<template>
  <div class="mypage content">
    <p class="mypage_title">마이 페이지</p>
    <form class="mypage_form" v-on:submit="registerUser">
      <label>
        아이디 : <span> {{ user_id }} </span></label
      >
      <label>
        사용자 이름 :<span>{{ user_name }}</span></label
      >
      <label>
        사용자 이메일 : <span>{{ user_email }}</span>
      </label>
      <label> 현재 비밀 번호 :</label>
      <input type="password" v-model="user_pwd" />
      <label> 새 비밀번호 :</label>
      <input type="password" v-model="update_pwd" />
      <label> 새 비밀번호 확인 :</label>
      <input type="password" v-model="check_updatePwd" />

      <div class="mypage_btn">
        <button class="cancle" type="button" @click="doCancle">취소</button>
        <button class="update" type="button" @click="updateInfo">
          저장 하기
        </button>
      </div>
    </form>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      user_id: $cookies.get("USER_ID"),
      user_name: $cookies.get("USER"),
      user_pwd: "",
      update_pwd: "",
      check_updatePwd: "",
      user_email: "",
    };
  },
  methods: {
    updateInfo() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      /*
            if (vm.strongPassword(vm.update_pwd) == false) {
                vm.$showAlert('error', '비밀번호 입력시 최소 8글자 이상이면서, 알파벳과 숫자 및 특수문자(@$!%*#?&)를 하나 이상 입력해주시기 바랍니다.');
                return;
            }
          */
      if (vm.update_pwd === "") {
        vm.$showAlert("error", "새 비밀번호를 입력해주세요.");
        return;
      }
      if (vm.update_pwd !== vm.check_updatePwd) {
        vm.$showAlert("error", "비밀번호가 일치하지 않습니다.");
      } else {
        axios({
          url: "/updateUserPw.json",
          method: "post",
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
          },
          data: {
            user_pw: vm.user_pwd,
            update_pw: vm.update_pwd,
            user_id: vm.user_id,
          },
        })
          .then((res) => {
            vm.$showAlert(
              res.data === 0 ? "error" : "success",
              res.data === 0
                ? "현재 비밀번호가 올바르지 않습니다."
                : "회원 정보 수정 완료"
            );
          })
          .catch((err) => {
            vm.$showAlert("error", "회원 정보 수정 실패");
          });
      }
      vm.user_pwd = "";
      vm.update_pwd = "";
      vm.check_updatePwd = "";
    },

    // 비밀번호 검증(8글자 이상, 영문, 숫자, 특수문자 사용)
    /*
        strongPassword(password) {
            return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(password);
        },
        */

    // 취소하기 버튼
    doCancle() {
      // '/'로 이동
      this.$router.push("/");
    },

    // 사용자 이메일
    getUserEmail() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      axios({
        url: "/getUserEmail.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: { user_id: vm.user_id },
      })
        .then((res) => {
          vm.user_email = res.data;
        })
        .catch((err) => {});
    },
  },
  mounted() {
    this.getUserEmail();
  },
};
</script>
<style>
.mypage {
  display: flex;
  flex-direction: column;
  width: 30%;
  margin-left: calc(50% - 15% + 10px);
  margin-top: 5%;
  padding: 30px;
  justify-content: center;
}
.mypage_title {
  font-size: 20px;
  font-weight: bold;
  width: 100%;
  margin: 20px 0px;
}
.mypage_form {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.mypage_form > label {
  font-size: 15px;
  margin-top: 30px;
}
.mypage_form > label > span {
  margin-left: 15px;
  display: inline-block;
  padding: 5px 20px;
  border-radius: 15px;
  background-color: #213f9812;
}
.mypage_form > input {
  padding: 15px 10px;
  margin-top: 15px;
  font-size: 15px;
  min-width: -webkit-fill-available;
}
.mypage_form > input:focus {
  background-color: #213f9812;
}
.mypage_btn {
  width: -webkit-fill-available;
  margin: 80px 0px 50px;
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.mypage_btn > button {
  padding: 10px 40px;
  border-radius: 15px;
}
.mypage_btn > button:hover {
  box-shadow: 0 0px 0px rgba(0, 0, 0, 0.25), 0 0 15px rgba(0, 0, 0, 0.22);
}
.mypage_btn > .cancle {
  background-color: #213f9812;
}
.mypage_btn > .update {
  background-color: #213f98;
  color: #ffffff;
}
</style>
