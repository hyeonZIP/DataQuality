<template>
    <div class="container">
        <div class="page-titleZone flex justify-between">
            <p class="main-title">진단대상관리 > 진단대상DB관리</p>
            <PageNavigation />
        </div>
        <div class="content">
            <div class="row">
                <div class="content-titleZone">
                    <p class="box-title">>검색조건</p>
                </div>
                <div class="searchbar-zone">
                    <div class="flex justify-start align-center">
                        <span class="option-title">DBMS유형</span>
                        <select v-model="dbListSearch.dbmsType" class="square-select">
                            <option value="all">전체</option>
                            <option value="PostgreSQL">PostgreSQL</option>
                        </select>
                        <span class="option-title">DBMS명</span>
                        <div class="search-square">
                            <input
                                v-model="dbListSearch.searchText"
                                type="text"
                                class="square-input"
                                placeholder="DBMS명"
                            />
                            <button @click="selectDbList" class="square-button">
                                <svg-icon type="mdi" :path="searchPath" class="square-icon"></svg-icon>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="table-zone">
                    <div class="flex justify-start" style="margin: 10px 0px">
                        <button class="blue-btn small-btn" @click="deleteDbData()">선택항목 삭제</button>
                        <button class="blue-btn small-btn" @click="updateDbData()">선택항목 수정</button>
                        <button class="blue-btn small-btn" @click="connectDb()">접속테스트</button>
                        <button class="blue-btn small-btn" @click="collectSchema()">스키마수집</button>
                    </div>
                    <table class="list-table">
                        <colgroup>
                            <col style="width: 5%" />
                            <col style="width: 5%" />
                            <col style="width: 5%" />
                            <col style="width: 8%" />
                            <col style="width: 8%" />
                            <col style="width: 5%" />
                            <col style="width: 12%" />
                            <col style="width: 12%" />
                            <col style="width: 5%" />
                            <col style="width: 5%" />
                            <col style="width: 5%" />
                            <col style="width: 5%" />
                            <col style="width: 20%" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>선택</th>
                                <th>연결상태</th>
                                <th>*DBMS명</th>
                                <th>*DBMS유형</th>
                                <th>DBMS버전</th>
                                <th>*연결 URL</th>
                                <th>*드라이버명</th>
                                <th>*DB접속계정ID</th>
                                <th>*DB접속계정암호</th>
                                <th>*기관명</th>
                                <th>*정보시스템명</th>
                                <th>설명</th>
                            </tr>
                        </thead>
                        <tbody class="dbData">
                            <tr
                                v-for="(db, index) in dbList"
                                :key="index"
                                @mouseover="() => handleSchemaClick(index)"
                                @click="selectSchemaList"
                            >
                                <td>{{ index + 1 }}</td>
                                <td>
                                    <input type="checkbox" :checked="isSelected(index)" @click="handleClick(index)" />
                                </td>
                                <td>{{ getConnectStatus(db.dbms_connect) }}</td>
                                <td><input type="text" v-model="db.dbms_name" /></td>
                                <td>
                                    <select v-model="db.dbms_type" @change="updateDriveNm(index)">
                                        <option value="PostgreSQL">PostgreSQL</option>
                                        <option value="MariaDB">MariaDB</option>
                                    </select>
                                </td>
                                <td>{{ db.dbms_version }}</td>
                                <td>
                                    <input type="text" v-model="db.dbms_url_port" style="width: 300px" />
                                </td>
                                <td><input type="text" v-model="db.dbms_drive_nm" /></td>
                                <td><input type="text" v-model="db.dbms_connect_id" /></td>
                                <td><input type="password" v-model="db.dbms_connect_pw" /></td>
                                <td><input type="text" v-model="db.dbms_ag_nm" /></td>
                                <td><input type="text" v-model="db.dbms_system_nm" /></td>
                                <td>
                                    <input type="text" v-model="db.dbms_explain" style="width: 300px" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" style="text-align: left">
                                    <button class="blue-btn small-btn" @click="insertDbData()">신규 DB 추가</button>
                                </td>
                                <td><input type="text" v-model="dbInput.dbms_name" /></td>
                                <td>
                                    <select v-model="dbInput.dbms_type" @change="updateDriveNm_input">
                                        <option value="" disabled selected>선택</option>
                                        <option value="PostgreSQL">PostgreSQL</option>
                                    </select>
                                </td>
                                <td></td>
                                <td>
                                    <input type="text" v-model="dbInput.dbms_url_port" style="width: 300px" />
                                </td>
                                <td><input type="text" v-model="dbInput.dbms_drive_nm" /></td>
                                <td><input type="text" v-model="dbInput.dbms_connect_id" /></td>
                                <td>
                                    <input type="password" v-model="dbInput.dbms_connect_pw" />
                                </td>
                                <td><input type="text" v-model="dbInput.dbms_ag_nm" /></td>
                                <td><input type="text" v-model="dbInput.dbms_system_nm" /></td>
                                <td>
                                    <input type="text" v-model="dbInput.dbms_explain" style="width: 300px" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <PaginationButton
                    v-model:currentPage="dbListSearch.currentPage"
                    :perPage="dbListSearch.perPage"
                    :totalCount="dbListCount"
                    :maxRange="5"
                />
            </div>
            <div class="content-titleZone">
                <p class="box-title">DBMS 스키마 정보</p>
            </div>
            <div class="table-zone">
                <table class="list-table" style="text-align: center">
                    <colgroup>
                        <col width="5%" />
                        <col width="10%" />
                        <col width="20%" />
                        <col width="20%" />
                        <col width="40%" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>수집 제외</th>
                            <th>DB 접속 대상</th>
                            <th>스키마명</th>
                            <th>설명</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item, index) in schemaList" :key="item.schema_id">
                            <td>{{ index + 1 }}</td>
                            <td>
                                <input type="checkbox" v-model="selectedSchemaIds" :value="item.schema_id" />
                            </td>
                            <td>{{ item.dbms_name }}</td>
                            <td>{{ item.schema_name }}</td>
                            <td style="display: flex">
                                <input
                                    type="text"
                                    :placeholder="
                                        selectedSchemaIds.includes(item.schema_id) ? item.schema_explain : null
                                    "
                                    :disabled="!selectedSchemaIds.includes(item.schema_id)"
                                    v-model="item.updatedSchemaExplain"
                                    style="width: 100%"
                                />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="flex justify-end">
                    <button class="red-btn variable-btn" @click="setSchema()">수집제외 여부, 설명 저장</button>
                </div>
            </div>
            <PaginationButton
                v-model:currentPage="schemaCurrentPage"
                :perPage="schemaPerPage"
                :totalCount="schemaTotalCount"
                :maxRange="5"
                :click="handlePageChange"
            />
        </div>
    </div>
