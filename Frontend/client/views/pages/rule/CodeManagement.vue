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
        <button class="gray-btn large-btn flex45" style="margin: 10px 5px" @click="closeCancelModal()">
          취소
        </button>
        <button class="blue-btn large-btn flex45" @click="closeCheckModal()">
          확인
        </button>
      </div>
    </div>
    <div class="container">
      <div class="page-titleZone flex justify-between">
        <p class="main-title">진단규칙설정 > 코드 관리</p>
      </div>
      <div class="content">
        <div class="row">
          <div class="content-titleZone">
            <p class="box-title">> 검색조건</p>
          </div>
          <div class="searchbar-zone">
            <div class="flex justify-start align-center">
              <span class="option-title">진단대상DBMS</span>
              <select name="" id="" class="square-select" v-model="cmListSearch.dbms_id" @change="updateSearchButton()">
                <option :value="null">전체</option>
                <option :value="DBname.dbms_name" v-for="DBname in ListDBMS" :key="DBname">
                  {{ DBname.dbms_name }}
                </option>
              </select>
              <span class="option-title">코드유형</span>
              <select name="" id="" class="square-select" v-model="cmListSearch.code_type" @change="updateSearchButton()">
                <option :value="null">선택</option>
                <option value="공통코드">공통코드</option>
                <option value="목록성코드">목록성코드</option>
              </select>
              <button class="blue-btn small-btn" @click="allSelectCode()">DBMS 검색</button>
              <span class="option-title">총 {{ cmListCount }}건이 검색되었습니다!</span>
            </div>
          </div>
          <div class="table-zone">
            <div class="flex-between">
              <div class="count-zone" style="margin: 5px 10px 15px">
                <p>
                  총 <span id>{{ cmListCount }}</span>건 중 <span>{{ selectedItems.length }}</span>건 선택
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
                  <th>DBMS명</th>
                  <th>코드유형</th>
                  <th>검증코드명</th>
                  <th>코드생성SQL</th>
                  <th>설명</th>
                </tr>
              </thead>
              <tbody class="searchResult">
                <tr :class="{ 'selected-row': isSelectedRow(item) }" v-for="(item, index) in dataList" :key="index"
                  @click="selectOne(item)">
                  <td>{{ index + 1 + (cmListSearch.currentPage - 1) * cmListSearch.perPage }}</td>
                  <td>
                    <input type="checkbox" v-model="checkboxList[index]" @change="checkHandle(item.cr_code_id, index)" />
                  </td>
                  <td>{{ item.dd_dbms_name }}</td>
                  <td>{{ item.cr_code_type }}</td>
                  <td>{{ item.cr_code_nm }}</td>
                  <td style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap">
                    {{ item.cr_code_sql }}
                  </td>
                  <td style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap">
                    {{ item.cr_code_explain }}
                  </td>
                </tr>
              </tbody>
            </table>
            <div>
              <button class="red-btn small-btn" v-on:click="selectCodeDelete()">선택 항목 삭제</button>
            </div>
          </div>
          <PaginationButton v-model:currentPage="cmListSearch.currentPage" :perPage="cmListSearch.perPage"
            :totalCount="cmListCount" :maxRange="5" :click="handlePageChange" />
        </div>
        <div class="row form-box" style="padding: 20px">
          <div class="content-titleZone">
            <p class="box-title">> {{ buttonName }}</p>
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
                  <th>검증코드명*</th>
                  <td>
                    <input type="text" name="" id="" v-model.trim="code.cr_code_nm" class="codeInput" />
                  </td>
                  <th>코드생성 SQL</th>
                  <td rowspan="4">
                    <textarea name="" id="input_text" v-model.trim="code.cr_code_sql" style="width: 100%"
                      :placeholder="selectedCode.cr_code_sql"></textarea>
                  </td>
                </tr>
                <tr>
                  <th>DBMS명*</th>
                  <td>
                    <div class="flex justify-between">
                      <select name="" id="" class="flex30 codeInput" v-model="code.cr_dbms_id">
                        <option :value="null">선택</option>
                        <option :value="dbName.dbms_id" v-for="dbName in ListDBMS" :key="dbName">
                          {{ dbName.dbms_name }}
                        </option>
                        >
                      </select>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>코드유형*</th>
                  <td>
                    <div class="flex justify-between">
                      <select name="" id="" class="flex30 codeInput" v-model="code.cr_code_type" v-on:change="typeChange">
                        <option :value="null">선택</option>
                        <option value="공통코드">공통코드</option>
                        <option value="목록성코드">목록성코드</option>
                      </select>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>설명</th>
                  <td>
                    <input type="text" name="" id="" v-model.trim="code.cr_code_explain" class="codeInput" />
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="flex justify-center" style="margin-top: 10px">
            <button class="blue-btn small-btn" v-if="buttonName == '등록'" v-on:click="register()">등록</button>
            <!-- 값이 있을때 수정 버튼으로 변경 -->
            <button class="blue-btn small-btn" v-if="buttonName == '수정'" v-on:click="update()">수정</button>
            <button class="blue-btn small-btn" @click="reset()">초기화</button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="container">
    <div class="page-titleZone flex justify-between">
      <p class="main-title">진단규칙설정 > 코드 관리</p>
    </div>
    <div class="content">
      <div class="row">
        <div class="content-titleZone">
          <p class="box-title">> 검색조건</p>
        </div>
        <div class="searchbar-zone">
          <div class="flex justify-start align-center">
            <span class="option-title">진단대상DBMS</span>
            <select name="" id="" class="square-select" v-model="cmListSearch.dbms_id" @change="updateSearchButton()">
              <option :value="null">선택</option>
              <option :value="DBname.dbms_name" v-for="DBname in ListDBMS" :key="DBname">
                {{ DBname.dbms_name }}
              </option>
            </select>
            <span class="option-title">코드유형</span>
            <select name="" id="" class="square-select" v-model="cmListSearch.code_type" @change="updateSearchButton()">
              <option :value="null">선택</option>
              <option value="공통코드">공통코드</option>
              <option value="목록성코드">목록성코드</option>
            </select>
            <button class="blue-btn small-btn" @click="allSelectCode()">
              DBMS 검색
            </button>
            <span class="option-title">총 {{ cmListCount }}건이 검색되었습니다!</span>
          </div>
        </div>
        <div class="table-zone">
          <div class="flex-between">
            <div class="count-zone" style="margin: 5px 10px 15px">
              <p>
                총 <span id>{{ cmListCount }}</span>건 중 <span>{{ selectedItems.length }}</span>건 선택
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
                <th>DBMS명</th>
                <th>코드유형</th>
                <th>검증코드명</th>
                <th>코드생성SQL</th>
                <th>설명</th>
              </tr>
            </thead>
            <tbody class="searchResult">
              <tr :class="{ 'selected-row': isSelectedRow(item) }" v-for="(item, index) in dataList" :key="index"
                @click="selectOne(item)">
                <td>
                  {{
                    index +
                    1 +
                    (cmListSearch.currentPage - 1) * cmListSearch.perPage
                  }}
                </td>
                <td>
                  <input type="checkbox" v-model="checkboxList[index]" @change="checkHandle(item.cr_code_id, index)" />
                </td>
                <td>{{ item.dd_dbms_name }}</td>
                <td>{{ item.cr_code_type }}</td>
                <td>{{ item.cr_code_nm }}</td>
                <td style="
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                  ">
                  {{ item.cr_code_sql }}
                </td>
                <td style="
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                  ">
                  {{ item.cr_code_explain }}
                </td>
              </tr>
            </tbody>
          </table>
          <div>
            <button class="red-btn small-btn" v-on:click="selectCodeDelete()">
              선택 항목 삭제
            </button>
          </div>
        </div>
        <PaginationButton v-model:currentPage="cmListSearch.currentPage" :perPage="cmListSearch.perPage"
          :totalCount="cmListCount" :maxRange="5" :click="handlePageChange" />
      </div>
      <div class="row form-box" style="padding: 20px">
        <div class="content-titleZone">
          <p class="box-title">> {{ buttonName }}</p>
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
                <th>검증코드명*</th>
                <td>
                  <input type="text" name="" id="" v-model.trim="code.cr_code_nm" class="codeInput" />
                </td>
                <th>코드생성 SQL</th>
                <td rowspan="4">
                  <textarea name="" id="input_text" v-model.trim="code.cr_code_sql" style="width: 100%"
                    :placeholder="selectedCode.cr_code_sql"></textarea>
                </td>
              </tr>
              <tr>
                <th>DBMS명*</th>
                <td>
                  <div class="flex justify-between">
                    <select name="" id="" class="flex30 codeInput" v-model="code.cr_dbms_id">
                      <option :value="null">선택</option>
                      <option :value="dbName.dbms_id" v-for="dbName in ListDBMS" :key="dbName">
                        {{ dbName.dbms_name }}
                      </option>
                      >
                    </select>
                  </div>
                </td>
              </tr>
              <tr>
                <th>코드유형*</th>
                <td>
                  <div class="flex justify-between">
                    <select name="" id="" class="flex30 codeInput" v-model="code.cr_code_type" v-on:change="typeChange">
                      <option :value="null">선택</option>
                      <option value="공통코드">공통코드</option>
                      <option value="목록성코드">목록성코드</option>
                    </select>
                  </div>
                </td>
              </tr>
              <tr>
                <th>설명</th>
                <td>
                  <input type="text" name="" id="" v-model.trim="code.cr_code_explain" class="codeInput" />
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="flex justify-center" style="margin-top: 10px">
          <button class="blue-btn small-btn" v-if="buttonName == '등록'" v-on:click="register()">
            등록
          </button>
          <!-- 값이 있을때 수정 버튼으로 변경 -->
          <button class="blue-btn small-btn" v-if="buttonName == '수정'" v-on:click="update()">
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

      // 수행완료(0), 등록(1), 수정(2), 삭제(3)인지 확인
      menu: null,

      //체크박스를 선택하였을 때 저장하는 곳
      selectedItems: [],
      isChecked: false,
      checkboxList: [],

      searchPath: mdiMagnify,
      inputValue: null,
      buttonName: "등록", // 버튼값 변경 등록-->수정, 수정-->등록
      // 데이터 저장
      code: {
        cr_code_id: null,
        cr_code_type: null,
        cr_code_nm: null,
        cr_code_sql: null,
        cr_code_explain: null,
        cr_dbms_id: null,
        dd_dbms_name: null,
      },

      // DB name을 저장
      ListDBMS: [],

      // 코드 관리 데이터 목록 조회
      dataList: [],

      // 검색 조건 db이름 저장
      cmListSearch: {
        user_id: $cookies.get("USER_ID"),
        dbms_id: null,
        code_type: null,
        currentPage: 1, // 현재 페이지
        perPage: 10, // 한 페이지에 보여질 데이터 개수
      },
      cmListCount: 0,
      cmIndex: 0,

      //공통 코드 sql
      commonSql: `SELECT 코드분류 AS CD_CLS
      ,코드분류명 AS CD_CLS_NM
      ,코드 AS CD_ID
      ,코드명 AS CD_NM
FROM 스키마명.코드테이블
WHERE 조건`,

      // 목적성 코드 sql
      purposeSql: `SELECT 코드 AS CD_ID
      ,코드명 AS CD_NM
FROM 스키마명.코드테이블
WHERE 조건`,

      //선택된 검증코드
      selectedCode: "",

      // 검색 버튼을 클릭시 1페이지에서 시작하도록 설정하기 위해 사용(true이면 무조건 1페이지부터, false면 해당 페이지)
      searchButton: true, // 검색 버튼을 클릭하면 true, 검색버튼이 아닌 추가 하거나 삭제 할때는 false
    };
  },
  methods: {
    // 검색버튼 클릭시 현재페이지를 1페이지부터 보여줄수있도록 설정
    updateSearchButton() {
      this.searchButton = true;
    },

    // 코드 관리 데이터 모든 항목 삭제
    allCodeDelete() {
      const vm = this;

      vm.menu = 4;
      vm.modalTitle = "모든 항목 삭제";
      vm.modalContents = "저장되어있는 모든 항목을 삭제 하시겠습니까?";
      vm.isSelectModalOpen = true;
    },

    // 코드 관리 등록
    register() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      if (vm.$checkLogin() == true) {
        return;
      }
      let result = vm.inputCheck();
      if (result == 0) return;
      vm.menu = 1;
      vm.modalTitle = "등록";
      vm.modalContents = "등록하시겠습니까?";
      vm.isSelectModalOpen = true;
    },

    // 목록들중에서 하나 선택
    selectOne(item) {
      this.selectedCode = item;
      this.code = {
        cr_code_id: item.cr_code_id,
        cr_code_type: item.cr_code_type,
        cr_code_nm: item.cr_code_nm,
        cr_code_sql: item.cr_code_sql,
        cr_code_explain: item.cr_code_explain,
        cr_dbms_id: item.cr_dbms_id,
        dd_dbms_name: item.dd_dbms_name,
      };
      this.buttonName = "수정";
    },

    isSelectedRow(item) {
      return item === this.selectedCode;
    },

    // 코드 관리 수정
    update() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      // 입력값 확인
      const result = vm.inputCheck();
      if (result == 0) return;

      vm.menu = 2;
      vm.modalTitle = "수정";
      vm.modalContents = "수정하시겠습니까?";
      vm.isSelectModalOpen = true;
    },

    // 체크 박스 선택한 행을 삭제
    selectCodeDelete() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      if (vm.selectedItems.length === 0) {
        vm.$showAlert("알림", "삭제할 항목을 선택하시기 바랍니다.");
        return;
      }
      vm.menu = 3;
      vm.modalTitle = "선택 항목 삭제";
      vm.modalContents =
        "선택하신 " + vm.selectedItems.length + "건을 삭제 하시겠습니까?";
      vm.isSelectModalOpen = true;
    },

    // 체크 박스를 선택했을 때 호출
    checkHandle(codeId, index) {
      // 해당 항목이 이미 선택되었는지 확인
      const selectedIndex = this.selectedItems.indexOf(codeId);

      if (selectedIndex !== -1) {
        // 이미 선택된 경우 배열에서 제거
        this.selectedItems.splice(selectedIndex, 1);
      } else {
        // 선택되지 않은 경우 배열에 추가
        this.selectedItems.push(codeId);
      }
    },

    // 초기화
    reset() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      vm.code = {
        cr_code_id: null,
        cr_code_type: null,
        cr_code_nm: null,
        cr_code_sql: null,
        cr_code_explain: null,
        cr_dbms_id: null,
        dd_dbms_name: null,
      };
      vm.selectedItems = [];
      vm.checkboxList = [];
      vm.selectedCode = "";
      vm.buttonName = "등록";
    },

    // input에 입력했는지 확인
    inputCheck() {
      const vm = this;
      if (vm.code.cr_code_nm == null || vm.code.cr_code_nm == "") {
        vm.$showAlert("알림", "검증코드명을 입력해주시기 바랍니다.");
        return 0;
      }
      if (vm.code.cr_dbms_id == null || vm.code.cr_dbms_id == "") {
        vm.$showAlert("알림", "DBMS명을 입력해주시기 바랍니다.");
        return 0;
      }
      if (vm.code.cr_code_type == null || vm.code.cr_code_type == "") {
        vm.$showAlert("알림", "코드유형을 선택해주시기 바랍니다.");
        return 0;
      }
      // if (vm.code.cr_code_explain == null || vm.code.cr_code_explain == '') {
      //     vm.$showAlert('알림', '설명을 입력해주시기 바랍니다.');
      //     return 0;
      // }
      if (vm.code.cr_code_sql == null || vm.code.cr_code_sql == "") {
        vm.$showAlert("알림", "코드생성 SQL을 입력해주시기 바랍니다.");
        return 0;
      }
      return 1;
    },

    // 진단대상DBMS 선택 항목 값들을 불러옴
    getOptionValue() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      let Data = {
        user_id: $cookies.get("USER_ID"),
      };
      axios({
        url: "/getOptionValue.json",
        method: "post",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        data: Data,
      })
        .then(function (response) {
          vm.ListDBMS = response.data;
        })
        .catch(function (error) {
          vm.$showAlert(
            "error",
            "옵션 값을 가져오는 데 오류가 발생하였습니다."
          );
        });
      // 코드 관리 데이터들을 모두 가져온 후 조회
      vm.allSelectCode();
    },

    // 코드 관리 데이터들을 조회
    allSelectCode() {
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
          vm.dataList = response.data.cmList;
          vm.cmListCount = response.data.cmListCount;
          vm.cmIndex =
            vm.cmListCount -
            (vm.cmListSearch.currentPage - 1) * vm.cmListSearch.perPage;
        })
        .catch(function (error) {
          vm.$showAlert(
            "error",
            "코드관리 데이터들을 가져오는 데 오류가 발생하였습니다."
          );
        });
    },

    // 모달창에서 취소버튼을 클릭했을 경우
    closeCancelModal() {
      const vm = this;
      vm.isSelectModalOpen = false;
      return;
    },

    // 모달창에서 확인 버튼을 클릭했을 경우
    closeCheckModal() {
      const vm = this;

      // 등록(1)
      if (vm.menu == 1) {
        vm.isSelectModalOpen = false;
        axios({
          url: "/insertCode.json",
          method: "post",
          headers: { "Content-Type": "application/json; charset=UTF-8" },
          data: vm.code,
        })
          .then(function (response) {
            if (response.data > 0) {
              vm.menu = 0;
              vm.$showAlert("등록 성공", "성공적으로 등록하였습니다.");
              vm.reset();
              vm.checkboxList = [];
              vm.selectedItems = [];
              vm.selectedCode = "";
              vm.searchButton = false;
              vm.allSelectCode();
            } else {
              vm.menu = -1;
              vm.$showAlert(
                "등록 실패",
                "등록 실패하였습니다.\n코드생성 SQL을 다시 확인해주세요"
              );
            }
          })
          .catch(function (error) {
            vm.$showAlert("error", "등록에 오류가 발생하였습니다.");
          });
      }

      // 수정(2)
      if (vm.menu == 2) {
        vm.isSelectModalOpen = false;
        axios({
          url: "/updateCode.json",
          method: "post",
          headers: { "Content-Type": "application/json; charset=UTF-8" },
          data: vm.code,
        })
          .then(function (response) {
            if (response.data > 0) {
              vm.menu = 0;
              vm.$showAlert("수정 성공", "성공적으로 수정하였습니다.");
              vm.reset();
              vm.checkboxList = [];
              vm.selectedItems = [];
              vm.selectedCode = "";
              vm.searchButton = false;
              vm.allSelectCode();
            } else {
              vm.menu = -1;
              vm.$showAlert(
                "삭제 실패",
                "수정 실패하였습니다.\n코드생성 SQL을 다시 확인해주세요"
              );
            }
          })
          .catch(function (error) {
            vm.$showAlert("error", "수정에 오류가 발생하였습니다.");
          });
      }

      // 선택한 항목 삭제(3)
      if (vm.menu == 3) {
        vm.isSelectModalOpen = false;
        axios({
          url: "/deleteCode.json",
          method: "post",
          headers: { "Content-Type": "application/json; charset=UTF-8" },
          data: this.selectedItems,
        })
          .then(function (response) {
            if (response.data > 0) {
              vm.menu = 0;
              vm.reset();
              vm.checkboxList = [];
              vm.selectedItems = [];
              vm.selectedCode = "";
              vm.$showAlert("삭제 성공", "선택한 항목을 삭제하였습니다.");
              vm.cmListSearch.currentPage = 1;
              vm.allSelectCode();
            } else {
              vm.menu = -1;
              vm.$showAlert(
                "삭제 실패",
                "선택한 항목을 삭제하는데 실패하였습니다."
              );
            }
          })
          .catch(function (error) {
            vm.$showAlert("error", "삭제에 오류가 발생하였습니다.");
          });
      }

      // 모든 항목 삭제(4)
      if (vm.menu == 4) {
        const vm = this;

        axios({
          url: "/allCodeDelete.json",
          method: "post",
          headers: { "Content-Type": "application/json; charset=UTF-8" },
          data: {},
        })
          .then(function (response) {
            if (response.data > 0) {
              vm.selectedItems = [];
              vm.checkBoxIndex = [];
              vm.modalContents = "모든 항목을 삭제하였습니다.";
              vm.menu = 0;
            } else {
              vm.modalContents = "모든 항목을 삭제하는데 실패하였습니다.";
              vm.menu = -1;
            }
            vm.isSelectModalOpen = false;
          })
          .catch(function (error) {
            vm.$showAlert(
              "error",
              "모든 항목을 삭제하는데 오류가 발생하였습니다."
            );
          });
      }

      // 수행완료(0)
      if (vm.menu == 0) {
        vm.isSelectModalOpen = false;
        vm.reset();

        vm.selectedCode = "";
        vm.allSelectCode();

        vm.modalTitle = null;
        vm.modalContents = null;
      }

      // 수행실패(-1)
      if (vm.menu == -1) {
        vm.isSelectModalOpen = false;
        vm.modalTitle = null;
        vm.modalContents = null;
        return;
      }
    },

    // 페이지 이동시 체크박스 상태 해제
    resetCheckBoxState() {
      this.selectedItems = [];
      this.checkboxList = [];
    },

    //코드유형 변경시 sql문 변경
    typeChange() {
      const vm = this;
      vm.code.cr_code_sql =
        vm.code.cr_code_type === "공통코드" ? vm.commonSql : vm.purposeSql;
    },

    // 페이지네이션 클릭 이벤트
    handlePageChange(pageNumber) {
      this.cmListSearch.currentPage = pageNumber;
    },
  },
  watch: {
    "cmListSearch.currentPage": function () {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      vm.searchButton = false;
      vm.allSelectCode();
      vm.resetCheckBoxState();
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
    this.getOptionValue();
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

.codeInput {
  width: 100%;
  min-width: -webkit-fill-available;
}

.searchResult .selected-row {
  background-color: #c6feff;
}
</style>
