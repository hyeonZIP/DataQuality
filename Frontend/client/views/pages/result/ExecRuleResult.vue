<template>
  <div class="container">
    <div class="page-titleZone flex justify-between">
      <p class="main-title">진단 결과 > 진단상세결과현황</p>
    </div>
    <div class="content">
      <div class="row">
        <div class="content-titleZone">
          <p class="box-title">> 검색 조건</p>
        </div>
        <div class="table-zone">
          <table class="list-table">
            <colgroup>
              <col style="width: 5%" />
              <col style="width: " />
              <col style="width: 5%" />
              <col style="width: " />
              <col style="width: 5%" />
              <col style="width: " />
              <col style="width: 5%" />
            </colgroup>
            <thead class="execRuleResultTable">
              <tr>
                <th>진단대상DBMS명</th>
                <td style="display: flex">
                  <select v-model="previousData.dbms_name">
                    <option value="none" disabled selected>--DBMS명--</option>
                    <option v-for="item in dbmsOption" :key="item">
                      {{ item }}
                    </option>
                  </select>

                  <select v-model="previousData.schema_name">
                    <option value="none" disabled selected>--스키마명--</option>
                    <option v-for="item in schemaOption" :key="item">
                      {{ item }}
                    </option>
                  </select>
                </td>
                <th>검증분류</th>
                <td>
                  <select v-model="previousData.rule_type">
                    <option value="none">전체</option>
                    <option value="domain_val">검증룰</option>
                    <option value="domain_code">코드</option>
                    <option value="ref_rule">참조무결성</option>
                    <option value="code_rule">업무규칙</option>
                    <option value="dupl_rule">중복분석</option>
                  </select>
                </td>
                <th>품질지표명</th>
                <td>
                  <select v-model="previousData.dqi_nm">
                    <option value="">전체</option>
                    <option value="시간순서 일관성">시간순서 일관성</option>
                    <option value="선후관계 정확성">선후관계 정확성</option>
                    <option value="논리관계 일관성">논리관계 일관성</option>
                    <option value="계산식">계산식</option>
                    <option value="참조관계">참조관계</option>
                    <option value="글자깨짐">글자깨짐</option>
                    <option value="공백">공백</option>
                    <option value="필수값">필수값</option>
                    <option value="중복데이터">중복데이터</option>
                    <option value="날짜 도메인">날짜 도메인</option>
                    <option value="번호 도메인">번호 도메인</option>
                    <option value="여부 도메인">여부 도메인</option>
                    <option value="코드 도메인">코드 도메인</option>
                    <option value="금액 도메인">금액 도메인</option>
                    <option value="수량 도메인">수량 도메인</option>
                    <option value="율 도메인">율 도메인</option>
                  </select>
                </td>
                <th :rowspan="2">
                  <button class="blue-btn large-btn" @click="setPage()">
                    검색
                  </button>
                </th>
              </tr>
              <tr>
                <th>테이블명</th>
                <td><input type="text" v-model="previousData.table_nm" /></td>
                <th>컬럼명</th>
                <td><input type="text" v-model="previousData.column_nm" /></td>
                <th>컬럼한글명</th>
                <td>
                  <input type="text" v-model="previousData.column_nm_k" />
                </td>
              </tr>
            </thead>
          </table>
        </div>
      </div>

      <div class="table-zone">
        <table class="list-table">
          <colgroup>
            <col style="width: " />
            <col style="width: " />
            <col style="width: " />
            <col style="width: " />
            <col style="width: " />
            <col style="width: " />
            <col style="width: " />
            <col style="width: " />
            <col style="width: " />
            <col style="width: " />
            <col style="width: " />
            <col style="width: " />
          </colgroup>
          <thead>
            <tr>
              <th>No.</th>
              <th>DBMS명</th>
              <th>스키마명</th>
              <th>테이블명</th>
              <th>컬럼명</th>
              <th>컬럼한글명</th>
              <th>프로파일 시작 시간</th>
              <th>분석총건수</th>
              <th>추정오류건수</th>
              <th>추정오류율(%)</th>
              <th>품질지표명</th>
              <th>검증룰명</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(item, index) in execResultList"
              :key="index"
              @dblclick="tableSelect()"
            >
              <td>
                {{
                  index + 1 + (searchData.currentPage - 1) * searchData.perPage
                }}
              </td>
              <td>{{ item.dbms_name }}</td>
              <td>{{ item.schema_name }}</td>
              <td>{{ item.table_nm }}</td>
              <td>{{ item.column_nm }}</td>
              <td>{{ item.column_nm_k }}</td>
              <td>{{item.task_start_tm}}</td>
              <td>{{item.total_task}}</td>
              <td>{{item.error_task}}</td>
              <td>{{item.error_task/item.total_task}}</td>
              <td>{{item.dqi_nm}}</td>
              <td>검증룰명{{}}</td>
            </tr>
          </tbody>
          <tfoot>
            <th colspan="12">총 {{ rowTotalCount }}건</th>
          </tfoot>
        </table>
        <PaginationButton
          v-model:currentPage="searchData.currentPage"
          :perPage="searchData.perPage"
          :totalCount="rowTotalCount"
          :maxRange="5"
          :click = "handlePageClick"
        />
      </div>
    </div>
  </div>
  <div v-show="isModalOpen" class="modal-wrapper">
    <div class="modal-container">
      <div class="modal-title">
        <div class="flex justify-between align-center">
          <h2>상세 내용 조회</h2>
          <button class="close-btn" @click="closeModal">X</button>
        </div>
      </div>
      <div class="modal-content-monthly" v-show="modalType == 'tab-modal'">
        <ul class="tab-content">
          <li id="tab01" v-show="activeTab === 'tab1'">
            <table class="list-table">
              <colgroup>
                <col style="width: 5%" />
                <col style="width: 85%" />
                <col style="width: " />
              </colgroup>
              <h1>{{ null }}컬럼오류데이터조회</h1>
              <thead>
                <tr>
                  <th>No.</th>
                  <th>오류 데이터</th>
                  <th>건수</th>
                </tr>
                <tbody>
                    <tr>
                        <!-- v-for자리 -->
                        <td>1</td>
                        <td>test_error</td>
                        <td>15</td>
                        </tr>
                </tbody>

              </thead>
            </table>
            <table class="list-table">
              <thead>
                <tr>
                  <th>추가할</th>
                  <th>테이블</th>
                  <th>공간</th>
                  <th>입니다</th>
                  <th>임재현</th>
                  <th>임재현</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1</td>
                  <td>04:00:00</td>
                  <td>접속성공</td>
                  <td>접속성공</td>
                  <td>접속성공</td>
                  <td>접속성공</td>
                </tr>
              </tbody>
            </table>
          </li>
          <li id="tab01" v-show="activeTab === 'tab1'"></li>
        </ul>
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
      isModalOpen: false,
      activeTab: "tab1",
      // modalType: 'form-modal',
      modalType: "tab-modal",

      searchPath: mdiMagnify,
      inputValue: null,

      //SELECT한 데이터 저장 리스트
      execResultList: [],
      //옵션리스트
      dbmsOption: [],
      schemaOption: [],

      //검색 조건 데이터
      searchData: {
        user_id: $cookies.get("USER_ID"),
        dbms_name: "none",
        dbms_id: "",
        schema_name: "none",
        schema_id: "",
        rule_type: "none",
        dqi_nm: "",
        table_nm: "",
        table_nm_k: "",
        table_id: "",
        column_nm: "",
        column_nm_k: "",
        column_id: "",
        currentPage: 1,
        perPage: 10,
      },

      previousData: {
        user_id: $cookies.get("USER_ID"),
        dbms_name: "none",
        dbms_id: "",
        schema_name: "none",
        schema_id: "",
        rule_type: "none",
        dqi_nm: "",
        table_nm: "",
        table_nm_k: "",
        table_id: "",
        column_nm: "",
        column_nm_k: "",
        column_id: "",
        currentPage: 1,
        perPage: 5,
      },

      rowTotalCount: 0,
    };
  },
  methods: {
    /**
     * 내용 : 검색 조건 바탕으로 데이터 SELECT
     * 작성자 : 임재현
     * 시간 : 2024.01.30
     */
    selectExecResult: function () {
      const vm = this;
      if (vm.$checkLogin() == true) {
          return;
      }
      axios({
        url: "selectExecResult.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.searchData,
      })
        .then(function (response) {
          vm.execResultList = response.data.table;
          vm.rowTotalCount = response.data.count;
        })
        .catch(function (error) {
          vm.$showAlert("error", "selectExecResult 오류");
        });
    },

    selectDbms: function () {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      axios({
        url: "selectDbms.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.previousData,
      })
        .then(function (response) {
          vm.dbmsOption = response.data;
        })
        .catch(function (error) {
          vm.$showAlert("error", "selectDbms 오류");
        });
    },

    selectSchema: function () {
      const vm = this;
      axios({
        url: "selectSchema.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.previousData,
      })
        .then(function (response) {
          vm.schemaOption = response.data;
        })
        .catch(function (error) {
          vm.$showAlert("error", "selectSchema 오류");
        });
    },
    selectManageRule: function () {
      const vm = this;
      axios({
        url: "selectManageRule.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.searchData,
      })
        .then(function (response) {
          vm.manageRuleList = response.data.manageList;
          vm.rowTotalCount = response.data.count;
        })
        .catch(function (error) {
          vm.$showAlert("error", "selectManageRule 오류");
        });
    },

    tableSelect: function () {
      this.isModalOpen = true;
    },

    // 탭 변경
    showTab: function (tabName) {
      this.activeTab = tabName;
    },
    closeModal: function () {
      this.isModalOpen = false;
    },

    //페이지네이션 클릭 이벤트
    handlePageClick() {},

    //검색버튼을 눌렀을 시
    setPage: function () {
      const vm = this;
      vm.searchData = { ...vm.previousData };
      vm.selectManageRule();
    },
  },

  watch: {
    //dbmsOption이 선택되는지 감시
    "previousData.dbms_name"() {
      this.previousData.schema_name = "none";
      this.selectSchema();
    },
    "searchResultData.schema_name"() {
      this.tableColumnList = ""; //컬럼 초기화
      this.tableColumnData.table_id = 0; //선택된 테이블 초기화
      this.tableColumnData.currentPage = 1; //컬럼 목록 페이지네이션
      this.selectedCol = 0; //선택된 컬럼 초기화
      this.selectTableColumn();
    },


    "searchData.currentPage"() {
      this.selectExecResult();
    },
  },
  computed: {},
  components: {
    PageNavigation: PageNavigation,
    PaginationButton: PaginationButton,
    SvgIcon: SvgIcon,
  },
  mounted() {
    this.selectDbms();
  },
};
</script>

<style scoped>
.execRuleResultTable tr td select, input {
    width: 100%;
}
</style>
