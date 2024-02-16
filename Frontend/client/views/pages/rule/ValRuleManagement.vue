<!--
작성자 : 임재현
작성일 : 2024.01.17
내용 : 검증룰 페이지
-->
<template>
  <div class="container">
    <div class="page-titleZone flex justify-between">
      <p class="main-title">진단규칙설정 > 검증룰 관리</p>
      <PageNavigation />
    </div>
    <div class="content">
      <div class="row">
        <div class="content-titleZone">
          <p class="box-title">> 검색조건</p>
        </div>
        <div class="searchbar-zone">
          <div class="flex justify-start align-center">
            <span class="option-title">검증유형</span>
            <select class="square-select" v-model="previousData.val_rule_type">
              <option value="전체">전체</option>
              <option v-for="item in dqiTypeList" :key="item" :value="item">
                {{ item }}
              </option>
            </select>
            <span class="option-title">품질지표명</span>
            <select v-model="previousData.dqi_nm" class="square-select">
              <option value="전체">전체</option>
              <option value="글자깨짐">글자깨짐</option>
              <option value="공백, 특수문자">공백, 특수문자</option>
              <option value="필수값">필수값</option>
              <option value="날짜 도메인">날짜 도메인</option>
              <option value="번호 도메인">번호 도메인</option>
              <option value="여부 도메인">여부 도메인</option>
              <option value="금액 도메인">금액 도메인</option>
              <option value="수량 도메인">수량 도메인</option>
              <option value="율 도메인">율 도메인</option>
            </select>
            <span class="option-title">검증룰명</span>
            <div class="search-square">
              <input
                v-model="previousData.val_rule_nm"
                type="text"
                class="square-input"
                placeholder="검증룰명"
              />
              <button class="square-button" @click="setPage(1)">
                <svg-icon
                  type="mdi"
                  :path="searchPath"
                  class="square-icon"
                ></svg-icon>
              </button>
            </div>
            <span class="option-title"
              >총 {{ dbListCount }}건이 검색되었습니다!</span
            >
          </div>
        </div>
        <div class="table-zone">
          <div
            class="flex justify-between align-center"
            style="margin: 5px 10px 10px"
          >
            <div class="count-zone">
              <p>
                총 <span id>{{ dbListCount }}</span
                >건 중
                <span>{{ checkboxList.filter((item) => item).length }}</span
                >건 선택
              </p>
            </div>
          </div>
          <table class="list-table">
            <colgroup>
              <col style="width: 5%" />
              <col style="width: 5%" />
              <col style="width: 25%" />
              <col style="width: 20%" />
              <col style="width: 5%" />
              <col style="width: 30%" />
              <col style="width: 10%" />
            </colgroup>
            <thead>
              <tr>
                <th>No.</th>
                <th>선택</th>
                <th>검증룰명</th>
                <th>검증유형</th>
                <th>매칭유형</th>
                <th>검증룰</th>
                <th>품질지표명</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(item, index) in dataList"
                :key="item.val_rule_id"
                @dblclick="dataSelectOne(item)"
              >
                <td>
                  {{
                    index +
                    1 +
                    (searchData.currentPage - 1) * searchData.perPage
                  }}
                </td>
                <td>
                  <input
                    type="checkbox"
                    v-model="checkboxList[index]"
                    @change="checkboxWatcher(index, item.val_rule_id)"
                    :disabled="
                      item.val_rule_match === '매치' && item.user_id === 'admin'
                    "
                  />
                </td>
                <td>{{ item.val_rule_nm }}</td>
                <td>{{ item.val_rule_type }}</td>
                <td>{{ item.val_rule_match }}</td>
                <td>{{ item.val_rule_detail }}</td>
                <td>{{ item.dqi_nm }}</td>
              </tr>
            </tbody>
          </table>
          <div class="flex justify-start">
            <button @click="isChecked()" class="red-btn small-btn">
              선택 항목 삭제
            </button>
          </div>
        </div>
        <div>
          <PaginationButton
            v-model:currentPage="searchData.currentPage"
            :perPage="searchData.perPage"
            :totalCount="dbListCount"
            :maxRange="5"
            :click="handlePageClick"
          />
        </div>
      </div>
      <div class="row form-box" style="padding: 20px">
        <div class="content-titleZone">
          <p class="box-title">> 검증룰 상세 정보 조회 / 추가 / 변경</p>
        </div>
        <div class="table-zone">
          <table class="form-table2">
            <colgroup>
              <col style="width: 5%" />
              <col style="width: 25%" />
              <col style="width: 5%" />
              <col style="width: 25%" />
              <col style="width: 5%" />
              <col style="width: 25%" />
            </colgroup>
            <tbody>
              <tr>
                <th>검증룰명*</th>
                <td>
                  <input
                    type="text"
                    v-model="newData.val_rule_nm"
                    class="valInput"
                  />
                </td>
                <th>품질지표명*</th>
                <td>
                  <select
                    v-model="newData.dqi_nm"
                    class="square-select valInput"
                  >
                    <option value="글자깨짐">글자깨짐</option>
                    <option value="공백, 특수문자">공백, 특수문자</option>
                    <option value="필수값">필수값</option>
                    <option value="날짜 도메인">날짜 도메인</option>
                    <option value="번호 도메인">번호 도메인</option>
                    <option value="여부 도메인">여부 도메인</option>
                    <option value="금액 도메인">금액 도메인</option>
                    <option value="수량 도메인">수량 도메인</option>
                    <option value="율 도메인">율 도메인</option>
                  </select>
                </td>
                <th>검증유형*</th>
                <td>
                  <select
                    v-model="newData.val_rule_type"
                    class="square-select valInput"
                  >
                    <option
                      v-for="item in dqiTypeList"
                      :key="item"
                      :value="item"
                    >
                      {{ item }}
                    </option>
                  </select>
                </td>
              </tr>
              <tr>
                <th>검증룰*</th>
                <td>
                  <input
                    type="text"
                    v-model="newData.val_rule_detail"
                    class="valInput"
                  />
                </td>
                <th>검증룰설명</th>
                <td>
                  <input
                    type="text"
                    v-model="newData.val_rule_explain"
                    class="valInput"
                  />
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="flex justify-center">
          <button class="blue-btn small-btn" @click="handleButtonAction()">
            {{ buttonLabel }}
          </button>
          <button class="blue-btn small-btn" @click="resetForm()">
            초기화
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PageNavigation from "../../component/PageNavigation.vue";
import PaginationButton from "../../component/PaginationButton.vue";
import SvgIcon from "@jamescoyle/vue-icon";
import { mdiMagnify } from "@mdi/js";
import axios from "axios";
export default {
  data() {
    return {
      isEditMode: false,

      searchPath: mdiMagnify,
      inputValue: null,

      dqiTypeList: [], // 옵션 항목을 DB로부터 받음
      dataList: [], // DB로부터 받은 데이터 저장
      checkboxList: [], //체크박스 된 행
      selectedValRuleId: [], //체크박스에 의해 선택된 행의 val_rule_id 저장
      checkCount: 0,

      newData: {
        val_rule_id: "", //검증룰 index
        val_rule_nm: "", //검증룰명
        dqi_nm: "", //품질지표명을 입력받고 DB에는 해당 dqi_index가 저장됨, 후에 상위품질지표명을 참조하기 위함
        val_rule_type: "", //검증유형
        val_rule_detail: "", //검증룰
        val_rule_explain: "", //검증룰설명
        user_id: $cookies.get("USER_ID"), //유저 아이디
      },

      //검색 조건 백엔드로 보냄
      searchData: {
        dqi_nm: "전체",
        val_rule_type: "전체",
        val_rule_nm: "",
        currentPage: 1,
        perPage: 15,
        user_id: $cookies.get("USER_ID"),
      },

      previousData: {
        dqi_nm: "전체",
        val_rule_type: "전체",
        val_rule_nm: "",
        currentPage: 1,
        perPage: 15,
        user_id: $cookies.get("USER_ID"),
      },

      dbListCount: 0,
    };
  },
  methods: {
    /**
     * 내용 : 검색 조건에 기반한 테이블 조회 기능
     * 작성자 : 임재현
     * 시간 : 2024.01.18
     */
    selectValRule: function () {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      axios({
        url: "selectValRule.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.searchData,
      })
        .then(function (response) {
          vm.dataList = response.data.valRuleList;
          vm.dbListCount = response.data.count;
        })
        .catch(function (error) {
          vm.$showAlert("error", "selectValRule오류");
        });
    },
    /**
     * 내용 : 검색 조건 중 검증유형 옵션 구현
     * 작성자 : 임재현
     * 시간 : 2024.01.18
     */
    selectDqiType: function () {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      axios({
        url: "selectDqiType.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: {},
      })
        .then(function (response) {
          vm.dqiTypeList = response.data;
        })
        .catch(function (error) {
          vm.$showAlert("error", "selectDqiType오류");
        });
    },
    /**
     * 내용 : 검증룰 추가 버튼 구현
     * 작성자 : 임재현
     * 시간 : 2024.01.18
     */
    insertValRule: function () {
      const vm = this;
      axios({
        url: "insertValRule.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.newData,
      })
        .then(function (response) {
          vm.$showAlert("Success", "추가되었습니다");
          vm.resetForm();
          vm.selectValRule();
        })
        .catch(function (error) {
          vm.$showAlert("error", "insertValRule오류");
        });
    },
    /**
     * 내용 : 검증룰 수정 버튼 구현
     * 작성자 : 임재현
     * 시간 : 2024.01.18
     */
    updateValRule: function () {
      const vm = this;
      axios({
        url: "updateValRule.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.newData,
      })
        .then(function (response) {
          vm.$showAlert("Success", "수정되었습니다");
          vm.resetForm();
          vm.selectValRule();
          vm.isEditMode = false;
        })
        .catch(function (error) {
          vm.$showAlert("error", "updateValRule오류");
        });
    },
    /**
     * 내용 : 검증룰 입력칸 초기화 구현
     * 작성자 : 임재현
     * 시간 : 2024.01.18
     */
    resetForm: function () {
      if (this.$checkLogin() == true) {
        return;
      }
      this.newData = {
        val_rule_id: "",
        val_rule_nm: "",
        dqi_nm: "",
        val_rule_type: "",
        val_rule_match: "",
        val_rule_detail: "",
        val_rule_explain: "",
        user_id: $cookies.get("USER_ID"),
      };

      this.isEditMode = false;
    },
    /**
     * 내용 : 선택한 행 삭제 기능
     * 작성자 : 임재현
     * 시간 : 2024.01.18
     */
    deleteValRule: function () {
      const vm = this;

      let pureArray = this.toPureArray();

      this.checkboxList = [];
      axios({
        url: "deleteValRule.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: pureArray,
      })
        .then(function (response) {
          vm.setPage();
          vm.selectedValRuleId = [];
          vm.pureArray = [];
          vm.resetForm();
          vm.$showAlert("Success", "삭제되었습니다");
        })
        .catch(function (error) {
          vm.$showAlert("error", "deleteValRule오류");
        });
    },

    /**
     * 내용 : 행 선택
     * 작성자 : 임재현
     * 시간 : 2024.01.19
     */
    dataSelectOne: function (item, index) {
      this.isEditMode = true; // 데이터 편집 상태: 등록 -> 수정

      // 클릭한 데이터 정보를 newData 객체에 할당
      this.newData = {
        val_rule_id: item.val_rule_id,
        val_rule_nm: item.val_rule_nm,
        dqi_nm: item.dqi_nm,
        val_rule_type: item.val_rule_type,
        val_rule_match: item.val_rule_match,
        val_rule_detail: item.val_rule_detail,
        val_rule_explain: item.val_rule_explain,
        user_id: $cookies.get("USER_ID"),
      };
    },

    //후에 선택 저장에서도 활용되기 때문에 함수로 만들어 delete에서 분리함
    toPureArray: function () {
      // 주어진 객체
      let objToDelete = this.selectedValRuleId;

      let keysArray = Object.keys(objToDelete);

      // MyBatis에 전달하기 위해 배열로 변환
      let idsToDelete = keysArray.map((key) => objToDelete[key]);

      return idsToDelete;
    },

    // 등록 수정 실행 버튼
    handleButtonAction: function () {
      if (this.$checkLogin() == true) {
        return;
      }
      if (!this.newData.val_rule_nm) {
        this.$showAlert("deny", "검증룰명을 입력해 주세요.");
        return;
      }

      if (!this.newData.dqi_nm) {
        this.$showAlert("deny", "품질지표명을 선택해 주세요.");
        return;
      }

      if (!this.newData.val_rule_type) {
        this.$showAlert("deny", "검증유형을 입력해 주세요.");
        return;
      }

      if (!this.newData.val_rule_detail) {
        this.$showAlert("deny", "세부 검증룰을 입력해 주세요.");
        return;
      }
      if (
        //매치유형이 매치 이면서, 사용자 아이디가 admin이 아닐경우 경고 뜨고 리턴
        this.newData.val_rule_match === "매치" &&
        this.newData.user_id != "admin"
      ) {
        this.$showAlert("deny", "관리자만 수정 할 수 있습니다.");
        return;
      }

      if (this.isEditMode) {
        // 데이터 편집 상태: 수정
        this.updateValRule(); // 데이터 수정 함수 실행
      } else {
        // 데이터 편집 상태: 등록
        this.insertValRule(); // 데이터 등록 함수 실행
      }
    },

    //체크박스 변화 감시
    checkboxWatcher: function (index, val_rule_id) {
      if (this.checkboxList[index]) {
        this.selectedValRuleId[index] = val_rule_id;
      } else {
        delete this.selectedValRuleId[index];
      }
    },
    // 탭 변경
    showTab: function (tabName) {
      this.activeTab = tabName;
    },

    isChecked: function () {
      if (this.$checkLogin() == true) {
        return;
      }
      if (this.checkboxList.filter((item) => item).length === 0) {
        //체크박스 개수가 0 아니면 실행
        this.$showAlert("deny", "선택된 검증룰이 없습니다.");
        return;
      }
      if (
        this.newData.val_rule_match === "매치" &&
        this.newData.user_id != "admin"
      ) {
        this.$showAlert("deny", "관리자만 삭제 할 수 있습니다.");
        return;
      }
      this.deleteValRule();
    },

    setPage: function (flag) {
      if (this.$checkLogin() == true) {
        return;
      }
      if (flag) {
        //검색 아이콘을 클릭한 경우에는 1을 넘겨서 select 테이블의 검색 조건을 업데이트 해줌
        this.searchData = { ...this.previousData };
      }

      // if(현재 페이지에 표시할 데이터가 없을 시 첫번째 페이지로 돌아가는 조건문)
      this.searchData.currentPage = 1;
      this.selectValRule();
    },

    //페이지네이션 클릭 이벤트
    handlePageClick() {},
  },
  watch: {
    //테이블 페이지가 변하면 테이블 로드
    "searchData.currentPage": function () {
      if (this.$checkLogin() == true) {
        return;
      }
      this.checkboxList = []; //체크박스 된 행
      this.selectedValRuleId = []; //체크박스에 의해 선택된 행의 val_rule_id 저장
      this.selectValRule();
    },
  },
  computed: {
    PaginationButton: PaginationButton,
    // 버튼 레이블을 동적으로 반환
    buttonLabel() {
      return this.isEditMode ? "수정" : "등록";
    },
  },
  components: {
    PageNavigation: PageNavigation,
    PaginationButton: PaginationButton,
    SvgIcon: SvgIcon,
  },
  mounted() {
    this.selectDqiType();
    this.selectValRule();
  },
};
</script>

<style>
.valInput {
  width: 100%;
  min-width: -webkit-fill-available;
}
</style>
