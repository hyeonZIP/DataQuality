<template>
  <!-- 알림창 질문형식 취소, 확인 선택 -->
  <div v-show="isSelectModalOpen" class="modal-wrapper">
    <div class="modal-container small-modal">
      <div class="modal-title text-ct">
        <h2>{{ modalTitle }}</h2>
      </div>
      <div class="modal-content-monthly">
        <p class="alert-write text-ct">
          {{ modalContents }}
        </p>
      </div>
      <div class="modal-end flex justify-between">
        <button
          class="gray-btn large-btn flex45"
          style="margin: 10px 5px"
          @click="closeCancelModal()"
        >
          취소
        </button>
        <button class="blue-btn large-btn flex45" @click="closeCheckModal()">
          확인
        </button>
      </div>
    </div>
  </div>
  <div class="container">
    <div class="page-titleZone flex justify-between">
      <p class="main-title">진단규칙설정 > 업무 규칙</p>
    </div>
    <div class="content">
      <div class="row">
        <div class="content-titleZone">
          <p class="box-title">> 검색조건</p>
        </div>
        <div class="searchbar-zone">
          <div class="flex justify-start align-center">
            <span class="option-title">진단대상DBMS</span>
            <select
              name=""
              id=""
              class="square-select"
              v-model="searchData.dbms_id"
              @change="searchSelectDbms()"
            >
              <option :value="null">선택</option>
              <option
                :value="item.dbms_id"
                v-for="(item, index) in dbmsList"
                :key="index"
              >
                {{ item.dbms_name }}
              </option>
            </select>
            <span class="option-title">스키마명</span>
            <select
              name=""
              id=""
              class="square-select"
              v-model="searchData.schema_id"
              @change="updateSearchButton()"
            >
              <option :value="null">{{ searchSchema }}</option>
              <option
                :value="item.schema_id"
                v-for="(item, index) in searchSchemaList"
                :key="index"
              >
                {{ item.schema_name }}
              </option>
            </select>
            <button class="blue-btn small-btn" @click="allWorkRuleList()">
              DBMS 검색
            </button>
            <span class="option-title"
              >총 {{ wrListCount }}건이 검색되었습니다!</span
            >
          </div>
        </div>
        <div class="table-zone">
          <div class="flex-between">
            <div class="count-zone">
              <p style="margin: 5px 10px 15px">
                총 <span id>{{ wrListCount }}</span
                >건 중 <span>{{ selectedItems.length }}</span
                >건 선택
              </p>
            </div>
          </div>
          <table class="list-table">
            <colgroup>
              <col style="width: " />
              <col style="width: " />
              <col style="width: " />
              <col style="width: " />
              <col style="width: " />
            </colgroup>
            <thead>
              <tr>
                <th>No.</th>
                <th>선택</th>
                <th>업무규칙명</th>
                <th>DBMS명</th>
                <th>스키마명</th>
                <th>테이블명</th>
                <th>컬럼명</th>
                <th>품질지표명</th>
                <th>설명</th>
                <th>건수SQL</th>
                <th>분석SQL</th>
              </tr>
            </thead>
            <tbody class="workRuleTable">
              <tr
                v-for="(item, index) in workRuleList"
                :key="index"
                @click="oneSelectWorkRule(item, index)"
                :class="{ selected: isSelected(index) }"
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
                    @change="checkHandle(item.work_rule_id)"
                  />
                </td>
                <td>{{ item.work_rule_name }}</td>
                <td>{{ item.dbms_name }}</td>
                <td>{{ item.schema_name }}</td>
                <td>{{ item.table_name }}</td>
                <td>{{ item.column_name }}</td>
                <td>{{ item.dqi_name }}</td>
                <td>{{ item.work_rule_explain }}</td>
                <td
                  style="
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                  "
                >
                  {{ item.work_rule_cnt }}
                </td>
                <td
                  style="
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                  "
                >
                  {{ item.work_rule_sql }}
                </td>
              </tr>
            </tbody>
          </table>
          <div>
            <button class="red-btn small-btn" v-on:click="selectWRDelete()">
              선택 항목 삭제
            </button>
          </div>
        </div>
        <PaginationButton
          v-model:currentPage="searchData.currentPage"
          :perPage="searchData.perPage"
          :totalCount="wrListCount"
          :maxRange="5"
          :click="handleClick"
        />
      </div>
      <div class="row form-box" style="padding: 20px">
        <div class="content-titleZone">
          <p class="box-title">> 업무 규칙 상세 정보 조회 / 추가 / 변경</p>
        </div>
        <div class="table-zone">
          <table class="form-table2">
            <colgroup>
              <col style="width: 10%" />
              <col style="width: 40%" />
              <col style="width: 10%" />
              <col style="width: 40%" />
            </colgroup>
            <tbody>
              <tr>
                <th>업무규칙명*</th>
                <td>
                  <input
                    type="text"
                    name=""
                    id=""
                    placeholder="입력하세요"
                    v-model.trim="wrManagement.work_rule_nm"
                    class="workRuleInput"
                  />
                </td>
                <th>품질지표명*</th>
                <td>
                  <div class="flex justify-between">
                    <select
                      name=""
                      id=""
                      class="flex30 workRuleInput"
                      v-model="wrManagement.dqi_nm"
                    >
                      <option :value="null">선택</option>
                      <option
                        :value="item"
                        v-for="(item, index) in dqiList"
                        :key="index"
                      >
                        {{ item }}
                      </option>
                    </select>
                  </div>
                </td>
              </tr>

              <tr>
                <th>DBMS명*</th>
                <td>
                  <div class="flex justify-between">
                    <select
                      name=""
                      id=""
                      class="flex30 workRuleInput"
                      v-model="optionSelect.dbms_id"
                      @change="changeDbmsNM()"
                    >
                      <option :value="null">선택</option>
                      <option
                        :value="item.dbms_id"
                        v-for="(item, index) in dbmsList"
                        :key="index"
                      >
                        {{ item.dbms_name }}
                      </option>
                    </select>
                  </div>
                </td>
                <th>테이블명*</th>
                <td>
                  <div class="flex justify-between">
                    <select
                      name=""
                      id=""
                      class="flex30 workRuleInput"
                      v-model="optionSelect.table_id"
                      @change="changeTable()"
                    >
                      <option :value="null">{{ selectTable }}</option>
                      <option
                        :value="item.table_id"
                        v-for="(item, index) in tableList"
                        :key="index"
                      >
                        {{ item.table_nm }}
                      </option>
                    </select>
                  </div>
                </td>
              </tr>

              <tr>
                <th>스키마명*</th>
                <td>
                  <div class="flex justify-between">
                    <select
                      name=""
                      id=""
                      class="flex30 workRuleInput"
                      v-model="optionSelect.schema_id"
                      @change="changeSchema()"
                    >
                      <option :value="null">{{ selectSchema }}</option>
                      <option
                        :value="item.schema_id"
                        v-for="(item, index) in schemaList"
                        :key="index"
                      >
                        {{ item.schema_name }}
                      </option>
                    </select>
                  </div>
                </td>
                <th>컬럼명*</th>
                <td>
                  <div class="flex justify-between">
                    <select
                      name=""
                      id=""
                      class="flex30 workRuleInput"
                      v-model="wrManagement.column_id"
                    >
                      <option :value="null">{{ selectColumn }}</option>
                      <option
                        :value="item.column_id"
                        v-for="(item, index) in columnList"
                        :key="index"
                      >
                        {{ item.column_nm }}
                      </option>
                    </select>
                  </div>
                </td>
              </tr>

              <tr>
                <th>설명*</th>
                <td colspan="3">
                  <input
                    type="text"
                    name=""
                    id=""
                    placeholder="작성해주세요"
                    v-model.trim="wrManagement.work_rule_explain"
                    style="width: 44%"
                  />
                </td>
              </tr>

              <tr>
                <th>건수 SQL*</th>
                <td rowspan="4" style="display: flex">
                  <textarea
                    name=""
                    id="input_text"
                    v-model.trim="wrManagement.work_rule_cnt"
                    class="workRuleInput"
                  ></textarea>
                </td>
                <th>분석 SQL*</th>
                <td rowspan="4" style="display: flex">
                  <textarea
                    name=""
                    id="input_text"
                    v-model.trim="wrManagement.work_rule_sql"
                    class="workRuleInput"
                  ></textarea>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="flex justify-center">
          <button
            class="blue-btn small-btn"
            v-if="buttonName == '등록'"
            v-on:click="register()"
          >
            등록
          </button>
          <!-- 값이 있을때 수정 버튼으로 변경 -->
          <button
            class="blue-btn small-btn"
            v-if="buttonName == '수정'"
            v-on:click="update()"
          >
            수정
          </button>
          <button class="blue-btn small-btn" @click="reset()">초기화</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import PageNavigation from "../../component/PageNavigation.vue";
