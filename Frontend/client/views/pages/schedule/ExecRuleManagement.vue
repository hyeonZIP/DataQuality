<template>
  <div class="container">
    <div class="page-titleZone flex justify-between">
      <p class="main-title">진단 실행 > 진단항목별 실행</p>
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
            <thead class="execRuleManageTable">
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
          <div class="flex justify-end">
            <button class="blue-btn small-btn" @click="execManageRule()">
              진단항목실행
            </button>
          </div>
        </div>
      </div>

      <div class="table-zone">
        <table class="list-table">
          <colgroup>
            <col style="width: 5%" />
            <col style="width: 5%" />
            <col style="width: 10%" />
            <col style="width: 10" />
            <col style="width: 10%" />
            <col style="width: 10%" />
            <col style="width: 10%" />
            <col style="width: 10%" />
            <col style="width: " />
          </colgroup>
          <thead>
            <tr>
              <th>No.</th>
              <th>선택</th>
              <th>DBMS명</th>
              <th>스키마명</th>
              <th>테이블명</th>
              <th>테이블한글명</th>
              <th>컬럼명</th>
              <th>컬럼한글명</th>
              <th>검증룰</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in manageRuleList" :key="index">
              <td>
                {{
                  index + 1 + (searchData.currentPage - 1) * searchData.perPage
                }}
              </td>
              <!-- 진단 항목 실행 버튼을 클릭시 수행할 인덱스들을 체크박스 리스트에 DBMS_ID 혹은 MANAGE_RULE_ID를 저장 -->
              <td>
                <input
                  type="checkbox"
                  v-model="checkboxList[index]"
                  @change="checkboxWatcher(index, item.manage_rule_id)"
                />
              </td>
              <td>{{ item.dbms_name }}</td>
              <td>{{ item.schema_name }}</td>
              <td>{{ item.table_nm }}</td>
              <td>{{ item.table_nm_k }}</td>
              <td>{{ item.column_nm }}</td>
              <td>{{ item.column_nm_k }}</td>
              <td>{{ item.result_nm }}</td>
            </tr>
          </tbody>
          <tfoot>
            <th colspan="9">
              총 {{ rowTotalCount }}건 - 선택
              {{ checkboxList.filter((item) => item).length }}행
            </th>
          </tfoot>
        </table>
        <PaginationButton
          v-model:currentPage="searchData.currentPage"
          :perPage="searchData.perPage"
          :totalCount="rowTotalCount"
          :maxRange="5"
          :click="handlePageClick"
        />
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
      searchPath: mdiMagnify,
      inputValue: null,

      //SELECT한 데이터 저장 리스트
      manageRuleList: [],
      //옵션리스트
      dbmsOption: [],
      schemaOption: [],

      //체크박스 리스트
      checkboxList: [],
      selectedManageRuleId: [],

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
        perPage: 10,
      },

      // 진단 실행할 리스트
      execData: [],

      rowTotalCount: 0,
    };
  },
  methods: {
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

    execManageRule: function () {
      const vm = this;
      axios({
        url: "execManageRule.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.execData,
      })
        .then(function (response) {})
        .catch(function (error) {
          vm.$showAlert("error", "진단 실행 오류");
        });
    },

    //체크박스 변화 감시
    checkboxWatcher: function (index, manage_rule_id) {
      if (this.checkboxList[index]) {
        this.selectedManageRuleId[index] = manage_rule_id;
        this.execData.push(this.manageRuleList[index]);
      } else {
        delete this.selectedManageRuleId[index];
        const indexOfItemToRemove = this.execData.findIndex(
          (item) => item.manage_rule_id === manage_rule_id
        );
        if (indexOfItemToRemove !== -1) {
          this.execData.splice(indexOfItemToRemove, 1);
        }
      }
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
      this.selectManageRule();
      this.checkboxList = [];
      this.selectedManageRuleId = [];
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
.execRuleManageTable tr td select, input {
  width: 100%;
}
</style>
