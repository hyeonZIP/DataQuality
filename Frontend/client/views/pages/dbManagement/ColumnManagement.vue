<!-- 
  작성자 : 임재현
	작성일 : 2024.01.17
	내용 : 컬럼분석 페이지
 -->
<template>
    <div v-show="isModalOpen" class="modal-wrapper">
        <div class="modal-container" style="height: 60%">
            <div class="modal-title">
                <div class="flex justify-between align-center">
                    <h2>컬럼분석결과조회</h2>
                    <button class="close-btn" @click="closeModal">X</button>
                </div>
            </div>
            <div class="modal-content-monthly">
                <ul class="tab-content">
                    <li id="tab01">
                        <table class="form-table" style="white-space: pre-wrap; width: 100%">
                            <tbody>
                                <tr>
                                    <th>분석건수</th>
                                    <td colspan="3">{{ columnsCount }}</td>
                                </tr>
                                <tr>
                                    <th>Null 건수</th>
                                    <td>{{ nullCount }}</td>
                                    <th>Space 건수</th>
                                    <td>{{ spaceCount }}</td>
                                </tr>
                                <tr>
                                    <th>최소값1</th>
                                    <td>{{ minCount }}</td>
                                    <th>최대값1</th>
                                    <td>{{ maxCount }}</td>
                                </tr>
                                <tr>
                                    <th>최소값2</th>
                                    <td>{{ minCount2 }}</td>
                                    <th>최대값2</th>
                                    <td>{{ maxCount2 }}</td>
                                </tr>
                                <tr>
                                    <th>최소값3</th>
                                    <td>{{ minCount3 }}</td>
                                    <th>최대값3</th>
                                    <td>{{ maxCount3 }}</td>
                                </tr>
                                <tr>
                                    <th>최소길이</th>
                                    <td>{{ minLength }}</td>
                                    <th>최대길이</th>
                                    <td>{{ maxLength }}</td>
                                </tr>
                            </tbody>
                        </table>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="page-titleZone flex justify-between">
            <p class="main-title">진단대상관리 > 컬럼분석</p>
            <PageNavigation />
        </div>
        <div class="content-box flex justify-between">
            <div class="left-content flex40">
                <div class="flex-column justify-between">
                    <div class="host-zone flex20">
                        <div class="content-titleZone">
                            <p class="box-title">검색 조건</p>
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
                                    <tbody class="columnManageTable">
                                        <tr>
                                            <th>DBMS</th>
                                            <td>
                                                <select v-model="previousData.dbms_name">
                                                    <option value="none">선택</option>
                                                    <option v-for="item in dbmsOption" :key="item" :value="item">
                                                        {{ item }}
                                                    </option>
                                                </select>
                                            </td>
                                            <th>스키마명</th>
                                            <td>
                                                <select v-model="previousData.schema_name">
                                                    <option value="none">선택</option>
                                                    <option v-for="item in schemaOption" :key="item" :value="item">
                                                        {{ item }}
                                                    </option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>테이블명</th>
                                            <td>
                                                <input type="text" v-model="previousData.table_nm" />
                                            </td>
                                            <th>컬럼명</th>
                                            <td>
                                                <input type="text" v-model="previousData.column_nm" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="justify-start">
                                    <button class="blue-btn small-btn" @click="setPage()">검색</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="file-zone flex80">
                        <div class="content-titleZone">
                            <p class="box-title">검색 결과</p>
                        </div>
                        <div class="table-zone">
                            <table class="list-table">
                                <colgroup>
                                    <col style="width: 5%" />
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
                                        <th>스키마명</th>
                                        <th>테이블명</th>
                                        <th>테이블 한글명</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr
                                        v-for="(item, index) in searchResultList"
                                        :class="{ 'selected-row': isSelectedRow(item.table_id) }"
                                        :key="index"
                                        @click="tableSelect(item.table_id)"
                                    >
                                        <td>
                                            {{
                                                index +
                                                1 +
                                                (searchResultData.currentPage - 1) * searchResultData.perPage
                                            }}
                                        </td>
                                        <td>
                                            <input
                                                type="checkbox"
                                                v-model="checkboxList[index]"
                                                :checked="isSelected(index)"
                                                @click="handleClick(index)"
                                            />
                                        </td>
                                        <td>{{ item.dbms_name }}</td>
                                        <td>{{ item.schema_name }}</td>
                                        <td>{{ item.table_nm }}</td>
                                        <td>{{ item.table_nm_k }}</td>
                                    </tr>
                                </tbody>
                            </table>
                            <div class="justify-start">
                                <button class="blue-btn variable-btn" @click="analyzeColumn">컬럼 분석 실행</button>
                            </div>
                            <PaginationButton
                                v-model:currentPage="searchResultData.currentPage"
                                :perPage="searchResultData.perPage"
                                :totalCount="searchResultCount"
                                :maxRange="5"
                                :click="handlePageChange"
                            />
                        </div>
                    </div>
                </div>
            </div>
            <div class="right-content flex60">
                <div class="flex-column">
                    <div>
                        <div class="content-titleZone">
                            <p class="box-title">테이블 컬럼 목록</p>
                        </div>
                        <div class="table-zone">
                            <table class="list-table">
                                <colgroup>
                                    <col style="width: 5%" />
                                    <col style="width: " />
                                    <col style="width: " />
                                    <col style="width: " />
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th>No.</th>
                                        <th>컬럼명</th>
                                        <th>한글명</th>
                                        <th>DataType</th>
                                        <th>Null여부</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr
                                        v-for="(item, index) in tableColumnList"
                                        :class="{ 'selected-row': isSelectedCol(item.column_id) }"
                                        :key="index"
                                        @click="selectColumnAnl(index)"
                                        @dblclick="openModal(index)"
                                    >
                                        <td>
                                            {{
                                                index + 1 + (tableColumnData.currentPage - 1) * tableColumnData.perPage
                                            }}
                                        </td>
                                        <td>{{ item.column_nm }}</td>
                                        <td>{{ item.column_nm_k }}</td>
                                        <td>{{ item.column_type }}</td>
                                        <td>{{ item.column_isnull }}</td>
                                    </tr>
                                </tbody>
                            </table>
                            <PaginationButton
                                v-model:currentPage="tableColumnData.currentPage"
                                :perPage="tableColumnData.perPage"
                                :totalCount="tableColumnListCount"
                                :maxRange="5"
                                :click="handlePageChange"
                            />
                        </div>
                    </div>
                    <div style="margin-top: 30px">
                        <div class="content-titleZone">
                            <p class="box-title">테이블 내 컬럼 데이터 분석 정보</p>
                        </div>
                        <div class="table-zone">
                            <div class="table-container">
                                <table class="list-table">
                                    <colgroup>
                                        <col style="width: 5%" />
                                        <col style="width: " />
                                        <col style="width: " />
                                        <col style="width: " />
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <th>No.</th>
                                            <th>데이터</th>
                                            <th>COUNT</th>
                                            <th>분포</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr v-for="(item, index) in selectedColumn" :key="index">
                                            <td>{{ index + 1 }}</td>
                                            <td>{{ item.column_anl_value }}</td>
                                            <td>{{ item.column_anl_count }}</td>
                                            <td>{{ ((item.column_anl_count / columnsCount) * 100).toFixed(2) }}%</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import PageNavigation from '../../component/PageNavigation.vue';