import PaginationButton from "../../component/PaginationButton.vue";
import SvgIcon from "@jamescoyle/vue-icon";
import { mdiMagnify } from "@mdi/js";
export default {
  data() {
    return {
      // 모달 알림창
      isSelectModalOpen: false, // 취소 확인 선택가능한 모달창
      activeTab: "tab1",
      modalType: "tab-modal",
      modalTitle: null, // 모달창 제목
      modalContents: null, // 모달창 내용

      // 수행실패(-1), 수행완료(0), 등록(1), 수정(2), 삭제(3)인지 확인
      menu: null,

      //체크박스를 선택하였을 때 업무규칙관리 id를 저장하는 곳
      selectedItems: [],
      // 체크박스를 선택하였을 떄 목록들이 보여지는 행의 인덱스를 저장
      checkboxList: [],

      searchPath: mdiMagnify,
      inputValue: null,

      buttonName: "등록", // 버튼값 변경 등록-->수정, 수정-->등록

      // DBMS명 목록
      dbmsList: [],

      //스키마명 목록
      schemaList: [],

      //테이블명 목록
      tableList: [],

      // 컬럼명 목록
      columnList: [],

      // 품질지표명 목록
      dqiList: [
        "시간순서 일관성",
        "선후관계 정확성",
        "논리관계 일관성",
        "계산식",
        "참조관계",
      ],

      // 업무 규칙 정보
      wrManagement: {
        work_rule_nm: null, // 업무규칙명
        work_rule_explain: null, // 설명
        work_rule_cnt:
          "SELECT COUNT(*) AS CNT\nFROM 스키마명.테이블명\nWHERE 총_건수_조건식", // 건수 SQL
        work_rule_sql:
          "SELECT 컬럼명1, 컬럼명2, 컬럼명_여러개_입력\nFROM 스키마명.테이블명\nWHERE 오류_조건식", // 분석 SQL
        dqi_nm: null, // 품질지표명
        column_id: null, // 컬럼명
        user_id: $cookies.get("USER_ID"),
      },

      // 검색조건 DBMS명, 스키마명
      searchData: {
        dbms_id: null,
        schema_id: null,
        user_id: $cookies.get("USER_ID"),
        currentPage: 1,
        perPage: 8,
      },
      wrListCount: 0,
      wrIndex: 0,

      // 옵션 선택
      optionSelect: {
        schema_id: null,
        table_id: null,
        dbms_id: null,
      },

      // 상세정보 조회/추가/변경에서 사용
      selectTable: "선택 불가",
      selectColumn: "선택 불가",
      selectSchema: "선택 불가",

      //검색 조건에서 사용
      searchSchema: "선택 불가",
      searchSchemaList: [],

      // 업무 규칙 목록
      workRuleList: [],

      // 검색 버튼을 클릭시 1페이지에서 시작하도록 설정하기 위해 사용(true이면 무조건 1페이지부터, false면 해당 페이지)
      searchButton: true, // 검색 버튼을 클릭하면 true, 검색버튼이 아닌 추가 하거나 삭제 할때는 false

      selectedItemIndex: null, // 선택된 항목의 인덱스
    };
  },
  methods: {
    // 검색버튼 클릭시 현재페이지를 1페이지부터 보여줄수있도록 설정
    updateSearchButton() {
      this.searchButton = true;
    },
    //체크박스를 선택하였을 경우
    checkHandle(wr_id) {
      const selectedIndex = this.selectedItems.indexOf(wr_id);

      if (selectedIndex !== -1) {
        // 이미 선택된 경우 배열에서 제거
        this.selectedItems.splice(selectedIndex, 1);
      } else {
        // 선택되지 않은 경우 배열에 추가
        this.selectedItems.push(wr_id);
      }
    },

    // 모달창에서 취소버튼을 클릭했을 경우
    closeCancelModal() {
      const vm = this;
      vm.isSelectModalOpen = false;
      vm.isCheckModalOpen = false;
      vm.modalTitle = null;
      vm.modalContents = null;
      return;
    },

    // 모달창에서 확인을 클릭했을 경우
    closeCheckModal() {
      const vm = this;

      // 수행완료(0)
      if (vm.menu == 0) {
        vm.isSelectModalOpen = false;
        vm.isCheckModalOpen = false;
        vm.modalTitle = null;
        vm.modalContents = null;
      }

      // 등록(1)
      if (vm.menu == 1) {
        vm.isSelectModalOpen = false;
        axios({
          url: "/insertWorkRule.json",
          method: "post",
          headers: { "Content-Type": "application/json; charset=UTF-8" },
          data: vm.wrManagement,
        })
          .then(function (response) {
            vm.menu = 0;
            if (response.data > 0) {
              vm.$showAlert("등록 성공", "성공적으로 등록하였습니다.");
              vm.reset();
              vm.allWorkRuleList();
            } else {
              vm.$showAlert(
                "등록 실패",
                "등록 실패하였습니다. 건수 SQL과 분석 SQL을 다시 확인해주세요."
              );
            }
          })
          .catch(function (error) {
            vm.menu = 0;
            vm.$showAlert("error", "등록에 오류가 발생하였습니다.");
          });
      }

      // 수정(2)
      if (vm.menu == 2) {
        vm.isSelectModalOpen = false;
        axios({
          url: "/updateWorkRule.json",
          method: "post",
          headers: { "Content-Type": "application/json; charset=UTF-8" },
          data: vm.wrManagement,
        })
          .then(function (response) {
            vm.menu = 0;
            if (response.data > 0) {
              vm.$showAlert("수정 성공", "성공적으로 수정하였습니다.");
              vm.reset();
              vm.allWorkRuleList();
            } else {
              vm.$showAlert(
                "수정 실패",
                "수정 실패하였습니다.\n건수 SQL과 분석 SQL을 다시 확인해주세요."
              );
            }
          })
          .catch(function (error) {
            vm.menu = 0;
            vm.$showAlert("error", "수정에 오류가 발생하였습니다.");
          });
      }

      // 삭제(3)
      if (vm.menu == 3) {
        vm.isSelectModalOpen = false;
        axios({
          url: "/deleteWorkRule.json",
          method: "post",
          headers: { "Content-Type": "application/json; charset=UTF-8" },
          data: vm.selectedItems,
        })
          .then(function (response) {
            vm.menu = 0;
            if (response.data > 0) {
              vm.$showAlert(
                "삭제 성공",
                "성공적으로 " + vm.selectedItems.length + "건을 삭제하였습니다."
              );
              vm.searchData.currentPage = 1;
              vm.allWorkRuleList();
              vm.reset();
            } else {
              vm.$showAlert("삭제 실패", "삭제를 실패하였습니다.");
            }
          })
          .catch(function (error) {
            vm.menu = 0;
            vm.$showAlert("error", "삭제에 오류가 발생하였습니다.");
          });
      }
    },

    // 업무규칙 등록
    register() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      let result = vm.inputCheck();
      if (result == 0) return;

      vm.menu = 1;
      vm.modalTitle = "등록";
      vm.modalContents = "작성하신 업무규칙 정보를 등록하시겠습니까?";
      vm.isCheckModalOpen = false;
      vm.isSelectModalOpen = true;
    },

    // 업무규칙 수정
    update() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      vm.inputCheck();
      vm.menu = 2;
      vm.modalTitle = "수정";
      vm.modalContents = "업무규칙 정보를 수정하시겠습니까?";
      vm.isSelectModalOpen = true;
    },

    // 업무규칙 삭제
    selectWRDelete() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      if (vm.selectedItems.length == 0) {
        vm.menu = 0;
        vm.$showAlert("알림", "삭제하실 목록을 체크하여 주시기 바랍니다.");
        return;
      }
      vm.menu = 3;
      vm.modalTitle = "삭제";
      vm.modalContents =
        "선택하신 " + vm.selectedItems.length + "건을 삭제 하시겠습니까?";
      vm.isSelectModalOpen = true;
    },

    // 검색조건에서 DBMS명을 선택했을 경우
    searchSelectDbms() {
      const vm = this;
      vm.searchButton = true;
      if (vm.searchData.dbms_id == null) {
        vm.searchSchema = "선택 불가";
        vm.searchData.schema_id = null;
        vm.searchSchemaList = [];
        return false;
      }

      axios({
        url: "/getSchemaList.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: vm.searchData,
      })
        .then(function (response) {
          vm.searchSchemaList = response.data;
          vm.searchSchema = "선택";
        })
        .catch(function (error) {
          vm.menu = 0;
          vm.$showAlert(
            "error",
            "검색조건에서 스키마명을 불러오는데 오류가 발생하였습니다."
          );
        });
    },

    // 초기화
    reset() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      vm.wrManagement = {
        work_rule_nm: null, // 업무규칙명
        work_rule_explain: null, // 설명
        work_rule_cnt:
          "SELECT COUNT(*) AS CNT\nFROM 스키마명.테이블명\nWHERE 총_건수_조건식", // 건수 SQL
        work_rule_sql:
          "SELECT 컬럼명1, 컬럼명2, 컬럼명_여러개_입력\nFROM 스키마명.테이블명\nWHERE 오류_조건식", // 분석 SQL
        dqi_nm: null, // 품질지표명
        column_id: null, // 컬럼명
        user_id: $cookies.get("USER_ID"),
      };
      vm.optionSelect = {
        schema_id: null,
        table_id: null,
        dbms_id: null,
      };
      vm.selectTable = "선택 불가";
      vm.selectColumn = "선택 불가";
      vm.selectSchema = "선택 불가";
      vm.schemaList = [];
      vm.tableList = [];
      vm.columnList = [];
      vm.buttonName = "등록";
      vm.checkboxList = [];
      vm.selectedItems = [];
      vm.selectedItemIndex = null;
    },

    // dbms명 목록 불러오기
    getDbmsList() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      axios({
        url: "/getDbmsList.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: vm.wrManagement,
      })
        .then(function (response) {
          vm.dbmsList = response.data;
        })
        .catch(function (error) {
          vm.menu = 0;
          vm.$showAlert("error", "DBMS명을 불러오는데 오류가 발생하였습니다.");
        });
    },

    // 상세정보 조회/추가/변경 dbms명에서 값을 선택하였을 경우
    changeDbmsNM() {
      const vm = this;
      if (vm.optionSelect.dbms_id == null) {
        vm.selectTable = "선택 불가";
        vm.selectColumn = "선택 불가";
        vm.selectSchema = "선택 불가";
        vm.schemaList = [];
        vm.tableList = [];
        vm.columnList = [];
        return false;
      }

      axios({
        url: "/getSchemaList.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: vm.optionSelect,
      })
        .then(function (response) {
          vm.schemaList = response.data;
          vm.selectSchema = "선택";
          vm.selectTable = "선택 불가";
          vm.selectColumn = "선택 불가";
        })
        .catch(function (error) {
          vm.menu = 0;
          vm.$showAlert(
            "error",
            "스키마명을 불러오는데 오류가 발생하였습니다."
          );
        });
    },

    // 상세정보 조회/추가/변경  스키마명에서 값을 선택했을 경우
    changeSchema() {
      const vm = this;
      if (vm.optionSelect.schema_id == null) {
        vm.selectColumn = "선택 불가";
        vm.selectTable = "선택 불가";
        vm.tableList = [];
        vm.columnList = [];
        return false;
      }

      let schemaid = {
        schema_id: vm.optionSelect.schema_id,
      };

      axios({
        url: "/getTableList.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: schemaid,
      })
        .then(function (response) {
          vm.tableList = response.data;
          vm.selectTable = "선택";
          vm.selectColumn = "선택 불가";
        })
        .catch(function (error) {
          vm.menu = 0;
          vm.$showAlert(
            "error",
            "테이블명을 불러오는데 오류가 발생하였습니다."
          );
        });
    },

    // 상세정보 조회/추가/변경  테이블명에서 값을 선택했을 경우
    changeTable() {
      const vm = this;
      if (vm.optionSelect.table_id == null) {
        vm.selectColumn = "선택 불가";
        vm.columnList = [];
        return false;
      }

      let tableid = {
        table_id: vm.optionSelect.table_id,
      };

      axios({
        url: "/getColumnList.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: tableid,
      })
        .then(function (response) {
          vm.columnList = response.data;
          vm.selectColumn = "선택";
        })
        .catch(function (error) {
          vm.menu = 0;
          vm.$showAlert("error", "컬럼명을 불러오는데 오류가 발생하였습니다.");
        });
    },

    // 업무 규칙에 입력칸들이 비어있는지 검사
    inputCheck() {
      const vm = this;
      vm.menu = 0;
      if (
        vm.wrManagement.work_rule_nm == null ||
        vm.wrManagement.work_rule_nm == ""
      ) {
        vm.$showAlert("알림", "업무규칙명을 입력해주시기 바랍니다.");
        return 0;
      }
      if (vm.wrManagement.dqi_nm == null) {
        vm.$showAlert("알림", "품질지표명을 선택해주시기 바랍니다.");
        return 0;
      }
      if (vm.optionSelect.dbms_id == null) {
        vm.$showAlert("알림", "DBMS명을 선택해주시기 바랍니다.");
        return 0;
      }
      if (vm.optionSelect.schema_id == null) {
        vm.$showAlert("알림", "스키마명을 선택해주시기 바랍니다.");
        return 0;
      }
      if (vm.optionSelect.table_id == null) {
        vm.$showAlert("알림", "테이블명을 선택해주시기 바랍니다.");
        return 0;
      }
      if (vm.wrManagement.column_id == null) {
        vm.$showAlert("알림", "컬럼명을 선택해주시기 바랍니다.");
        return 0;
      }
      if (
        vm.wrManagement.work_rule_explain == null ||
        vm.wrManagement.work_rule_explain == ""
      ) {
        vm.$showAlert("알림", "설명을 입력해주시기 바랍니다.");
        return 0;
      }
      if (
        vm.wrManagement.work_rule_cnt == null ||
        vm.wrManagement.work_rule_cnt == ""
      ) {
        vm.$showAlert("알림", "건수 SQL을 입력해주시기 바랍니다.");
        return 0;
      }
      if (
        vm.wrManagement.work_rule_sql == null ||
        vm.wrManagement.work_rule_sql == ""
      ) {
        vm.$showAlert("알림", "분석 SQL을 입력해주시기 바랍니다.");
        return 0;
      }
      return 1;
    },

    // 저장된 목록들 조회
    allWorkRuleList() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      if (vm.searchButton) {
        vm.searchData.currentPage = 1;
      }
      axios({
        url: "/allWorkRuleList.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: vm.searchData,
      })
        .then(function (response) {
          vm.workRuleList = response.data.workRuleList;
          vm.wrListCount = response.data.wrListCount;
          vm.wrIndex =
            vm.wrListCount -
            (vm.searchData.currentPage - -1) * vm.searchData.perPage;
        })
        .catch(function (error) {
          vm.menu = 0;
          vm.$showAlert("error", "목록들을 불러오는데 오류가 발생하였습니다.");
        });
    },

    // 목록들중에서 하나를 선택했을 경우
    oneSelectWorkRule(item, index) {
      const vm = this;
      vm.buttonName = "수정";
      vm.getSelectList(item);
      vm.selectedItemIndex = index;
      vm.wrManagement = {
        work_rule_nm: item.work_rule_name, // 업무규칙명
        work_rule_explain: item.work_rule_explain, // 설명
        work_rule_cnt: item.work_rule_cnt, // 건수 SQL
        work_rule_sql: item.work_rule_sql, // 분석 SQL
        dqi_nm: item.dqi_name, // 품질지표명
        column_id: item.column_id,
        work_rule_id: item.work_rule_id,
      };
      vm.optionSelect = {
        schema_id: item.schema_id,
        table_id: item.table_id,
        dbms_id: item.dbms_id,
      };
    },

    // 목록들중에서 하나를 선택했을 경우에 dbms 선택 목록, schema 선택 목록, table 선택 목록, column 선택 목록을 불러옴
    getSelectList(item) {
      const vm = this;
      let idData = {
        dbms_id: item.dbms_id,
        schema_id: item.schema_id,
        table_id: item.table_id,
      };
      axios({
        url: "/getSelectList.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: idData,
      })
        .then(function (response) {
          vm.schemaList = response.data.schemaList;
          vm.tableList = response.data.tableList;
          vm.columnList = response.data.columnList;
          vm.selectSchema = "선택";
          vm.selectTable = "선택";
          vm.selectColumn = "선택";
        })
        .catch(function (error) {
          vm.menu = 0;
          vm.$showAlert(
            "error",
            "dbms명, 스키마명, 테이블명, 컬럼명을 불러오는데 오류가 발생하였습니다."
          );
        });
    },

    // 페이지 이동시 체크박스 상태 해제
    resetCheckBoxState() {
      this.selectedItems = [];
      this.checkboxList = [];
    },

    //페이지네이션 클릭 이벤트
    handleClick() {},

    isSelected(index) {
      return index === this.selectedItemIndex;
    },
  },
  watch: {
    "searchData.currentPage": function () {
      this.searchButton = false;
      this.allWorkRuleList();
      this.resetCheckBoxState();
      this.selectedItemIndex = null;
    },
  },
  computed: {
    PaginationButton: PaginationButton,
  },
  components: {
    PageNavigation: PageNavigation,
    PaginationButton: PaginationButton,
    SvgIcon: SvgIcon,
  },
  mounted() {
    this.getDbmsList();
    this.allWorkRuleList();
  },
};
</script>

<style scoped>
#input_text {
  width: 973px;
  height: 196px;
  padding: 5px 10px;
  resize: none;
  outline: none;
  border: none;
  border-radius: 5px;
}
.workRuleInput {
  width: 100%;
  min-width: -webkit-fill-available;
}

.workRuleTable > .selected {
  background-color: #c6feff;
}
</style>
