<template>
  <!-- 검색 모달창 -->
  <div v-show="isModalOpen" class="modal-wrapper">
    <div class="modal-container" style="height: 60%">
      <div class="modal-title">
        <div class="flex justify-between align-center">
          <h2>진단 규칙 검색</h2>
          <button class="close-btn" @click="closeModal">X</button>
        </div>
      </div>
      <div class="modal-content-monthly" v-show="modalType == 'tab-modal'">
        <ul class="tab-nav flex justify-between">
          <li @click="showTab('tab1')" class="flex50">
            <a href="#tab01" :class="{ activeTab: activeTab === 'tab1' }"
              >검증룰</a
            >
          </li>
          <li @click="showTab('tab2')" class="flex50">
            <a href="#tab02" :class="{ activeTab: activeTab === 'tab2' }"
              >코드</a
            >
          </li>
        </ul>
        <ul class="tab-content">
          <li id="tab01" v-show="activeTab === 'tab1'">
            <table class="form-table">
              <colgroup>
                <col style="width: 10%" />
                <col style="width: 20%" />
                <col style="width: 10%" />
                <col style="width: 60%" />
              </colgroup>
              <tbody>
                <tr>
                  <th>검증유형</th>
                  <td>
                    <select
                      class="square-select"
                      v-model="valRuleSearch.val_rule_type"
                      style="width: 100%"
                      @change="updateSearchButton()"
                    >
                      <option value="전체">전체</option>
                      <option
                        v-for="item in dqiTypeList"
                        :key="item"
                        :value="item"
                      >
                        {{ item }}
                      </option>
                    </select>
                  </td>
                  <th>검증룰명</th>
                  <td style="position: relative">
                    <input
                      type="text"
                      v-model="valRuleSearch.val_rule_nm"
                      class="square-input"
                      style="width: 100%; margin-left: auto"
                    />
                    <button
                      style="position: absolute"
                      @click="selectValRule"
                      class="square-button"
                    >
                      <svg-icon
                        type="mdi"
                        :path="searchPath"
                        class="square-icon"
                      ></svg-icon>
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
            <table class="list-table">
              <colgroup>
                <col style="width: 5%" />
                <col style="width: 10%" />
                <col style="width: 25%" />
                <col style="width: 25%" />
                <col style="width: 35%" />
              </colgroup>
              <thead>
                <tr>
                  <th>No</th>
                  <th>선택</th>
                  <th>검증룰명</th>
                  <th>검증유형</th>
                  <th>검증룰</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in valRuleList" :key="index">
                  <td>
                    {{
                      index +
                      1 +
                      (valRuleSearch.currentPage - 1) * valRuleSearch.perPage
                    }}
                  </td>
                  <td>
                    <input
                      type="checkbox"
                      :checked="isSelected(index)"
                      @click="handleClick(index)"
                    />
                  </td>
                  <td>{{ item.val_rule_nm }}</td>
                  <td>{{ item.val_rule_type }}</td>
                  <td>{{ item.val_rule_detail }}</td>
                </tr>
              </tbody>
            </table>
            <PaginationButton
              v-model:currentPage="valRuleSearch.currentPage"
              :perPage="valRuleSearch.perPage"
              :totalCount="valRuleListCount"
              :maxRange="5"
              :click="handlePageClick"
            />
          </li>
          <li id="tab02" v-show="activeTab === 'tab2'">
            <table class="form-table">
              <colgroup>
                <col style="width: 10%" />
                <col style="width: 20%" />
                <col style="width: 10%" />
                <col style="width: 60%" />
              </colgroup>
              <tbody>
                <tr>
                  <th>코드유형</th>
                  <td>
                    <select
                      name=""
                      id=""
                      class="square-select"
                      v-model="cmListSearch.code_type"
                      @change="updateSearchButton()"
                    >
                      <option :value="null">선택</option>
                      <option value="공통코드">공통코드</option>
                      <option value="목록성코드">목록성코드</option>
                    </select>
                  </td>
                  <th>코드명</th>
                  <td style="position: relative">
                    <input
                      type="text"
                      class="square-input"
                      v-model="cmListSearch.code_nm"
                      style="width: 100%; margin-left: auto"
                    />
                    <button
                      style="position: absolute"
                      @click="selectCodeList"
                      class="square-button"
                    >
                      <svg-icon
                        type="mdi"
                        :path="searchPath"
                        class="square-icon"
                      ></svg-icon>
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
            <table class="list-table">
              <colgroup>
                <col style="width: 5%" />
                <col style="width: 10%" />
                <col style="width: 25%" />
                <col style="width: 25%" />
                <col style="width: 35%" />
              </colgroup>
              <thead>
                <tr>
                  <th>No</th>
                  <th>선택</th>
                  <th>코드명</th>
                  <th>코드유형</th>
                  <th>코드 생성 SQL</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in codeList" :key="index">
                  <td>
                    {{
                      index +
                      1 +
                      (cmListSearch.currentPage - 1) * cmListSearch.perPage
                    }}
                  </td>
                  <td>
                    <input
                      type="checkbox"
                      :checked="isSelected(index)"
                      @click="handleClick(index)"
                    />
                  </td>
                  <td>{{ item.cr_code_nm }}</td>
                  <td>{{ item.cr_code_type }}</td>
                  <td
                    style="
                      overflow: hidden;
                      text-overflow: ellipsis;
                      white-space: nowrap;
                    "
                  >
                    {{ item.cr_code_sql }}
                  </td>
                </tr>
              </tbody>
            </table>
            <PaginationButton
              v-model:currentPage="cmListSearch.currentPage"
              :perPage="cmListSearch.perPage"
              :totalCount="codeListCount"
              :maxRange="5"
              :click="handlePageClick"
            />
          </li>
        </ul>
      </div>
      <div class="modal-end flex justify-end">
        <button
          v-show="activeTab === 'tab1'"
          class="blue-btn small-btn"
          @click="inputDomainRule('rule')"
        >
          검증룰 적용
        </button>
        <button
          v-show="activeTab === 'tab2'"
          class="blue-btn small-btn"
          @click="inputDomainRule('code')"
        >
          코드 적용
        </button>
      </div>
    </div>
  </div>
  <div class="container">
    <div class="page-titleZone flex justify-between">
      <p class="main-title">진단규칙설정 > 도메인 진단 규칙 관리</p>
      <PageNavigation />
    </div>
    <div class="content">
      <div class="row">
        <div class="box" style="margin-bottom: 10px">
          <div class="content-titleZone">
            <p class="box-title">>검색조건</p>
          </div>
          <div class="table-zone">
            <table class="form-table">
              <colgroup>
                <col style="width: " />
                <col style="width: " />
                <col style="width: " />
                <col style="width: " />
              </colgroup>
              <tbody>
                <tr>
                  <th>DBMS명</th>
                  <td>
                    <select
                      class="square-select domainInput"
                      v-model="selectDbms"
                    >
                      <option value="" disabled selected>-- DBMS명 --</option>
                      <option
                        v-for="db in dbNameList"
                        :key="db.dbms_id"
                        :value="{
                          dbms_id: db.dbms_id,
                          dbms_name: db.dbms_name,
                        }"
                      >
                        {{ db.dbms_name }}
                      </option>
                    </select>
                  </td>
                  <th>스키마명</th>
                  <td>
                    <select
                      class="square-select domainInput"
                      v-model="selectSchema"
                      @change="updateSearchButton()"
                    >
                      <option value="" disabled selected>-- 스키마명 --</option>
                      <option
                        v-for="sc in schemaNameList"
                        :key="sc.schema_id"
                        :value="{
                          schema_id: sc.schema_id,
                          schema_name: sc.schema_name,
                        }"
                      >
                        {{ sc.schema_name }}
                      </option>
                    </select>
                  </td>
                </tr>
                <tr>
                  <th>테이블명</th>
                  <td>
                    <input
                      type="text"
                      v-model="domainSearch.table_nm"
                      class="domainInput"
                    />
                  </td>
                  <th>컬럼명</th>
                  <td>
                    <input
                      type="text"
                      v-model="domainSearch.column_nm"
                      class="domainInput"
                    />
                  </td>
                </tr>
              </tbody>
            </table>
            <button class="blue-btn small-btn" @click="setPage()">검색</button>
          </div>
        </div>
        <div class="content-titleZone">
          <p class="box-title">>진단규칙 일괄 지정</p>
        </div>
        <div class="searchbar-zone">
          <div class="flex justify-start align-center">
            <span class="option-title">진단규칙</span>
            <div class="search-square">
              <input
                type="text"
                class="square-input"
                v-model="selectedInput"
                placeholder="진단 규칙 검색"
                disabled
              />
              <button @click="openModal(-1)" class="square-button">
                <svg-icon
                  type="mdi"
                  :path="searchPath"
                  class="square-icon"
                ></svg-icon>
              </button>
            </div>
            <button class="blue-btn variable-btn" @click="inputAllDomainRule">
              진단규칙 일괄적용
            </button>
          </div>
        </div>
        <div class="table-zone">
          <div class="flex justify-start">
            <button class="blue-btn small-btn" @click="saveDomainRule">
              선택항목 저장
            </button>
            <button class="blue-btn small-btn" @click="deleteDomainRule">
              진단규칙 초기화
            </button>
          </div>
          <table class="list-table">
            <colgroup>
              <col style="width: 5%" />
              <col style="width: 5%" />
              <col style="width: 10%" />
              <col style="width: 10%" />
              <col style="width: 10%" />
              <col style="width: 10%" />
              <col style="width: 20%" />
              <col style="width: 30%" />
            </colgroup>
            <thead>
              <tr>
                <th>No</th>
                <th>선택</th>
                <th>DBMS명</th>
                <th>스키마명</th>
                <th>테이블명</th>
                <th>컬럼명</th>
                <th>컬럼타입</th>
                <th>진단규칙</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in domainList" :key="index">
                <td>
                  {{
                    index +
                    1 +
                    (domainSearch.currentPage - 1) * domainSearch.perPage
                  }}
                </td>
                <td>
                  <input type="checkbox" v-model="checkedDomain[index]" />
                </td>
                <td :title="item.dbms_name">{{ item.dbms_name }}</td>
                <td :title="item.schema_nm">{{ item.schema_nm }}</td>
                <td :title="item.table_nm">{{ item.table_nm }}</td>
                <td :title="item.column_nm">{{ item.column_nm }}</td>
                <td :title="item.column_type">{{ item.column_type }}</td>
                <td style="position: relative">
                  <span v-if="item.code_id != null">{{ item.code_nm }}</span>
                  <span v-if="item.val_rule_id != null">{{
                    item.val_rule_nm
                  }}</span>
                  <button
                    style="position: absolute"
                    @click="openModal(index)"
                    class="square-button"
                  >
                    <svg-icon
                      type="mdi"
                      :path="searchPath"
                      class="square-icon"
                    ></svg-icon>
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <PaginationButton
          v-model:currentPage="domainSearch.currentPage"
          :perPage="domainSearch.perPage"
          :totalCount="listCount"
          :maxRange="10"
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
      isModalOpen: false,
      activeTab: "tab1",
      modalType: "tab-modal",
      searchPath: mdiMagnify,
      user_id: $cookies.get("USER_ID"),

      // select한 도메인 진단 규칙
      domainList: [],
      domainSearch: {
        currentPage: 1,
        perPage: 15,
        user_id: $cookies.get("USER_ID"),
        dbmsType: "all",
        searchText: null,
        dbms_name: null,
        schema_name: null,
        table_nm: null,
        column_nm: null,
      },
      // update할 도메인 진단 규칙
      checkedDomain: [],
      domainUpdate: {
        rule_id: null,
        code_id: null,
        val_rule_id: null,
        column_id: null,
        user_id: null,
      },

      listCount: 0,

      // select한 검증룰
      valRuleList: [],
      valRuleListCount: 0,
      dqiTypeList: [],

      valRuleSearch: {
        dqi_nm: "전체",
        val_rule_type: "전체",
        val_rule_nm: null,
        currentPage: 1,
        perPage: 10,
        user_id: $cookies.get("USER_ID"),
      },

      // select한 코드
      codeList: [],
      codeListCount: 0,
      cmListSearch: {
        dbms_id: null,
        code_type: null,
        code_nm: null,
        currentPage: 1,
        perPage: 10,
        user_id: $cookies.get("USER_ID"),
      },

      // 모달 검색창에서 선택된 것
      selectedCheck: null,

      // 진단 규칙 선택한 행
      selectedDomain: null,

      // 진단규칙 일괄 지정용
      selectedInput: null,
      selectedAllDomain: {
        val_rule_id: null,
        val_rule_nm: null,
        code_id: null,
        code_nm: null,
      },

      // 검색용
      dbNameList: [],
      selectDbms: "",

      schemaNameList: [],
      selectSchema: "",

      // 검색 버튼을 클릭시 1페이지에서 시작하도록 설정하기 위해 사용(true이면 무조건 1페이지부터, false면 해당 페이지)
      searchButton: true, // 검색 버튼을 클릭하면 true, 검색버튼이 아닌 추가 하거나 삭제 할때는 false
    };
  },
  methods: {
    // 검색버튼 클릭시 현재페이지를 1페이지부터 보여줄수있도록 설정
    updateSearchButton() {
      this.searchButton = true;
    },
    // 탭 변경
    showTab: function (tabName) {
      this.activeTab = tabName;
      this.selectedCheck = null;
    },
    openModal: function (index) {
      if (this.$checkLogin() == true) {
        return;
      }
      this.isModalOpen = true;
      this.selectedDomain = index;
    },
    closeModal: function () {
      this.updateSearchButton();
      this.selectValRule();
      this.selectCodeList();
      this.selectedCheck = null;
      this.isModalOpen = false;
    },
    isSelected(index) {
      // 현재 인덱스가 선택된 인덱스인지 확인
      return this.selectedCheck === index;
    },
    handleClick(index) {
      // 체크박스 클릭 시 실행되는 메서드
      if (this.selectedCheck === index) {
        this.seleselectedCheckctedDb = null;
      } else {
        this.selectedCheck = index;
      }
    },
    // 모달창에서 선택한 진단 규칙을 페이지에 적용시키기
    inputDomainRule: function (ruleName) {
      const index = this.selectedCheck;
      this.selectedCheck = [];
      const domainIndex = this.selectedDomain;
      // 선택한 진단 규칙이 없을때
      if (!this.valRuleList[index]) {
        this.$showAlert("error", "진단규칙을 선택해주세요");
        return;
      }
      if (domainIndex == -1) {
        if (ruleName == "rule") {
          this.selectedInput = this.valRuleList[index].val_rule_nm;
          this.selectedAllDomain.val_rule_id =
            this.valRuleList[index].val_rule_id;
          this.selectedAllDomain.val_rule_nm =
            this.valRuleList[index].val_rule_nm;
          this.selectedAllDomain.code_id = null;
          this.selectedAllDomain.code_nm = null;
        } else if (ruleName == "code") {
          this.selectedInput = this.codeList[index].cr_code_nm;
          this.selectedAllDomain.code_id = this.codeList[index].cr_code_id;
          this.selectedAllDomain.code_nm = this.codeList[index].cr_code_nm;
          this.selectedAllDomain.val_rule_id = null;
          this.selectedAllDomain.val_rule_nm = null;
        }
      } else {
        if (ruleName == "rule") {
          this.domainList[domainIndex].val_rule_id =
            this.valRuleList[index].val_rule_id;
          this.domainList[domainIndex].val_rule_nm =
            this.valRuleList[index].val_rule_nm;
          this.domainList[domainIndex].code_id = null;
          this.domainList[domainIndex].code_nm = null;
        } else if (ruleName == "code") {
          this.domainList[domainIndex].code_id =
            this.codeList[index].cr_code_id;
          this.domainList[domainIndex].code_nm =
            this.codeList[index].cr_code_nm;
          this.domainList[domainIndex].val_rule_id = null;
          this.domainList[domainIndex].val_rule_nm = null;
        }
      }
      this.closeModal();
    },
    // 진단규칙 일괄적용
    inputAllDomainRule: function () {
      if (this.$checkLogin() == true) {
        return;
      }
      for (let i = 0; i < this.domainList.length; i++) {
        if (this.checkedDomain[i] == true) {
          this.domainList[i].val_rule_id = this.selectedAllDomain.val_rule_id;
          this.domainList[i].val_rule_nm = this.selectedAllDomain.val_rule_nm;
          this.domainList[i].code_id = this.selectedAllDomain.code_id;
          this.domainList[i].code_nm = this.selectedAllDomain.code_nm;
        }
      }
    },
    // 진단 규칙 초기화
    deleteDomainRule() {
      if (this.$checkLogin() == true) {
        return;
      }
      for (let i = 0; i < this.domainList.length; i++) {
        if (this.checkedDomain[i] == true) {
          this.domainList[i].val_rule_id = null;
          this.domainList[i].val_rule_nm = null;
          this.domainList[i].code_id = null;
          this.domainList[i].code_nm = null;
        }
      }
    },
    // 진단 규칙 선택 항목 저장
    saveDomainRule() {
      if (this.$checkLogin() == true) {
        return;
      }
      for (let i = 0; i < this.domainList.length; i++) {
        if (this.checkedDomain[i] == true) {
          this.domainUpdate = {
            rule_id: this.domainList[i].rule_id,
            code_id: this.domainList[i].code_id,
            val_rule_id: this.domainList[i].val_rule_id,
            column_id: this.domainList[i].column_id,
            user_id: $cookies.get("USER_ID"),
          };
          this.updateDomainRule();
        }
      }
      this.$showAlert("확인", "선택한 컬럼에 맞춰 규칙이 저장되었습니다");
      this.checkedDomain = [];
    },
    // 진단 규칙 update
    updateDomainRule() {
      const vm = this;
      axios({
        url: "updateDomainRule.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.domainUpdate,
      })
        .then(function (response) {
          vm.selectDomainRule();
        })
        .catch(function (error) {
          vm.$showAlert("error", "updateDomainRule 오류");
        });
    },
    // 도메인 진단 규칙 select
    selectDomainRule: function () {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      if (vm.searchButton) {
        vm.domainSearch.currentPage = 1;
      }
      vm.domainSearch.dbms_name = vm.selectDbms.dbms_name;
      vm.domainSearch.schema_name = vm.selectSchema.schema_name;
      axios({
        url: "selectDomainRule.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.domainSearch,
      })
        .then(function (response) {
          vm.domainList = response.data.list;
          vm.listCount = response.data.listCount;
        })
        .catch(function (error) {
          this.$showAlert("error", "selectDomainRule 오류");
        });
    },
    // 진단 규칙 select
    selectValRule: function () {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      if (vm.searchButton) {
        vm.valRuleSearch.currentPage = 1;
      }
      axios({
        url: "selectValRule.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: vm.valRuleSearch,
      })
        .then(function (response) {
          vm.valRuleList = response.data.valRuleList;
          vm.valRuleListCount = response.data.count;
        })
        .catch(function (error) {
          this.$showAlert("error", "selectValRule 오류");
        });
    },
    // 검증 유형 select
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
          this.$showAlert("error", "selectDqiType 오류");
        });
    },
    // 코드 select
    selectCodeList: function () {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      if (vm.searchButton) {
        vm.cmListSearch.currentPage = 1;
      }
      axios({
        url: "/selectCodeList.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: vm.cmListSearch,
      })
        .then(function (response) {
          vm.codeList = response.data.cmList;
          vm.codeListCount = response.data.cmListCount;
        })
        .catch(function (error) {
          this.$showAlert("error", "selectCodeList 오류");
        });
    },

    // 검색 - DBMS명 조회
    selectDbName() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      axios({
        url: "/selectDbmsName.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: { user_id: vm.user_id },
      })
        .then((res) => {
          vm.dbNameList = res.data;
        })
        .catch((err) => {
          this.$showAlert("error", "selectDbName 오류");
        });
    },

    // 검색 - 스키마명 조회
    selectSchemaName() {
      const vm = this;
      vm.updateSearchButton();
      axios({
        url: "/selectSchemaName.json",
        method: "post",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        data: { dbms_id: vm.selectDbms.dbms_id },
      })
        .then((res) => {
          vm.schemaNameList = res.data;
        })
        .catch((err) => {
          this.$showAlert("error", "selectSchemaName 오류");
        });
    },

    //예외 처리 및 검색 버튼 누를시 페이지 1로 초기화
    setPage: function () {
      if (this.$checkLogin() == true) {
        return;
      }
      if (this.searchButton) {
        this.domainSearch.currentPage = 1;
      }
      if (this.$checkLogin() == true) {
        return;
      }
      if (this.selectDbms === "") {
        this.$showAlert("deny", "DBMS를 선택해주세요.");
        return;
      }
      if (this.selectSchema === "") {
        this.$showAlert("deny", "스키마를 선택해주세요.");
        return;
      }
      this.checkedDomain = [];
      this.selectDomainRule();
    },

    //페이지네이션 클릭 이벤트
    handlePageClick() {},
  },

  watch: {
    "domainSearch.currentPage": function () {
      if (this.$checkLogin() == true) {
        return;
      }
      this.checkedDomain = [];
      this.searchButton = false;
      this.selectDomainRule();
    },
    "valRuleSearch.currentPage": function () {
      this.searchButton = false;
      this.selectedCheck = null;
      this.selectValRule();
    },
    "cmListSearch.currentPage": function () {
      this.search;
      this.searchButton = false;
      this.selectedCheck = null;
      this.selectCodeList();
    },
    selectDbms: function () {
      this.selectSchemaName();
      this.selectSchema = ""; //DBMS명 새로 선택 할 때마다 기존에 선택했던 스키마 초기화
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
    this.selectDomainRule();
    this.selectDqiType();
    this.selectValRule();
    this.selectCodeList();
    this.selectDbName();
  },
};
</script>
<style>
.domainInput {
  width: 100%;
  min-width: -webkit-fill-available;
}
.list-table tbody tr td {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
