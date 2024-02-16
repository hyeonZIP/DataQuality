<template>
    <div class="container">
        <div class="page-titleZone flex justify-between">
            <p class="main-title">진단 결과 > 품질진단종합현황</p>
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
                        </colgroup>
                        <thead class="totalRuleResultTable">
                            <tr>
                                <th>진단대상DBMS명</th>
                                <td style="display: flex; min-height: 64px; align-items: center">
                                    <select v-model="searchData.dbms_name">
                                        <option value="all">DBMS명</option>
                                        <option v-for="item in dbmsOption" :key="item">
                                            {{ item }}
                                        </option>
                                    </select>

                                    <select v-model="searchData.schema_name">
                                        <option value="all">스키마명</option>
                                        <option v-for="item in schemaOption" :key="item">
                                            {{ item }}
                                        </option>
                                    </select>
                                </td>
                                <th>테이블명</th>
                                <td>
                                    <input type="text" v-model="searchData.table_nm" />
                                </td>
                                <th>컬럼명</th>
                                <td><input type="text" v-model="searchData.column_nm" /></td>
                                <th>
                                    <button class="blue-btn small-btn" @click="selectTotalResult()">검색</button>
                                </th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>

            <div class="flex justify-between align-center" style="margin: 50px 5px 5px">
                <div class="count-zone">
                    <p>
                        선택한 DBMS명 :
                        <span>{{ totalResultList.dbms_name }}</span> 스키마명 :
                        <span>{{ totalResultList.schema_name }}</span> 테이블명 :
                        <span>{{ totalResultList.table_nm }}</span> 컬럼명 :
                        <span>{{ totalResultList.column_nm }}</span>
                    </p>
                </div>
            </div>

            <div class="table-zone">
                <table class="list-table">
                    <colgroup>
                        <col style="width: 5%" />
                        <col style="width: 20%" />
                        <col style="width: 20%" />
                        <col style="width: " />
                        <col style="width: " />
                        <col style="width: " />
                        <col style="width: " />
                        <col style="width: " />
                    </colgroup>
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>상위품질지표명</th>
                            <th>품질지표명</th>
                            <th>테이블수</th>
                            <th>컬럼수</th>
                            <th>전체건수</th>
                            <th>오류건수</th>
                            <th>오류율(%)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- 상위품질지표명 rowspan -->
                        <tr class="text-ct" v-for="(item, index) in totalResultList" :key="item.dqi_nm_high">
                            <td>{{ index + 1 }}</td>
                            <td
                                v-if="index === 0 || item.dqi_nm_high !== totalResultList[index - 1].dqi_nm_high"
                                :rowspan="getRowSpan(totalResultList, item)"
                            >
                                {{ item.dqi_nm_high }}
                            </td>
                            <td>{{ item.dqi_nm }}</td>
                            <td>{{ item.table_count }}</td>
                            <td>{{ item.column_count }}</td>
                            <td>{{ item.total_count }}</td>
                            <td>{{ item.error_count }}</td>
                            <td>{{ item.error_percent }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
import { mdiMagnify } from '@mdi/js';
import axios from 'axios';
export default {
    data() {
        return {
            searchPath: mdiMagnify,
            inputValue: null,

            //SELECT한 데이터 저장 리스트
            totalResultList: [],
            //옵션리스트
            dbmsOption: [],
            schemaOption: [],

            //검색 조건 데이터
            searchData: {
                user_id: $cookies.get('USER_ID'),
                dbms_name: 'all',
                schema_name: 'all',
                table_nm: '',
                column_nm: '',
            },
        };
    },
    methods: {
        /**
         * 내용 : 검색 조건 바탕으로 데이터 SELECT
         * 작성자 : 임재현
         * 시간 : 2024.01.31
         */
        selectTotalResult: function () {
            const vm = this;
            if (vm.$checkLogin() == true) {
                return;
            }
            axios({
                url: 'selectTotalResult.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.searchData,
            })
                .then(function (response) {
                    vm.totalResultList = response.data;
                })
                .catch(function (error) {
                    vm.$showAlert('error', 'selectTotalResult 오류');
                });
        },

        /**
         * 내용 : DBMS option을 user_id와 매칭시켜 가져옴
         * 작성자 : 임재현
         * 시간 : 2024.01.31
         */
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
                data: vm.searchData,
            })
                .then(function (response) {
                    vm.dbmsOption = response.data;
                })
                .catch(function (error) {
                    vm.$showAlert('error', 'selectDbms 오류');
                });
        },
        /**
         * 내용 : 선택한 DBMS_ID에 해당하는 스키마 SELECT함수 실행
         * 작성자 : 임재현
         * 시간 : 2024.01.31
         */
        selectSchema: function () {
            const vm = this;
            axios({
                url: 'selectSchema.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.searchData,
            })
                .then(function (response) {
                    vm.schemaOption = response.data;
                })
                .catch(function (error) {
                    vm.$showAlert('error', 'selectSchema 오류');
                });
        },
    },

    watch: {
        //dbmsOption이 선택되는지 감시
        'searchData.dbms_name'() {
            this.selectSchema();
        },
    },
    computed: {},
    components: {},
    mounted() {
        this.selectDbms();
        // this.selectTotalResult();
    },
};
</script>

<style scoped>
.totalRuleResultTable tr td select,
input {
    width: 100%;
}

.totalRuleResultTable th td {
    height: 40px; /* 테이블 셀의 높이 조절 */
}
</style>
