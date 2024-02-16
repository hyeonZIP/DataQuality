<template>
    <div class="container">
        <div class="page-titleZone flex justify-between">
            <p class="main-title">진단대상관리 > 진단대상 테이블 관리</p>
            <PageNavigation />
        </div>
        <div class="content">
            <div class="row">
                <!-- 검색 조건 -->
                <div class="content-titleZone">
                    <p class="box-title">> 검색조건</p>
                </div>
                <div class="searchbar-zone">
                    <div class="flex justify-start align-center">
                        <span class="option-title">DBMS명</span>
                        <select class="square-select" v-model="selectDbms" @change="selectSchemaName">
                            <option value="" disabled selected>-- DBMS명 --</option>
                            <option value="all">전체</option>
                            <option
                                v-for="db in dbNameList"
                                :key="db.dbms_id"
                                :value="{ dbms_id: db.dbms_id, dbms_name: db.dbms_name }"
                            >
                                {{ db.dbms_name }}
                            </option>
                        </select>

                        <select class="square-select" v-model="selectSchema" @change="updateSearchButton">
                            <option value="" disabled selected>-- 스키마명 --</option>
                            <option :value="{ dbms_id: selectDbms.dbms_id }">전체</option>
                            <option
                                v-for="sc in schemaNameList"
                                :key="sc.schema_id"
                                :value="{ schema_id: sc.schema_id, schema_name: sc.schema_name }"
                            >
                                {{ sc.schema_name }}
                            </option>
                        </select>

                        <button class="blue-btn small-btn" @click="selectTableList">검색</button>
                    </div>
                </div>
                <!-- 제외 사유 -->
                <div style="margin-top: 30px">
                    <p class="box-title">> 제외 사유 일괄 지정</p>
                    <div class="exceptExplain">
                        <span class="option-title">제외 사유</span>
                        <div class="search-square">
                            <input type="text" class="square-input" placeholder="제외 사유" v-model="update_explain" />
                            <button class="square-button">
                                <svg-icon type="mdi" :path="searchPath" class="square-icon"></svg-icon>
                            </button>
                        </div>
                        <button class="red-btn small-btn" @click="handleExplainAdd">일괄 추가</button>
                        <button class="red-btn small-btn" @click="handleExplainDelete">일괄 삭제</button>
                    </div>
                </div>
                <!-- 테이블 조회 -->
                <div class="table-zone">
                    <div class="flex justify-end" style="margin-top: -40px">
                        <button class="red-btn variable-btn" @click="handleTableUpdate">제외 여부, 사유 저장</button>
                    </div>
                    <table class="list-table" style="text-align: center">
                        <colgroup>
                            <col width="5%" />
                            <col width="10%" />
                            <col width="15%" />
                            <col width="15%" />
                            <col width="15%" />
                            <col width="15%" />
                            <col width="25%" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>제외 여부</th>
                                <th>DBMS</th>
                                <th>스키마 명</th>
                                <th>테이블 명</th>
                                <th>테이블 한글명</th>
                                <th>제외사유</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(tb, index) in tableList" :key="tb.table_id">
                                <td>{{ index + 1 + (currentPage - 1) * perPage }}</td>
                                <td>
                                    <input type="checkbox" v-model="selectedTableIds" :value="tb.table_id" />
                                </td>
                                <td>{{ tb.dbms_name }}</td>
                                <td>{{ tb.schema_name }}</td>
                                <td>{{ tb.table_nm }}</td>
                                <td>{{ tb.table_nm_k }}</td>
                                <td>{{ tb.updatedExplain !== undefined ? tb.updatedExplain : tb.table_explain }}</td>
                            </tr>
                        </tbody>
                    </table>
                    <p class="noTableMsg" v-if="tableList && tableList.length === 0">수집한 테이블이 없습니다.</p>
                    <PaginationButton
                        v-model:currentPage="currentPage"
                        :perPage="perPage"
                        :totalCount="totalCount"
                        :maxRange="5"
                        :click="handlePageChange"
                    />
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios';
import PageNavigation from '../../component/PageNavigation.vue';
import PaginationButton from '../../component/PaginationButton.vue';
import SvgIcon from '@jamescoyle/vue-icon';