</template>

<script>
import PageNavigation from '../../component/PageNavigation.vue';
import PaginationButton from '../../component/PaginationButton.vue';
import svgIcon from '@jamescoyle/vue-icon';
import { mdiMagnify } from '@mdi/js';
import axios from 'axios';

export default {
    data() {
        return {
            searchPath: mdiMagnify,
            inputValue: null,
            dbList: [],
            dbInput: {
                dbms_name: '',
                dbms_type: '',
                dbms_version: '',
                dbms_url_port: '',
                dbms_drive_nm: '',
                dbms_connect: '',
                dbms_connect_id: '',
                dbms_connect_pw: '',
                dbms_ag_nm: '',
                dbms_system_nm: '',
                dbms_explain: '',
                user_id: $cookies.get('USER_ID'),
            },
            selectedDb: null, // 선택된 DB의 인덱스를 저장하는 변수
            selectedSchema: null, // 선택된 스키마 행의 인덱스를 저장하는 변수
            userName: $cookies.get('USER'),

            selectedDbIndex: null,
            schemaList: [],

            dbListSearch: {
                currentPage: 1,
                perPage: 10,
                user_id: $cookies.get('USER_ID'),
                dbmsType: 'all',
                searchText: null,
            },
            dbListCount: 0,
            dbIdx: 0,

            /* ---------- 스키마 목록 --------- */
            schemaCurrentPage: 1,
            schemaPerPage: 10,
            schemaTotalCount: 0,
            schemaIndex: 0,

            selectedSchemaIds: [], //선택한 스키마 id 배열
            update_schemaList: [], // 업데이트할 스키마 목록
            update_schemaExplain: '',
        };
    },
    methods: {
        // db유형마다 다른 driver명이 되도록
        updateDriveNm_input() {
            if (this.dbInput.dbms_type === 'PostgreSQL') {
                this.dbInput.dbms_url_port = 'jdbc:postgresql://{ip}:{port}/{db명}';
                this.dbInput.dbms_drive_nm = 'org.postgresql.Driver';
            }
        },
        updateDriveNm(index) {
            if (this.dbList[index].dbms_type === 'PostgreSQL') {
                this.dbList[index].dbms_url_port = 'jdbc:postgresql://{ip}:{port}/{db명}';
                this.dbList[index].dbms_drive_nm = 'org.postgresql.Driver';
            }
        },
        // 연결 상태 확인
        getConnectStatus(connect) {
            switch (connect) {
                case '0':
                    return '연결 필요';
                case '1':
                    return '연결 성공';
            }
        },
        isSelected(index) {
            // 현재 인덱스가 선택된 인덱스인지 확인
            return this.selectedDb === index;
        },
        handleClick(index) {
            // 체크박스 클릭 시 실행되는 메서드
            if (this.selectedDb === index) {
                this.selectedDb = null;
            } else {
                this.selectedDb = index;
            }
        },
        handleSchemaClick(index) {
            this.selectedSchema = index;
        },
        insertDbData: function () {
            const vm = this;
            if (vm.$checkLogin() == true) {
                return;
            }
            const inputData = vm.dbInput;
            // 유효성 검사
            if (
                !vm.dbInput.dbms_name ||
                !vm.dbInput.dbms_type ||
                !vm.dbInput.dbms_url_port ||
                !vm.dbInput.dbms_drive_nm ||
                !vm.dbInput.dbms_connect_id ||
                !vm.dbInput.dbms_connect_pw ||
                !vm.dbInput.dbms_ag_nm ||
                !vm.dbInput.dbms_system_nm
            ) {
                if (!vm.dbInput.dbms_name) {
                    vm.$showAlert('error', 'DBMS명은 반드시 입력해야 합니다.');
                    vm.$refs.dbms_name.focus();
                } else if (!vm.dbInput.dbms_type) {
                    vm.$showAlert('error', 'DBMS유형은 반드시 선택해야 합니다.');
                    vm.$refs.dbms_type.focus();
                } else if (!vm.dbInput.dbms_url_port) {
                    vm.$showAlert('error', '연결 URL은 반드시 입력해야 합니다.');
                    vm.$refs.dbms_url_port.focus();
                } else if (!vm.dbInput.dbms_drive_nm) {
                    vm.$showAlert('error', '드라이버명은 반드시 입력해야 합니다.');
                    vm.$refs.dbms_drive_nm.focus();
                } else if (!this.dbInput.dbms_connect_id) {
                    vm.$showAlert('error', 'DB접속계정ID는 반드시 입력해야 합니다.');
                    vm.$refs.dbms_connect_id.focus();
                } else if (!vm.dbInput.dbms_connect_pw) {
                    vm.$showAlert('error', 'DB접속계정암호는 반드시 입력해야 합니다.');
                    vm.$refs.dbms_connect_pw.focus();
                } else if (!vm.dbInput.dbms_ag_nm) {
                    vm.$showAlert('error', '기관명은 반드시 입력해야 합니다.');
                    vm.$refs.dbms_ag_nm.focus();
                } else if (!vm.dbInput.dbms_system_nm) {
                    vm.$showAlert('error', '정보시스템명은 반드시 입력해야 합니다.');
                    vm.$refs.dbms_system_nm.focus();
                }

                return;
            }
            axios({
                url: 'insertDbData.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: inputData,
            })
                .then(function (response) {
                    vm.selectDbList();
                })
                .catch(function (error) {
                    vm.$showAlert('error', 'insertDbData 오류');
                });
        },
        selectDbList: function () {
            const vm = this;
            if (vm.$checkLogin() == true) {
                return;
            }
            axios({
                url: 'selectDbList.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.dbListSearch,
            })
                .then(function (response) {
                    vm.selectedDb = null;
                    vm.dbList = response.data.dbList;
                    vm.dbListCount = response.data.dbListCount;
                    vm.dbIdx = vm.dbListCount - (vm.dbListSearch.currentPage - 1) * vm.dbListSearch.perPage;
                })
                .catch(function (error) {
                    vm.$showAlert('error', 'selectDbList 오류');
                });
        },

        connectDb: function () {
            const vm = this;

            if (vm.$checkLogin() == true) {
                return;
            }
            if (vm.selectedDb == null) {
                vm.$showAlert('error', '접속 테스트할 DB를 선택해주세요!');
                return;
            }

            axios({
                url: 'connectDb.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.dbList[vm.selectedDb],
            })
                .then(function (response) {
                    if (response.data == 1) {
                        vm.$showAlert('response', 'DB 접속이 성공되었습니다!');
                        vm.selectDbList();
                    } else {
                        vm.$showAlert('error', '접속이 실패했습니다! DB 정보를 다시 확인해주세요!');
                    }
                })
                .catch(function (error) {
                    vm.$showAlert('error', '접속이 실패했습니다! DB 정보를 다시 확인해주세요');
                    vm.selectDbList();
                });
        },

        collectSchema: function () {
            const vm = this;

            if (vm.$checkLogin() == true) {
                return;
            }
            if (vm.selectedDb == null) {
                vm.$showAlert('error', '스키마를 수집할 DB를 선택해주세요!');
                return;
            }
            if (vm.dbList[vm.selectedDb].dbms_connect != 1) {
                vm.$showAlert('error', '접속 테스트가 완료된 DB를 선택해주세요!');
                return;
            }
            vm.$showAlert('progress', '스키마 수집중 입니다.');

            axios({
                url: 'collectSchema.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.dbList[vm.selectedDb],
            })
                .then(function (response) {
                    if (response.data == 1) {
                        vm.$showAlert('success', '스키마 수집이 성공되었습니다!');
                        vm.selectDbList();
                    }
                })
                .catch(function (error) {
                    vm.$showAlert('error', '스키마 수집이 실패했습니다! DB 정보를 다시 확인해주세요');
                });
        },

        deleteDbData: function () {
            const vm = this;
            if (vm.$checkLogin() == true) {
                return;
            }
            if (vm.selectedDb == null) {
                vm.$showAlert('error', '삭제할 DB를 선택해주세요!');
                return;
            }

            axios({
                url: 'deleteDbData.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.dbList[vm.selectedDb],
            })
                .then(function (response) {
                    vm.$showAlert('response', 'DB가 삭제되었습니다!');
                    vm.selectDbList();
                })
                .catch(function (error) {
                    vm.selectDbList();
                });
        },

        // DB 데이터 수정
        updateDbData: function () {
            const vm = this;
            if (vm.$checkLogin() == true) {
                return;
            }
            if (vm.selectedDb == null) {
                vm.$showAlert('error', '수정할 DB를 선택해주세요!');
                return;
            }

            axios({
                url: 'updateDbData.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.dbList[vm.selectedDb],
            })
                .then(function (response) {
                    vm.$showAlert('response', 'DB가 수정되었습니다! 다시 접속 테스트를 부탁드립니다 !');
                    vm.selectDbList();
                })
                .catch(function (error) {
                    vm.selectDbList();
                });
        },

        //스키마 정보 수집
        selectSchemaList() {
            const vm = this;
            if (vm.dbList && vm.dbList.length > vm.selectedSchema) {
                vm.selectedDbIndex = vm.dbList[vm.selectedSchema].dbms_id;
            }

            axios({
                url: '/selectSchemaList.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: {
                    currentPage: vm.schemaCurrentPage,
                    perPage: vm.schemaPerPage,
                    dbms_id: vm.selectedDbIndex,
                },
            })
                .then((res) => {
                    // 수집 제외할 스키마 id
                    const checkedSchemaIds = res.data.schemaList
                        .filter((item) => item.schema_yn === 'n')
                        .map((item) => item.schema_id);

                    vm.selectedSchemaIds = checkedSchemaIds;

                    vm.schemaList = res.data.schemaList.map((item) => {
                        // explain을 수정하지 않는 경우
                        if (item.schema_yn === 'n' && !item.updatedSchemaExplain) {
                            item.updatedSchemaExplain = item.schema_explain;
                        }
                        return item;
                    });

                    vm.schemaTotalCount = res.data.schemaListCount;
                    vm.schemaIndex = vm.schemaTotalCount - (vm.schemaCurrentPage - 1) * vm.schemaPerPage;
                })
                .catch((err) => {
                    vm.$showAlert('error', '스키마 수집 에러');
                });
        },

        /* 수정 정보 저장 하기 */
        setSchema() {
            const vm = this;
            if (vm.$checkLogin() == true) {
                return;
            }

            // 수집 제외 선택 후 설명 입력 안하면 경고창
            const hasUndefinedExplain = vm.schemaList.some(
                (item) =>
                    vm.selectedSchemaIds.includes(item.schema_id) &&
                    (item.updatedSchemaExplain === undefined || item.updatedSchemaExplain === null)
            );
            if (hasUndefinedExplain) {
                vm.$showAlert('error', '설명을 입력해주세요.');
                return;
            }

            vm.update_schemaList = vm.schemaList.map((item) => {
                //제외하지 않는 스키마의 설명 null로 설정
                if (!vm.selectedSchemaIds.includes(item.schema_id)) {
                    item.updatedSchemaExplain = null;
                }

                return {
                    schema_id: item.schema_id,
                    schema_yn: vm.selectedSchemaIds.includes(item.schema_id) ? 'n' : 'y',
                    schema_explain: item.updatedSchemaExplain,
                };
            });

            axios({
                url: '/updateSchema.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.update_schemaList,
            })
                .then((res) => {
                    if (res.data > 0) {
                        vm.$showAlert('response', '변경 내용 저장 완료');
                        vm.selectSchemaList();
                    }
                })
                .catch((err) => {
                    vm.$showAlert('error', '스키마 수정 에러');
                });
        },

        // 페이지네이션 클릭 이벤트
        handlePageChange(pageNumber) {
            this.schemaCurrentPage = pageNumber;
        },
    },

    watch: {
        'dbListSearch.currentPage': function () {
            this.selectDbList();
        },
        schemaCurrentPage(newPage, oldPage) {
            if (newPage !== oldPage) {
                this.selectSchemaList();
            }
        },
        selectedSchemaIds(newSelectedIds) {
            this.schemaList.forEach((item) => {
                if (!newSelectedIds.includes(item.schema_id)) {
                    item.updatedSchemaExplain = null;
                }
            });
        },
    },
    computed: {
        PaginationButton: PaginationButton,
    },
    components: {
        PageNavigation: PageNavigation,
        PaginationButton: PaginationButton,
        svgIcon: svgIcon,
    },
    mounted() {
        if (this.$checkLogin() == true) {
            return;
        }
        this.selectDbList();
    },
};
</script>
