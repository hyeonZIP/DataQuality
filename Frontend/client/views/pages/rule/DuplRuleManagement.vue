<template>
  <div class="container">
    <div class="page-titleZone flex justify-between">
      <p class="main-title">진단규칙설정 > 중복분석 관리</p>
      <PageNavigation />
    </div>
    <div class="content-box flex justify-between">
      <div class="left-content flex40">
        <div class="flex-column justify-between">
          <div class="host-zone flex20">
            <div class="content-titleZone">
              <p class="box-title">> 검색 조건</p>
            </div>
            <div>
              <div class="table-zone">
                <table class="form-table">
                  <colgroup>
                    <col style="width: 10%" />
                    <col style="width: 40%" />
                    <col style="width: 10%" />
                    <col style="width: 40%" />
                  </colgroup>
                  <tbody>
                    <tr>
                      <th>DBMS명</th>
                      <td>
                        <select
                          v-model="searchResultData.dbms_name"
                          style="min-width: -webkit-fill-available"
                        >
                          <option value="none" disabled selected>선택</option>
                          <option
                            v-for="item in dbmsOption"
                            :key="item"
                            :value="item"
                          >
                            {{ item }}
                          </option>
                        </select>
                      </td>
                      <th>스키마명</th>
                      <td>
                        <select
                          v-model="searchResultData.schema_name"
                          style="min-width: -webkit-fill-available"
                        >
                          <option value="none" disabled selected>선택</option>
                          <option
                            v-for="item in schemaOption"
                            :key="item"
                            :value="item"
                          >
                            {{ item }}
                          </option>
                        </select>
                      </td>
                    </tr>
                    <tr>
                      <th>테이블명</th>
                      <td>
                        <input
                          type="text"
                          v-model="searchResultData.table_nm"
                          style="min-width: -webkit-fill-available"
                        />
                      </td>
                      <th>컬럼명</th>
                      <td>
                        <input
                          type="text"
                          v-model="searchResultData.column_nm"
                          style="min-width: -webkit-fill-available"
                        />
                      </td>
                    </tr>
                  </tbody>
                </table>
                <div class="justify-start">
                  <button
                    class="blue-btn small-btn"
                    @click="setPage()"
                    style="margin-top: 10px"
                  >
                    검색
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div class="file-zone flex80" style="margin-top: 50px">
            <p
              class="box-title"
              style="border-bottom: solid 1px; padding-bottom: 10px"
            >
              > 검색 결과
            </p>
            <table class="list-table">
              <colgroup>
                <col style="width: 5%" />
                <col style="width: 20%" />
                <col style="width: 20%" />
                <col style="width: 20%" />
                <col style="width: 20%" />
              </colgroup>
              <thead>
                <tr>
                  <th>No.</th>
                  <th>DBMS명</th>
                  <th>스키마명</th>
                  <th>테이블명</th>
                  <th>테이블 한글명</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  :class="{ 'selected-row': isSelectedRow(item.table_id) }"
                  v-for="(item, index) in searchResultList"
                  :key="index"
                  @click="selectTableRow(item.table_id)"
                >
                  <td>
                    {{
                      index +
                      1 +
                      (searchResultData.currentPage - 1) *
                        searchResultData.perPage
                    }}
                  </td>
                  <td :title="item.dbms_name">{{ item.dbms_name }}</td>
                  <td :title="item.schema_name">{{ item.schema_name }}</td>
                  <td :title="item.table_nm">{{ item.table_nm }}</td>
                  <td :title="item.table_nm_k">{{ item.table_nm_k }}</td>
                </tr>
              </tbody>
            </table>
            <PaginationButton
              v-model:currentPage="searchResultData.currentPage"
              :perPage="searchResultData.perPage"
              :totalCount="searchResultCount"
              :maxRange="5"
              :click="handlePageClick"
            />
          </div>
        </div>
      </div>
      <div class="right-content flex60">
        <div class="flex-column">
          <div class="content-titleZone">
            <p class="box-title">> 중복분석 상세 정보</p>
          </div>
          <table class="list-table">
            <colgroup>
              <col style="width: 10%" />
              <col style="width: 20%" />
              <col style="width: 25%" />
              <col style="width: 25%" />
            </colgroup>
            <thead>
              <tr>
                <th>No.</th>
                <th>선택</th>
                <th>컬럼명</th>
                <th>컬럼 한글명</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(column, index) in columnList" :key="index">
                <td>
                  {{ index + 1 + (column_currentPage - 1) * column_perPage }}
                </td>
                <td>
                  <input
                    type="checkbox"
                    v-model="selectedColumnIds"
                    :value="{
                      column_id: column.column_id,
                      column_nm: column.column_nm,
                    }"
                  />
                </td>
                <!-- 상태 추가 필요 -->
                <td :title="column.clumn_nm">{{ column.column_nm }}</td>
                <td :title="column.column_nm_k">{{ column.column_nm_k }}</td>
              </tr>
            </tbody>
          </table>
          <PaginationButton
            v-model:currentPage="column_currentPage"
            :perPage="column_perPage"
            :totalCount="column_totalCount"
            :maxRange="5"
            :click="handlePageClick"
          />
          <div class="justify-start">
            <button class="blue-btn small-btn" :onClick="insertColumn">
              저장
            </button>
          </div>
          <div class="file-zone flex80" style="margin-top: 50px">
            <div style="border-bottom: solid 1px; padding-bottom: 10px">
              <p class="box-title">> 중복분석 진단 대상</p>
            </div>
            <table class="list-table">
              <colgroup>
                <col style="width: 10%" />
                <col style="width: 20%" />
                <col style="width: " />
              </colgroup>
              <thead>
                <tr>
                  <th>No.</th>
                  <th>선택</th>
                  <th>DBMS명</th>
                  <th>스키마명</th>
                  <th>테이블명</th>
                  <th>진단 대상 컬럼명</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(item, index) in selectCheckedListTable"
                  :key="index"
                >
                  <td>
                    {{
                      index +
                      1 +
                      (selectedCBL.curPage - 1) * selectedCBL.perPage
                    }}
                  </td>
                  <td>
                    <input
                      type="checkbox"
                      v-model="selectDuplRuleId"
                      :value="item.dupl_rule_id"
                    />
                  </td>
                  <td :title="item.dbms_name">{{ item.dbms_name }}</td>
                  <td :title="item.schema_name">{{ item.schema_name }}</td>
                  <td :title="item.table_nm">{{ item.table_nm }}</td>
                  <td :title="item.code_list_nm">{{ item.code_list_nm }}</td>
                </tr>
              </tbody>
            </table>
            <PaginationButton
              v-model:currentPage="selectedCBL.curPage"
              :perPage="selectedCBL.perPage"
              :totalCount="selectedCBL.totalCount"
              :maxRange="5"
              :click="handlePageClick"
            />
            <div class="justify-start">
              <button class="blue-btn small-btn" :onClick="deleteColumn">
                선택 취소
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import PageNavigation from "../../component/PageNavigation.vue";
import PaginationButton from "../../component/PaginationButton.vue";
import FileTree from "../../component/FileTree.vue";
import SvgIcon from "@jamescoyle/vue-icon";
import { mdiMagnify } from "@mdi/js";
export default {
  data() {
    return {
      searchPath: mdiMagnify,

      /* ----- 검색 ------ */
      searchResultList: [], //검색결과 리스트
      dbmsOption: [], //DBMS 옵션
      schemaOption: [], //스키마 옵션

      //검색조건
      searchResultData: {
        dbms_name: "none",
        dbms_id: "",
        schema_name: "none",
        scheam_id: "",
        table_id: "",
        table_nm: "",
        column_nm: "",
        user_id: $cookies.get("USER_ID"),
        currentPage: 1,
        perPage: 10,
      },

      //검색결과 페이징
      searchResultCount: 0,

      /* ------ 컬럼 조회 ------ */
      selected_tableId: [],
      column_currentPage: 1,
      column_perPage: 10,
      column_totalCount: 0,

      columnList: [],
      selectedColumnIds: [],
      /* ------ 중복분석 insert할 때 체크한 행의 ID값 저장 ------ */
      selectedCheckBoxList: [],

      /* ------ 중복분석 선택 대상 관련 ------ */
      selectCheckedListTable: [],
      testList: [],
      selectedCBL: {
        curPage: 1,
        perPage: 5,
        totalCount: 0,
      },

      // 선택한 중복분석 대상
      selectDuplRuleId: [],
    };
  },
  methods: {
    /* DBMS 목록 검색 */
    selectDbms: function () {
      const vm = this;
      axios({
        url: "selectDbms.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.searchResultData,
      })
        .then(function (response) {
          vm.dbmsOption = response.data;
        })
        .catch(function (error) {
          vm.$showAlert("error", "DBMS 조회 오류");
        });
    },

    /* 스키마 목록 검색 */
    selectSchema: function () {
      const vm = this;
      axios({
        url: "selectSchema.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.searchResultData,
      })
        .then(function (response) {
          vm.schemaOption = response.data;
        })
        .catch(function (error) {
          vm.$showAlert("error", "스키마 조회 오류");
        });
    },

    /* 검색 결과 - 테이블 조회 */
    selectSearchResult: function () {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      axios({
        url: "selectSearchResult.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.searchResultData,
      })
        .then(function (response) {
          vm.searchResultList = response.data.table;
          vm.searchResultCount = response.data.count;
        })
        .catch(function (error) {
          vm.$showAlert("error", "테이블 조회 오류");
        });
    },

    /* 컬럼 조회 */
    selectTableRow(table_id) {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      vm.selected_tableId = table_id;

      axios({
        url: "/selectDupRuleColumn.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: {
          table_id: table_id,
          currentPage: vm.column_currentPage,
          perPage: vm.column_perPage,
        },
      })
        .then((res) => {
          vm.columnList = res.data.columnList;
          vm.column_totalCount = res.data.columnListCount;
          vm.selectCheckedList();
        })
        .catch((err) => {
          vm.$showAlert("error", "컬럼 조회 오류");
        });
    },

    /* 컬럼 저장 */
    insertColumn() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }

      if (vm.selectedColumnIds.length === 0) {
        vm.$showAlert("error", "컬럼을 선택해주세요.");
        return;
      }

      /* 선택한 컬럼 id만 배열로 저장 */
      const selectedColumnIdList = Object.values(vm.selectedColumnIds).map(
        (column) => column.column_id
      );

      const selectedColumnNmList = Object.values(vm.selectedColumnIds).map(
        (column) => column.column_nm
      );

      //240206 임재현 컬럼ID 값
      vm.selectedColumnIdList = selectedColumnIdList;

      axios({
        url: "/insertDupRuleColumn.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: {
          columnList: vm.selectedColumnIdList,
          user_id: $cookies.get("USER_ID"),
        },
        // user_id: $cookies.get("USER_ID"),
      })
        .then((res) => {
          vm.$showAlert("response", "저장되었습니다.");
          vm.selectTableRow(vm.selected_tableId);
          vm.selectedColumnIds = []; //선택된 컬럼 초기화
          vm.column_currentPage = 1;
          vm.selectCheckedList();
        })
        .catch((err) => {
          vm.$showAlert("error", "컬럼 저장 실패");
        });
    },
    /**
     * 내용 : 중복검사 선택 대상 테이블 조회
     * 작성자 : 임재현
     * 시간 : 2024.02.06
     */
    selectCheckedList() {
      const vm = this;
      axios({
        url: "/selectCheckedList.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.selectedCBL,
      })
        .then((res) => {
          vm.selectCheckedListTable = res.data.table;
          vm.testList = res.data.table;
          vm.selectedCBL.totalCount = res.data.count;
        })
        .catch((err) => {
          vm.$showAlert("error", "컬럼 저장 실패");
        });
    },

    /* 컬럼 저장 취소 */
    deleteColumn() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }

      if (vm.selectDuplRuleId.length === 0) {
        vm.$showAlert("error", "컬럼을 선택해주세요.");
        return;
      }

      axios({
        url: "/deleteDupRuleColumn.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: {
          ruleIdList: vm.selectDuplRuleId,
        },
      })
        .then((res) => {
          vm.$showAlert("response", "저장 취소했습니다.");
          vm.selectTableRow(vm.selected_tableId);
          vm.selectDuplRuleId = []; //선택된 컬럼 초기화
          vm.selectedCBL.curPage = 1;
        })
        .catch((err) => {
          vm.$showAlert("error", "컬럼 저장 취소 실패");
        });
    },

    // table_id 확인
    isSelectedRow(tableId) {
      return tableId === this.selected_tableId;
    },

    //예외 처리 및 검색 버튼 누를시 페이지 1로 초기화
    setPage: function () {
      if (this.$checkLogin() == true) {
        return;
      }
      if (this.searchResultData.dbms_name === "none") {
        this.$showAlert("deny", "DBMS를 선택해주세요.");
        return;
      }
      if (this.searchResultData.schema_name === "none") {
        this.$showAlert("deny", "스키마를 선택해주세요.");
        return;
      }
      this.searchResultData.currentPage = 1;
      this.selectSearchResult();
    },

    //페이지네이션 클릭 이벤트
    handlePageClick() {},
  },
  watch: {
    "searchResultData.dbms_name"() {
      //DBMS선택시 기존에 선택했던 스키마 초기화, 이전과 동일한거 선택시 변동 사항 없음으로 초기화 되지않음
      this.searchResultData.schema_name = "none";
      this.selectSchema();
    },
    "searchResultData.schema_name"() {
      this.selected_tableId = "";
      this.searchResultData.currentPage = 1;
    },

    "searchResultData.currentPage": function () {
      this.selectSearchResult();
    },
    column_currentPage: function () {
      this.selectTableRow(this.selected_tableId);
    },

    "selectedCBL.curPage": function () {
      this.selectCheckedList();
    },
    selected_tableId() {
      this.column_currentPage = 1;
    },
  },
  computed: {
    PaginationButton: PaginationButton,
  },
  components: {
    PageNavigation: PageNavigation,
    FileTree: FileTree,
    SvgIcon: SvgIcon,
    PaginationButton: PaginationButton,
  },
  mounted() {
    if (this.$checkLogin() == true) {
      return;
    }
    this.selectSearchResult();
    this.selectDbms();
  },
};
</script>

<style lang="css" scoped>
.content-zone {
  border: 1px solid #eee;
  padding: 10px;
  height: calc(100% rgb(49, 83, 84));
  overflow: auto;
}
.list-table tbody .selected-row {
  background-color: #c6feff;
}

.list-table tbody tr td {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