export default {
    data() {
        return {
            user_id: $cookies.get('USER_ID'),

            dbNameList: [],
            selectDbms: '', // 선택한 DB의 id 값과 name 저장

            schemaNameList: [],
            selectSchema: '', // 선택한 Schema의 id 값과 name 저장

            tableList: [], //수집한 테이블 리스트
            tableInfo: '', //테이블의 Dbms_name과 schema_name 저장

            selectedTableIds: [], //선택된 테이블의 ID 저장
            update_explain: '', //입력할 제외 사유
            update_state: '', //일괄 추가 또는 일괄 삭제 상태

            updateTable: [], //변경할 테이블 목록

            currentPage: 1,
            perPage: 20,
            totalCount: 0,
            tableIdx: 0,

            // 검색 버튼을 클릭시 1페이지에서 시작하도록 설정하기 위해 사용(true이면 무조건 1페이지부터, false면 해당 페이지)
            searchButton: true, // 검색 버튼을 클릭하면 true, 검색버튼이 아닌 추가 하거나 삭제 할때는 false
        };
    },
    methods: {
        // 검색버튼 클릭시 현재페이지를 1페이지부터 보여줄수있도록 설정
        updateSearchButton() {
            this.searchButton = true;
        },
        // 검색 - DBMS명 조회
        selectDbName() {
            const vm = this;
            axios({
                url: '/selectDbmsName.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: { user_id: vm.user_id },
            })
                .then((res) => {
                    vm.dbNameList = res.data;
                })
                .catch((err) => {
                    vm.$showAlert('error', 'DBMS명 조회 에러');
                });
        },

        // 검색 - 스키마명 조회
        selectSchemaName() {
            const vm = this;
            vm.searchButton = true;
            axios({
                url: '/selectSchemaName.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: { dbms_id: vm.selectDbms.dbms_id },
            })
                .then((res) => {
                    vm.schemaNameList = res.data;
                })
                .catch((err) => {
                    vm.$showAlert('error', 'Schema명 조회 에러');
                });
        },

        // 검색 - 테이블 조회
        selectTableList() {
            const vm = this;
            if (vm.$checkLogin() == true) {
                return;
            }

            if (!vm.selectDbms.dbms_id && vm.selectSchema.schema_id) {
                vm.$showAlert('error', '스키마를 선택해주세요');
                return;
            }

            if (vm.searchButton) {
                vm.currentPage = 1;
            }
            const data = {
                currentPage: vm.currentPage,
                perPage: vm.perPage,
                user_id: vm.user_id,
                schema_id: vm.selectSchema.schema_id,
                dbms_id: vm.selectDbms.dbms_id,
            };

            axios({
                url: '/selectTableList.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: {
                    currentPage: vm.currentPage,
                    perPage: vm.perPage,
                    user_id: vm.user_id,
                    schema_id: vm.selectSchema.schema_id,
                    dbms_id: vm.selectSchema.dbms_id,
                },
            })
                .then((res) => {
                    vm.tableList = res.data.tableList;
                    vm.totalCount = res.data.tableListCount;
                    vm.tableInfo = {
                        schema_name: vm.selectSchema.schema_name,
                        dbms_name: vm.selectDbms.dbms_name,
                    };
                    vm.tableIdx = vm.totalCount - (vm.currentPage - 1) * vm.perPage;
                })
                .catch((err) => {
                    vm.$showAlert('error', '테이블 조회 실패');
                });
        },

        // 일괄 추가 버튼
        handleExplainAdd() {
            const vm = this;
            if (vm.$checkLogin() == true) {
                return;
            }

            if (vm.selectedTableIds.length === 0) {
                vm.$showAlert('error', '테이블을 선택해주세요.');
                return;
            }

            if (vm.update_explain === '') {
                vm.$showAlert('error', '제외사유를 입력하세요.');
                // 제외 사유 입력칸으로 커서 이동
                const inputElement = document.querySelector('.square-input');
                if (inputElement) {
                    inputElement.focus();
                }
                return;
            }

            vm.updateTable.push({
                updateTableId: vm.selectedTableIds,
                state: true,
                update_explain: vm.update_explain,
            });

            // 선택된 테이블에 새로운 설명을 적용
            vm.tableList.forEach((table) => {
                if (vm.selectedTableIds.includes(table.table_id)) {
                    table.updatedExplain = vm.update_explain;
                }
            });

            vm.selectedTableIds = []; //선택된 id 초기화
            vm.update_explain = '';
        },

        // 일괄 삭제 버튼
        handleExplainDelete() {
            const vm = this;
            if (vm.$checkLogin() == true) {
                return;
            }

            if (vm.selectedTableIds.length === 0) {
                vm.$showAlert('error', '테이블을 선택해주세요.');
                return;
            }

            vm.update_explain = '';

            vm.updateTable.push({
                updateTableId: vm.selectedTableIds,
                state: false,
                update_explain: vm.update_explain,
            });

            // 선택된 테이블에 빈 문자열을 적용
            vm.tableList.forEach((table) => {
                if (vm.selectedTableIds.includes(table.table_id)) {
                    table.updatedExplain = '';
                }
            });

            vm.selectedTableIds = []; //선택된 id 초기화
            vm.update_explain = '';
        },

        // 제외 여부, 사유 저장
        handleTableUpdate() {
            const vm = this;
            if (vm.$checkLogin() == true) {
                return;
            }
            vm.searchButton = false;
            axios({
                url: 'updateTableData.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: { data: vm.updateTable },
            })
                .then((res) => {
                    vm.$showAlert('response', '제외 여부 및 사유 저장 완료');
                    vm.searchButton = false;
                    vm.selectTableList();
                })
                .catch((err) => {
                    vm.$showAlert('error', '제외 여부 및 사유 저장 실패');
                });

            vm.selectTableList();
        },

        // 페이지네이션 클릭 이벤트
        handlePageChange(pageNumber) {
            this.currentPage = pageNumber;
        },
    },
    watch: {
        currentPage(newPage, oldPage) {
            if (newPage !== oldPage) {
                this.searchButton = false;
                this.selectTableList(); // 페이지가 변경될 때 데이터를 가져오는 메서드 호출
            }
        },
    },
    computed: {
        PaginationButton: PaginationButton,
    },
    mounted() {
        if (this.$checkLogin() == true) {
            return;
        }
        this.selectTableList();
        this.selectDbName();
    },
    components: {
        PageNavigation: PageNavigation,
        PaginationButton: PaginationButton,
        SvgIcon: SvgIcon,
    },
};
</script>
<style>
.table-searchBtn {
    background-color: #dbe3fb;
    padding: 5px 10px;
    border-radius: 10px;
    color: #213f9f;
}
.noTableMsg {
    text-align: center;
    font-size: 15px;
    margin: 150px 0px;
}
.exceptExplain {
    display: flex;
    align-items: center;
    margin-top: 15px;
}
</style>