import PaginationButton from '../../component/PaginationButton.vue';
import FileTree from '../../component/FileTree.vue';
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiMagnify } from '@mdi/js';
export default {
    data() {
        return {
            searchPath: mdiMagnify,

            searchResultList: [], //검색결과 리스트
            tableColumnList: [], //컬럼 리스트

            dbmsOption: [], //DBMS 옵션
            schemaOption: [], //스키마 옵션

            checkboxList: [], //체크된 행의 배열상의 인덱스 0,1,2...
            selectedIndex: [], //체크된 행의 DB상의 인덱스 저장 24, 34, 56 ...

            selectedColumn: [], // 상세 표에 나올 컬럼 데이터
            columnsCount: null, // 컬럼의 총 데이터 수

            //검색조건
            searchResultData: {
                dbms_name: 'none',
                dbms_id: '',
                schema_name: 'none',
                schema_id: '',
                table_id: '',
                table_nm: '',
                column_nm: '',
                user_id: $cookies.get('USER_ID'),
                currentPage: 1,
                perPage: 5,
            },

            previousData: {
                dbms_name: 'none',
                dbms_id: '',
                schema_name: 'none',
                schema_id: '',
                table_id: '',
                table_nm: '',
                column_nm: '',
                user_id: $cookies.get('USER_ID'),
                currentPage: 1,
                perPage: 5,
            },

            tableColumnData: {
                table_id: '',
                user_id: $cookies.get('USER_ID'),
                currentPage: 1,
                perPage: 5,
            },

            //검색결과 페이징
            searchResultCount: 0,
            searchResultIdx: 0,
            //테이블 컬럼 페이징
            tableColumnListCount: 0,
            tableColumnListIdx: 0,

            selectedTable: null, // 선택된 Table 인덱스를 저장하는 변수
            selectedCol: null,

            // 상세 데이터 모달창 용 데이터
            isModalOpen: false,
            nullCount: null,
            spaceCount: null,
            minCount: null,
            maxCount: null,
            minCount2: null,
            maxCount2: null,
            minCount3: null,
            maxCount3: null,
            minLength: null,
            maxLength: null,
        };
    },
    methods: {
        selectDbms: function () {
            const vm = this;
            if (vm.$checkLogin() == true) {
                return;
            }
            axios({
                url: 'selectDbms.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.searchResultData,
            })
                .then(function (response) {
                    vm.dbmsOption = response.data;
                })
                .catch(function (error) {
                    vm.$showAlert('error', 'selectDbms 오류');
                });
        },

        selectSchema: function () {
            const vm = this;
            axios({
                url: 'selectSchema.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.previousData,
            })
                .then(function (response) {
                    vm.schemaOption = response.data;
                })
                .catch(function (error) {
                    vm.$showAlert('error', 'selectSchema 오류');
                });
        },

        selectSearchResult: function () {
            const vm = this;
            axios({
                url: 'selectSearchResult.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.searchResultData,
            })
                .then(function (response) {
                    vm.searchResultList = response.data.table;
                    vm.searchResultCount = response.data.count;
                    vm.searchResultIdx =
                        vm.searchResultCount - (vm.searchResultData.currentPage - 1) * vm.searchResultData.perPage;
                })
                .catch(function (error) {
                    vm.$showAlert('error', 'selectSearchResult 오류');
                });
        },

        selectTableColumn: function () {
            const vm = this;
            axios({
                url: 'selectTableColumn.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.tableColumnData,
            })
                .then(function (response) {
                    vm.tableColumnList = response.data.table1;
                    vm.tableColumnListCount = response.data.count1;
                    vm.tableColumnListIdx =
                        vm.tableColumnListCount - (vm.tableColumnData.currentPage - 1) * vm.tableColumnData.perPage;
                })
                .catch(function (error) {
                    vm.$showAlert('error', 'selectTableColumn 오류');
                });
        },

        // 컬럼 분석
        analyzeColumn: function () {
            const vm = this;
            if (vm.$checkLogin() == true) {
                return;
            }
            const selectedResult = vm.searchResultList.find((result, index) => vm.checkboxList[index]);
            vm.checkboxList = [];

            if (!selectedResult) {
                vm.$showAlert('error', '컬럼 분석할 테이블을 선택해야 합니다.');
                return;
            }

            const { dbms_id, schema_name, table_nm, table_id } = selectedResult;

            axios({
                url: 'analyzeColumn.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: {
                    dbms_id: dbms_id,
                    schema_name: schema_name,
                    table_nm: table_nm,
                    table_id: table_id,
                },
            })
                .then(function () {
                    vm.$showAlert('컬럼 분석 완료', table_nm + ' 테이블의 컬럼 분석이 완료되었습니다.');
                })
                .catch(function (error) {
                    vm.$showAlert('컬럼 분석 실패', '테이블의 컬럼 분석에 실패하였습니다.');
                });
        },

        // 컬럼 분석 데이터 가져오기
        selectColumnAnl: function (index) {
            const vm = this;
            axios({
                url: 'selectColumnAnl.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.tableColumnList[index],
            })
                .then(function (response) {
                    vm.selectedColumn = response.data;
                    vm.selectedColumn.sort((a, b) => b.column_anl_count - a.column_anl_count);
                    vm.computeStats();
                })
                .catch(function (error) {
                    vm.$showAlert('error', '테이블의 컬럼 분석 데이터 조회 실패');
                });
        },

        // 상세 데이터 확인
        computeStats: function () {
            const vm = this;

            vm.nullCount = 0;
            vm.spaceCount = 0;
            vm.minCount = null;
            vm.maxCount = null;
            vm.minCount2 = null;
            vm.maxCount2 = null;
            vm.minCount3 = null;
            vm.maxCount3 = null;
            vm.minLength = null;
            vm.maxLength = null;

            const colLengths = vm.selectedColumn
                .map((item) => {
                    if (typeof item.column_anl_value === 'string') {
                        return item.column_anl_value.length;
                    } else if (typeof item.column_anl_value === 'number') {
                        return String(item.column_anl_value).length;
                    }
                    return null;
                })
                .filter((value) => value !== null);

            vm.minLength = colLengths.length > 0 ? Math.min(...colLengths) : null;
            vm.maxLength = colLengths.length > 0 ? Math.max(...colLengths) : null;

            vm.nullCount = vm.selectedColumn.filter((item) => item.column_anl_value === null).length;

            vm.spaceCount = vm.selectedColumn.filter((item) => {
                return typeof item.column_anl_value === 'string' && /\s/.test(item.column_anl_value);
            }).length;

            const numData = vm.selectedColumn
                .filter((item) => typeof item.column_anl_value === 'number')
                .map((item) => item.column_anl_value);
            vm.minCount = numData.length > 0 ? Math.min(...numData) : null;
            vm.maxCount = numData.length > 0 ? Math.max(...numData) : null;

            numData.sort((a, b) => a - b);
            vm.minCount2 = numData.length > 1 ? numData[1] : null;
            vm.maxCount2 = numData.length > 1 ? numData[numData.length - 2] : null;
            vm.minCount3 = numData.length > 2 ? numData[2] : null;
            vm.maxCount3 = numData.length > 2 ? numData[numData.length - 3] : null;

            const strData = vm.selectedColumn
                .filter((item) => typeof item.column_anl_value === 'string')
                .map((item) => item.column_anl_value);

            if (strData.length > 0) {
                const sortedStrData = strData.slice().sort((a, b) => a.length - b.length);
                vm.minCount = sortedStrData[0];
                vm.maxCount = sortedStrData[sortedStrData.length - 1];
                vm.minCount2 = sortedStrData.length > 1 ? sortedStrData[1] : null;
                vm.maxCount2 = sortedStrData.length > 1 ? sortedStrData[sortedStrData.length - 2] : null;
                vm.minCount3 = sortedStrData.length > 2 ? sortedStrData[2] : null;
                vm.maxCount3 = sortedStrData.length > 2 ? sortedStrData[sortedStrData.length - 3] : null;
            }

            vm.columnsCount = vm.selectedColumn.reduce((sum, item) => sum + item.column_anl_count, 0);
        },

        tableSelect: function (table_id) {
            // 검색 결과에서 이미 선택한 행을 다시 선택했을 경우
            if (table_id == this.tableColumnData.table_id) {
                return;
            }
            this.tableColumnData.currentPage = 1; // 행을 클릭시 테이블 컬럼 목록 결과를 1페이지 부터 나오도록 하기 위함
            this.tableColumnData.table_id = table_id;
            this.selectTableColumn();
            this.selectedColumn = [];
        },

        // table_id 확인
        isSelectedRow(tableId) {
            return tableId === this.tableColumnData.table_id;
        },

        isSelectedCol(colId) {
            return colId === this.selectedCol;
        },

        isSelected(index) {
            // 현재 인덱스가 선택된 인덱스인지 확인
            return this.selectedTable === index;
        },

        handleClick(index) {
            // 체크박스 클릭 시 실행되는 메서드
            if (this.selectedTable === index) {
                this.selectedTable = null;
            } else {
                this.selectedTable = index;
            }
        },

        openModal: function () {
            const vm = this;
            if (!vm.selectedColumn || vm.selectedColumn.length === 0) {
                vm.$showAlert('error', '상세 확인할 데이터가 없습니다. 컬럼 분석을 먼저 실행해 주세요.');
            } else {
                vm.isModalOpen = true;
            }
        },
        closeModal: function () {
            this.isModalOpen = false;
        },

        setPage: function () {
            if (this.$checkLogin() == true) {
                return;
            }
            if (this.previousData.dbms_name === 'none') {
                this.$showAlert('deny', 'DBMS를 선택해주세요.');
                return;
            }
            if (this.previousData.schema_name === 'none') {
                this.$showAlert('deny', '스키마를 선택해주세요.');
                return;
            }
            this.searchResultData = { ...this.previousData };
            this.searchResultData.currentPage = 1;
            this.selectSearchResult();
        },

        // 페이지네이션 클릭 이벤트
        handlePageChange(pageNumber) {
            this.currentPage = pageNumber;
        },
    },
    watch: {
        'previousData.dbms_name'() {
            this.previousData.schema_name = 'none';
            this.selectSchema();
        },
        'previousData.schema_name'() {
            this.tableColumnList = ''; //컬럼 초기화
            this.tableColumnData.table_id = 0; //선택된 테이블 초기화
            this.tableColumnData.currentPage = 1; //컬럼 목록 페이지네이션
            this.selectedCol = 0; //선택된 컬럼 초기화
            this.selectTableColumn();
        },

        'searchResultData.currentPage': function () {
            this.checkboxList = [];
            this.selectedIndex = [];
            this.selectSearchResult();
        },

        'tableColumnData.currentPage': function () {
            this.selectTableColumn();
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
        this.selectDbms();
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

.table-container {
    overflow: auto;
    max-height: 620px;
}
.columnManageTable tr td {
    display: flex;
}
.columnManageTable tr td select,
input {
    width: 100%;
}
</style>
