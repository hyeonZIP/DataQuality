<template>
  <!-- 참조 무결성 추가 모달창 -->
  <div v-show="isModalOpen" class="modal-wrapper">
    <div class="modal-container" style="height: 83%; width: 80%">
      <div class="modal-title" style="height: 50%">
        <div class="flex justify-between align-center">
          <h2>진단규칙설정 > 데이터 규칙 관리 > 참조 무결성 추가</h2>
          <button class="close-btn" @click="back()">X</button>
        </div>
      </div>
      <div class="modal-content-monthly">
        <h2>> 테이블 검색조건</h2>
        <ul class="tab-content">
          <li style="margin-top: 10px">
            <table class="form-table">
              <colgroup>
                <col style="width: 10%" />
                <col style="width: 20%" />
                <col style="width: 10%" />
                <col style="width: 20%" />
                <col style="width: 10%" />
                <col style="width: 20%" />
              </colgroup>
              <tbody>
                <tr>
                  <th>진단대상 DB</th>
                  <td>{{ searchTableData.dbms_name }}</td>
                  <th>스키마명</th>
                  <td>{{ searchTableData.schema_name }}</td>
                  <th>테이블명</th>
                  <td style="position: relative">
                    <input
                      type="text"
                      class="square-input"
                      style="width: 100%; margin-left: auto"
                      v-model.trim="searchTableData.table_name"
                    />
                    <button
                      style="position: absolute"
                      @click="searchParentTable()"
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
            <table class="list-table" style="margin-top: 10px">
              <thead>
                <tr>
                  <th>No</th>
                  <th>진단대상 DB</th>
                  <th>스키마명</th>
                  <th>테이블명</th>
                  <th>테이블한글명</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  :class="{ 'selected-row': isSelectedRow2(item.table_nm) }"
                  v-for="(item, index) in searchList"
                  :key="index"
                  @click="clickParentList(item.table_nm)"
                >
                  <td>
                    {{
                      index +
                      1 +
                      (searchTableData.currentPage - 1) *
                        searchTableData.perPage
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
              v-model:currentPage="searchTableData.currentPage"
              :perPage="searchTableData.perPage"
              :totalCount="searchTableDataCount"
              :maxRange="5"
              :click="handlePageClick"
            />
          </li>
          <div class="flex justify-between" style="margin-top: 10px">
            <div class="flex50">
              <h2>> 부모테이블 검색조건</h2>
              <table class="list-table" style="margin-top: 10px">
                <colgroup>
                  <col style="width: 15%" />
                  <col style="width: 5%" />
                  <col style="width: " />
                  <col style="width: " />
                </colgroup>
                <thead>
                  <tr>
                    <th>선택</th>
                    <th>No.</th>
                    <th>부모컬럼명</th>
                    <th>부모한글컬럼명</th>
                    <th>PK여부</th>
                    <th>Data type</th>
                    <th>Null 여부</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(item, index) in parentColumnList" :key="index">
                    <td>
                      <select class="text-ct" v-model="parentNumList[index]">
                        <option></option>
                        <option :value="1">1</option>
                        <option :value="2">2</option>
                        <option :value="3">3</option>
                        <option :value="4">4</option>
                        <option :value="5">5</option>
                      </select>
                    </td>
                    <td>{{ index + 1 }}</td>
                    <td :title="item.column_nm">{{ item.column_nm }}</td>
                    <td :title="item.column_nm_k">{{ item.column_nm_k }}</td>
                    <td>{{}}</td>
                    <td :title="item.column_type">{{ item.column_type }}</td>
                    <td :title="item.column_isnull">
                      {{ item.column_isnull }}
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div class="flex50">
              <h2>> 자식테이블 검색조건</h2>
              <table class="list-table" style="margin-top: 10px">
                <colgroup>
                  <col style="width: 15%" />
                  <col style="width: 5%" />
                  <col style="width: " />
                  <col style="width: " />
                </colgroup>
                <thead>
                  <tr>
                    <th>선택</th>
                    <th>No.</th>
                    <th>자식컬럼명</th>
                    <th>자식한글컬럼명</th>
                    <th>PK여부</th>
                    <th>Data type</th>
                    <th>Null 여부</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(item, index) in childTableList" :key="index">
                    <td>
                      <select class="text-ct" v-model="childNumList[index]">
                        <option></option>
                        <option :value="1">1</option>
                        <option :value="2">2</option>
                        <option :value="3">3</option>
                        <option :value="4">4</option>
                        <option :value="5">5</option>
                      </select>
                    </td>
                    <td>{{ index + 1 }}</td>
                    <td :title="item.column_nm">{{ item.column_nm }}</td>
                    <td :title="item.column_nm_k">{{ item.column_nm_k }}</td>
                    <td>{{}}</td>
                    <td :title="item.column_type">{{ item.column_type }}</td>
                    <td :title="item.column_isnull">
                      {{ item.column_isnull }}
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </ul>
      </div>
      <div class="modal-end flex justify-center">
        <button class="blue-btn small-btn" @click="back()">이전</button>
        <button class="blue-btn small-btn" @click="registerRef()">
          참조 무결성 등록
        </button>
      </div>
    </div>
  </div>

  <!-- 데이터 규칙 관리 -->
  <div class="container">
    <div class="page-titleZone flex justify-between">
      <p class="main-title">진단규칙설정 > 데이터 규칙 관리</p>
    </div>
    <div class="content-box flex justify-between">
      <div class="left-content flex45">
        <div class="content-titleZone">
          <p class="box-title">> 검색 조건</p>
        </div>
        <div class="table-zone">
          <table class="form-table">
            <colgroup>
              <col style="width: 10%" />
              <col style="width: 40%" />
              <col style="width: 10%" />
              <col style="width: 40%" />
            </colgroup>
            <tbody class="refRuleManageTable">
              <tr>
                <th>DBMS</th>
                <td>
                  <select
                    v-model="searchData.dbms_name"
                    @change="clickDbmsName()"
                  >
                    <option :value="null">선택</option>
                    <option
                      v-for="(item, index) in dbmsList"
                      :key="index"
                      :value="item.dbms_name"
                    >
                      {{ item.dbms_name }}
                    </option>
                  </select>
                </td>
                <th>스키마</th>
                <td>
                  <select
                    v-model="searchData.schema_name"
                    @change="updateSearchButton()"
                  >
                    <option :value="null">{{ selectSchema }}</option>
                    <option
                      v-for="(item, index) in schemaList"
                      :key="index"
                      :value="item.schema_name"
                    >
                      {{ item.schema_name }}
                    </option>
                  </select>
                </td>
              </tr>
              <tr>
                <th>테이블명</th>
                <td>
                  <input
                    type="text"
                    name=""
                    id=""
                    v-model.trim="searchData.table_name"
                  />
                </td>
                <th>컬럼명</th>
                <td>
                  <input
                    type="text"
                    name=""
                    id=""
                    v-model.trim="searchData.column_name"
                  />
                </td>
              </tr>
            </tbody>
          </table>
          <div class="justify-start">
            <button class="blue-btn small-btn" @click="search()">검색</button>
          </div>
        </div>
        <div class="table-zone" style="margin-top: 50px">
          <table class="list-table">
            <colgroup>
              <col style="width: 10" />
              <col style="width: 20" />
              <col style="width: 30" />
              <col style="width: 20" />
              <col style="width: 20" />
            </colgroup>
            <thead>
              <tr>
                <th>No</th>
                <th>DBMS</th>
                <th>스키마</th>
                <th>테이블</th>
                <th>한글명</th>
              </tr>
            </thead>
            <tbody>
              <tr
                :class="{
                  'selected-row': isSelectedRow(
                    index +
                      1 +
                      (searchData.currentPage - 1) * searchData.perPage
                  ),
                }"
                v-for="(item, index) in searchDataList"
                :key="index"
                @click="
                  oneSelectList(
                    item,
                    index +
                      1 +
                      (searchData.currentPage - 1) * searchData.perPage
                  )
                "
              >
                <td
                  style="
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                  "
                >
                  {{
                    index +
                    1 +
                    (searchData.currentPage - 1) * searchData.perPage
                  }}
                </td>
                <td
                  style="
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                  "
                  :title="item.dbms_name"
                >
                  {{ item.dbms_name }}
                </td>
                <td
                  style="
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                  "
                  :title="item.schema_name"
                >
                  {{ item.schema_name }}
                </td>
                <td
                  style="
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                  "
                  :title="item.table_name"
                >
                  {{ item.table_name }}
                </td>
                <td
                  style="
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                  "
                  :title="item.table_name_k"
                >
                  {{ item.table_name_k }}
                </td>
              </tr>
            </tbody>
          </table>
          <PaginationButton
            v-model:currentPage="searchData.currentPage"
            :perPage="searchData.perPage"
            :totalCount="searchDataListCount"
            :maxRange="5"
            :click="handlePageClick"
          />
        </div>
      </div>

      <div class="right-content flex55">
        <div class="content-titleZone">
          <p class="box-title">> 참조무결성 상세 정보</p>
        </div>
        <div class="table-zone">
          <table class="form-table">
            <colgroup>
              <col style="width: 10%" />
              <col style="width: 25%" />
              <col style="width: 25%" />
              <col style="width: 20%" />
              <col style="width: 20%" />
            </colgroup>
            <tbody>
              <tr>
                <th rowspan="2" style="border-right: 1px solid #aaa">
                  부모<br />테이블
                </th>
                <th>DBMS명</th>
                <td>
                  {{ parentDbmsName }}
                </td>
                <th>스키마</th>
                <td>
                  {{ parentSchemaName }}
                </td>
              </tr>
              <tr>
                <th>테이블명</th>
                <td>{{ parentTableName }}</td>
                <th>테이블<br />한글명</th>
                <td>{{ parentTableNameK }}</td>
              </tr>
            </tbody>
          </table>
        </div>

        <div class="table-zone" style="margin-top: 60px">
          <div class="content-titleZone">
            <p class="box-title">> 참조무결성 관계컬럼 상세 정보</p>
          </div>
          <table class="list-table">
            <colgroup>
              <col style="width: 7%" />
              <col style="width: 5%" />
              <col style="width: 8%" />
              <col style="width: 20%" />
              <col style="width: 20%" />
              <col style="width: 20%" />
              <col style="width: 20%" />
            </colgroup>
            <thead>
              <tr>
                <th>No</th>
                <th>선택</th>
                <th>상태</th>
                <th>자식테이블명</th>
                <th>자식컬럼명</th>
                <th>부모테이블명</th>
                <th>부모컬럼명</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(item, index) in selectList"
                :key="index"
                @click="clickRelationColumn(item)"
              >
                <td>
                  {{
                    index +
                    1 +
                    (oneListData.currentPage - 1) * oneListData.perPage
                  }}
                </td>
                <td>
                  <input
                    type="checkbox"
                    v-model="checkboxList[index]"
                    @change="checkHandle(item.ref_rule_id, index)"
                  />
                </td>
                <td>{{}}</td>
                <td :title="item.child_table_name">
                  {{ item.child_table_name }}
                </td>
                <td :title="item.child_column_name">
                  {{ item.child_column_name }}
                </td>
                <td :title="item.parent_table_name">
                  {{ item.parent_table_name }}
                </td>
                <td :title="item.parent_column_name">
                  {{ item.parent_column_name }}
                </td>
              </tr>
            </tbody>
          </table>
          <PaginationButton
            v-model:currentPage="oneListData.currentPage"
            :perPage="oneListData.perPage"
            :totalCount="oneListDataCount"
            :maxRange="5"
            :click="handlePageClick"
          />
        </div>
        <div class="flex justify-center">
          <button class="blue-btn small-btn" @click="goAddRef()">
            참조무결성 추가
          </button>
          <button class="blue-btn small-btn" v-on:click="deleteRef()">
            참조무결성 삭제
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
      isModalOpen: false,
      activeTab: "tab1",
      modalType: "tab-modal",
      searchPath: mdiMagnify,

      // 검색 조건
      searchData: {
        dbms_name: null,
        schema_name: null,
        table_name: "",
        column_name: "",
        user_id: $cookies.get("USER_ID"),
        currentPage: 1, // 현재 페이지
        perPage: 20, // 한 페이지에 보여질 데이터 개수
      },
      searchDataListCount: 0,
      searchDataIndex: 0,
      // 검색 조건을 통한 데이터 리스트
      searchDataList: [],
      // 검색조건에서 dbms명을 선택하였을 경우 스키마명에서 보여줄 문구
      selectSchema: "선택 불가",

      // DBMS명을 저장할 리스트
      dbmsList: [],
      // 스키마명을 저장할 리스트
      schemaList: [],

      // 조회된 목록들 중에서 하나를 선택하였을 때 저장할 데이터
      oneListData: {
        dbms_name: null,
        schema_name: null,
        table_name: null,
        user_id: $cookies.get("USER_ID"),
        currentPage: 1, // 현재 페이지
        perPage: 20, // 한 페이지에 보여질 데이터 개수
      },
      oneListDataCount: 0,
      oneListDataIndex: 0,
      // 체크박스 선택시 인덱스가 들어갈 리스트
      selectList: [],
      // 체크박스 선택시 ref_rule_id가 들어갈 리스트
      selectedItems: [],

      //데이터 규칙 관리에서 검색조건 결과 목록중 하나를 선택하였을 경우 해당 행의 정보를 저장
      selected_index: 0,

      // 부모 dbms명
      parentDbmsName: null,
      // 부모 스키마명
      parentSchemaName: null,
      //부모 테이블명
      parentTableName: null,
      //부모 테이블한글명
      parentTableNameK: null,

      /************************** 참조무결성 추가 부분 ************************/
      // 부모 테이블 검색
      searchTableData: {
        dbms_name: null,
        schema_name: null,
        table_name: null,
        user_id: $cookies.get("USER_ID"),
        currentPage: 1, // 현재 페이지
        perPage: 7,
      },
      searchTableDataCount: 0,
      searchTableDataIndex: 0,
      // 부모 테이블에 따른 컬럼 목록
      parentColumnList: [],

      // 검색에 따른 데이터 모음
      searchList: [],

      // 자식테이블 조회
      childTableList: [],

      // 부모테이블 검색조건에서 보여지는 목록들중에서 번호를 선택하면 저장되는 리스트
      parentNumList: [],

      // 자식테이블 검색조건에서 보여지는 목록들중에서 번호를 선택하면 저장되는 리스트
      childNumList: [],

      // 참조무결성 추가 부분에서 검색 결과 목록중 하나를 선택하였을 경우
      selected_index2: "",

      checkboxList: [],

      // 검색 버튼을 클릭시 1페이지에서 시작하도록 설정하기 위해 사용(true이면 무조건 1페이지부터, false면 해당 페이지)
      searchButton: true, // 검색 버튼을 클릭하면 true, 검색버튼이 아닌 추가 하거나 삭제 할때는 false
    };
  },
  methods: {
    // 검색버튼 클릭시 현재페이지를 1페이지부터 보여줄수있도록 설정
    updateSearchButton() {
      this.searchButton = true;
    },

    // 참조무결성 삭제
    deleteRef() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      if (vm.selectedItems.length == 0) {
        vm.$showAlert("알림", "삭제할 관계컬럼을 선택해주세요.");
        return;
      }

      vm.Delete();
    },

    // 참조무결성 삭제
    Delete() {
      const vm = this;
      vm.parentDbmsName = null;
      vm.parentSchemaName = null;
      vm.parentTableName = null;
      vm.parentTableNameK = null;
      axios({
        url: "/deleteRef.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: vm.selectedItems,
      })
        .then(function (response) {
          if (response.data > 0) {
            vm.$showAlert("삭제 성공", "성공적으로 삭제하였습니다.");
            vm.selectedItems = [];
            vm.selectList = [];
            vm.checkboxList = [];
            vm.oneListData.currentPage = 1;
            vm.oneSelectList(vm.oneListData, vm.selected_index);
          } else {
            vm.$showAlert("삭제 실패", "삭제 실패하였습니다.");
          }
        })
        .catch(function (error) {
          vm.$showAlert("삭제에 오류가 발생하였습니다.");
        });
    },

    // 체크 박스를 선택했을 때 호출
    checkHandle(ref_rule_id, index) {
      // 해당 항목이 이미 선택되었는지 확인
      const selectedIndex = this.selectedItems.indexOf(ref_rule_id);

      if (selectedIndex !== -1) {
        // 이미 선택된 경우 배열에서 제거
        this.selectedItems.splice(selectedIndex, 1);
      } else {
        // 선택되지 않은 경우 배열에 추가
        this.selectedItems.push(ref_rule_id);
      }
    },

    // table_name 확인
    isSelectedRow(index) {
      return index === this.selected_index;
    },
    // 참조무결성 추가 버튼을 클릭했을 경우
    goAddRef() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      // 검색 조건에서 검색을 하지 않았을 경우
      if (vm.searchDataList.length == 0) {
        vm.$showAlert("알림", "검색을 해주시기 바랍니다.");
        return;
      }
      // 검색조건에서 나열된 행들을 선택하지 않은 상태에서 참조무결성 추가 버튼을 클릭하였을 경우
      let openCheck =
        vm.oneListData.dbms_name == null &&
        vm.oneListData.schema_name == null &&
        vm.oneListData.table_name == null;
      if (openCheck) {
        vm.$showAlert(
          "알림",
          "검색조건에 나열된 테이블 중 하나를 선택해주세요."
        );
        return;
      }
      vm.parentDbmsName = null; // 참조무결성 상세정보 dbms명
      vm.parentSchemaName = null; // 참조무결성 상세정보 스키마명
      vm.parentTableName = null; // 참조무결성 상세정보 테이블명
      vm.parentTableNameK = null; // 참조무결성 상세정보 테이블한글명
      vm.checkboxList = []; // 체크박스 해제
      vm.selectedItems = []; // 체크박스 선택시 ref_rule_id가 저장되는 리스트
      vm.searchTableData.dbms_name = vm.oneListData.dbms_name;
      vm.searchTableData.schema_name = vm.oneListData.schema_name;
      vm.searchChildTable();
      vm.searchList = [];
      vm.searchTableDataCount = 0;
      vm.isModalOpen = true;
    },

    // 검색조건에 필요한 DBMS명을 가져옴
    getDbmsList() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      axios({
        url: "/getDbmsNameList.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: vm.searchData,
      })
        .then(function (response) {
          vm.dbmsList = response.data;
        })
        .catch(function (error) {
          vm.$showAlert("error", "DBMS명을 불러오는데 실패하였습니다.");
        });
    },

    // 검색조건에서 DBMS명을 선택하였을 경우
    clickDbmsName() {
      const vm = this;
      vm.searchButton = true;
      if (vm.searchData.dbms_name == null) {
        vm.selectSchema = "선택 불가";
        vm.searchData.schema_name = null;
        vm.searchData.table_name = "";
        vm.searchData.column_name = "";
        vm.schemaList = [];
        return;
      }

      axios({
        url: "/getSchemaNameList.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: vm.searchData,
      })
        .then(function (response) {
          vm.schemaList = response.data;
          vm.selectSchema = "선택";
        })
        .catch(function (error) {
          vm.$showAlert(
            "error",
            "스키마명을 불러오는데 오류가 발생하였습니다."
          );
        });
    },

    // 검색 버튼을 클릭하였을 경우
    search() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      vm.oneListData.dbms_name = null; // 참조무결성 추가 모달창에서 자식테이블 검색조건 리스트 초기화
      vm.oneListData.schema_name = null; // 참조무결성 추가 모달창에서 자식테이블 검색조건 리스트 초기화
      vm.oneListData.table_name = null; // 참조무결성 추가 모달창에서 자식테이블 검색조건 리스트 초기화
      vm.selected_index = 0;
      vm.parentDbmsName = null; // 참조무결성 상세정보 dbms명
      vm.parentSchemaName = null; // 참조무결성 상세정보 스키마명
      vm.parentTableName = null; // 참조무결성 상세정보 테이블명
      vm.parentTableNameK = null; // 참조무결성 상세정보 테이블한글명
      vm.checkboxList = []; // 체크박스 해제
      vm.selectedItems = []; // 체크박스 선택시 ref_rule_id가 저장되는 리스트
      vm.selectList = []; // 참조무결성 관계컬럼 상세정보 목록
      if (vm.searchButton) {
        vm.searchData.currentPage = 1;
      }
      axios({
        url: "/searchDataList.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: vm.searchData,
      })
        .then(function (response) {
          vm.searchDataList = response.data.searchDataList;
          vm.searchDataListCount = response.data.searchDataListCount;
          vm.searchDataIndex =
            vm.searchDataListCount -
            (vm.searchData.currentPage - 1) * vm.searchData.perPage;
        })
        .catch(function (error) {
          vm.$showAlert(
            "error",
            "검색조건에 맞는 데이터를 불러오는데 오류가 발생하였습니다."
          );
        });
    },

    // 조회된 목록들 중에서 하나를 클릭하였을 경우
    oneSelectList(item, index) {
      const vm = this;
      vm.selected_index = index;
      vm.oneListData.dbms_name = item.dbms_name;
      vm.oneListData.schema_name = item.schema_name;
      vm.oneListData.table_name = item.table_name;
      axios({
        url: "/oneSelectList.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: vm.oneListData,
      })
        .then(function (response) {
          vm.selectList = response.data.selectList;
          vm.oneListDataCount = response.data.oneListDataCount;
          vm.oneListDataIndex =
            vm.oneListDataCount -
            (vm.oneListData.currentPage - 1) * vm.oneListData.perPage;
        })
        .catch(function (error) {
          vm.$showAlert(
            "error",
            "여러 목록들중 선택한 데이터를 불러오는데 오류가 발생하였습니다."
          );
        });
    },

    // 참조무결성 관계컬럼 정보에서 하나의 목록을 선택했을 경우
    clickRelationColumn(item) {
      const vm = this;
      vm.parentDbmsName = item.dbms_name;
      vm.parentSchemaName = item.schema_name;
      vm.parentTableName = item.parent_table_name;
      vm.parentTableNameK = item.parent_table_name_k;
    },

    /************************** 참조무결성 추가 부분 ************************/
    // table_name 확인
    isSelectedRow2(index) {
      return index.trim() === this.selected_index2.trim();
    },
    // 이전 페이지로 돌아가기
    back() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      vm.searchTableData.table_name = null;
      vm.parentColumnList = [];
      vm.searchList = [];
      vm.isModalOpen = false;
      vm.isSelectedRow2("");
      vm.selected_index2 = "";
      vm.childNumList = [];
      vm.parentNumList = [];
      vm.searchButton = true;
      vm.searchParentTableJson();
    },

    // 조회된 부모테이블 목록중 하나를 클릭하였을 경우
    clickParentList(name) {
      const vm = this;
      vm.parentNumList = [];
      vm.childNumList = [];

      vm.selected_index2 = name;
      let data = {
        dbms_name: vm.searchTableData.dbms_name,
        schema_name: vm.searchTableData.schema_name,
        user_id: vm.searchTableData.user_id,
        table_name: name,
      };

      axios({
        url: "/childTableList.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: data,
      })
        .then(function (response) {
          vm.parentColumnList = response.data;
        })
        .catch(function (error) {
          vm.$showAlert(
            "error",
            "선택하신 부모테이블의 컬럼을 불러오는데 오류가 발생하였습니다."
          );
        });
    },

    // 부모테이블 검색을 하였을 경우
    searchParentTable() {
      const vm = this;

      if (vm.oneListData.table_name.trim() === vm.searchTableData.table_name) {
        vm.$showAlert("알림", "테이블명을 다시 입력해 주시기 바랍니다.");
        return;
      }

      vm.parentColumnList = [];
      vm.childNumList = [];
      vm.parentNumList = [];
      vm.isSelectedRow2("");
      vm.selected_index2 = "";
      vm.searchButton = true;
      vm.searchParentTableJson();
    },
    // 부모테이블 검색
    searchParentTableJson() {
      const vm = this;
      if (vm.searchButton) {
        vm.searchTableData.currentPage = 1;
      }
      const searchtabledata = {
        dbms_name: vm.searchTableData.dbms_name,
        schema_name: vm.searchTableData.schema_name,
        table_name: vm.searchTableData.table_name,
        user_id: vm.searchTableData.user_id,
        currentPage: vm.searchTableData.currentPage,
        perPage: vm.searchTableData.perPage,
        child_table_name: vm.oneListData.table_name,
      };
      axios({
        url: "/searchParentTable.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: searchtabledata,
      })
        .then(function (response) {
          vm.searchList = response.data.searchList;
          vm.searchTableDataCount = response.data.searchListCount;
          vm.searchTableDataIndex =
            vm.searchTableDataCount -
            (vm.searchTableData.currentPage - 1) * vm.searchTableData.perPage;
        })
        .catch(function (error) {
          vm.$showAlert(
            "error",
            "부모테이블 검색조건을 통해 데이터들을 불러오는데 오류가 발생하였습니다."
          );
        });
    },

    // 자식테이블 검색조건에 해당하는 데이터들 조회
    searchChildTable() {
      const vm = this;
      let childData = {
        dbms_name: vm.oneListData.dbms_name,
        schema_name: vm.oneListData.schema_name,
        table_name: vm.oneListData.table_name,
        user_id: $cookies.get("USER_ID"),
      };
      axios({
        url: "/childTableList.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: childData,
      })
        .then(function (response) {
          vm.childTableList = response.data;
        })
        .catch(function (error) {
          vm.$showAlert(
            "error",
            "자식테이블 데이터 조회에 오류가 발생하였습니다."
          );
        });
    },

    // 참조 무결성 등록을 클릭하였을 경우
    registerRef() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      // 아무것도 선택하지 않았을 경우
      if (vm.parentNumList.length == 0 || vm.childNumList.length == 0) {
        vm.parentNumList = [];
        vm.childNumList = [];
        vm.$showAlert("알림", "관계를 선택해주시기 바랍니다.");
        return;
      }
      let data = []; // 등록할 부모테이블 컬럼과 자식테이블 컬럼을 저장할 공간
      let num = [];
      // 부모 테이블 검색조건에서 중복된 번호가 존재하는지 확인
      for (let re of vm.parentNumList) {
        if (num.includes(re)) {
          vm.parentNumList = [];
          vm.childNumList = [];
          vm.$showAlert("알림", "관계를 다시 선택해주시기 바랍니다.");
          return;
        }
        if (re != undefined) {
          num.push(re);
        }
      }
      num = [];
      // 자식테이블 검색조건에서 중복된 번호가 존재하는지 확인
      for (let re of vm.childNumList) {
        if (num.includes(re)) {
          vm.parentNumList = [];
          vm.childNumList = [];
          vm.$showAlert("알림", "관계를 다시 선택해주시기 바랍니다.");
          return;
        }
        if (re != undefined) {
          num.push(re);
        }
      }

      vm.parentNumList.forEach((value, index) => {
        const childIndex = vm.childNumList.indexOf(value);
        if (childIndex !== -1) {
          data.push({
            parent_column_id: vm.parentColumnList[index].column_id,
            child_column_id: vm.childTableList[childIndex].column_id,
            user_id: $cookies.get("USER_ID"),
          });
        }
      });
      // 선택한 관계들이 서로 일치하지 않을 때
      if (data.length == 0) {
        vm.$showAlert("알림", "관계를 다시 선택해주시기 바랍니다.");
      }
      axios({
        url: "/insertRef.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: data,
      })
        .then(function (response) {
          if (response.data > 0) {
            vm.$showAlert("등록 성공", "성공적으로 등록하였습니다.");
            vm.isModalOpen = false;
            vm.parentColumnList = [];
            vm.searchList = [];
            vm.searchTableData.table_name = null;
            vm.isSelectedRow2("");
            vm.selected_index2 = "";
            vm.searchButton = true;
            vm.searchParentTableJson();
            vm.searchButton = false;
            vm.oneSelectList(vm.oneListData, vm.selected_index);
            vm.childNumList = [];
            vm.parentNumList = [];
          } else {
            vm.$showAlert("등록 실패", "등록 실패하였습니다.");
          }
        })
        .catch(function (error) {
          vm.$showAlert("error", "등록에 오류가 발생하였습니다.");
        });
    },

    //페이지네이션 클릭 이벤트
    handlePageClick() {},
  },

  watch: {
    "searchData.currentPage": function () {
      this.searchButton = false;
      this.search();
    },
    "oneListData.currentPage": function () {
      this.oneSelectList(this.oneListData, this.selected_index);
      this.checkboxList = [];
    },
    "searchTableData.currentPage": function () {
      this.searchButton = false;
      this.searchParentTableJson();
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
  },
};
</script>

<style lang="css" scoped>
.content-zone {
  border: 1px solid #eee;
  padding: 10px;
  height: calc(100% - 55px);
  overflow: auto;
}
.list-table tbody .selected-row {
  background-color: #c6feff;
}
.refRuleManageTable tr td select,
input {
  width: 100%;
}
</style>
