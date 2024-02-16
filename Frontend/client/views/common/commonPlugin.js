/**
 * 
 * 공통 처리 플러그인
 */
import Vue from "vue";

export default{    
    install(Vue){ 

        let alertRef = {};

        Vue.config.globalProperties.$setAlertRef = function(ref){
            alertRef = ref;
        }

        //날짜 비교 함수
        Vue.config.globalProperties.$getSum = function(prevDate, currentDate){
           alert(prevDate)
           alert(currentDate)
        }

        // 모달 호출
        Vue.config.globalProperties.$showAlert = function(title, message){          
            alertRef.setTitle(title);
            alertRef.setMessage(message);
            alertRef.showModal();
        }
        
         // confirm 창 호출 
         Vue.config.globalProperties.$showConfirm = async function(title, message){          
           
            alertRef.setTitle(title);
            alertRef.setMessage(message);
            const resultData =  await alertRef.showConfirm();
            return resultData;
        }
        
        // 로그인 확인(쿠키가 만료된 후에 이벤트가 발생하면 로그인 창으로 이동)
        Vue.config.globalProperties.$checkLogin = function () {
            if ($cookies.get('USER_ID') == null && $cookies.get('USER') == null && $cookies.get('JSESSIONID') == null) {
                this.$router.push('/');
                this.$router.go();
                return true;
            }
        }
    }
}
