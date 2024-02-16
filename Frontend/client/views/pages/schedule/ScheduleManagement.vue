<template>
    <div class="container">
        <div class="page-titleZone flex justify-between">
            <p class="main-title">진단 실행 > 스케줄 실행 결과 조회</p>
        </div>
        <div class="content">
            <div class="row">
                <div class="content-titleZone">
                    <p class="box-title">> 검색조건</p>
                </div>
                <div class="table-zone">
                    <table class="list-table">
                        <colgroup>
                            <col style="width: 5%" />
                            <col style="width: " />
                            <col style="width: 5%" />
                            <col style="width: " />
                        </colgroup>
                        <thead class="schedulManageTable">
                            <tr>
                                <th>스케줄명</th>
                                <td><input type="text" v-model="searchData.schedule_nm" /></td>
                                <th>작업상태</th>
                                <td>
                                    <select v-model="searchData.task_state">
                                        <option value="all">선택</option>
                                        <option value="done">완료</option>
                                        <option value="doing">진행중</option>
                                        <option value="err">기타</option>
                                    </select>
                                </td>
                                <th :rowspan="2">
                                    <button class="blue-btn small-btn" @click="selectScheduleManager()">검색</button>
                                </th>
                            </tr>
                            <tr>
                                <th>기간</th>
                                <td>
                                    <select v-model="searchData.duration_date">
                                        <option value="all">전체</option>
                                        <option value="1">1일</option>
                                        <option value="3">3일</option>
                                        <option value="7">7일</option>
                                        <option value="30">1개월</option>
                                        <option value="90">3개월</option>
                                        <option value="180">6개월</option>
                                        <option value="over">6개월 이상</option>
                                    </select>
                                </td>
                                <th>조회기간</th>
                                <td style="display: flex; gap: 10px">
                                    <input type="date" v-model="searchData.search_start_tm" /> ~
                                    <input type="date" v-model="searchData.search_end_tm" />
                                </td>
                            </tr>
                        </thead>
                    </table>
                    <div class="flex justify-end">
                        <button class="blue-btn small-btn" @click="deleteSchedule()">결과 삭제</button>
                    </div>
                </div>
            </div>

            <div class="table-zone">
                <table class="list-table">
                    <colgroup>
                        <col style="width: 10%" />
                        <col style="width: 10%" />
                        <col style="width: 20%" />
                        <col style="width: 20%" />
                        <col style="width: 20%" />
                        <col style="width: 5%" />
                        <col style="width: 5%" />
                        <col style="width: 5%" />
                        <col style="width: 5%" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>선택</th>
                            <th>스케줄명</th>
                            <th>스케줄시작일시</th>
                            <th>스케줄종료일시</th>
                            <th>진행시간(초)</th>
                            <th>총건수</th>
                            <th>성공건수</th>
                            <th>실패건수</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr
                            v-for="(item, index) in scheduleManagerList"
                            :key="index"
                            @click="tableSelect(item.schedule_id)"
                        >
                            <td>
                                {{ index + 1 + (searchData.currentPage - 1) * searchData.perPage }}
                            </td>
                            <!-- manage_rule DB를 사용 할 시 체크박스에 저장될 인덱스는 사용할 DB의 인덱스 저장 -->
                            <td>
                                <input
                                    type="checkbox"
                                    v-model="checkboxList[index]"
                                    @change="checkboxWatcher(index, item.schedule_id)"
                                />
                            </td>
                            <td>{{ item.schedule_nm }}</td>
                            <td>{{ item.schedule_start_tm }}</td>
                            <td>{{ item.schedule_end_tm }}</td>
                            <td>{{ item.duration_time }}</td>
                            <td>{{ item.total_task }}</td>
                            <td>{{ item.success_task }}</td>
                            <td>{{ item.total_task - item.success_task }}</td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <th colspan="9">
                            총 {{ searchDataCount }}건 - 선택 {{ checkboxList.filter((item) => item).length }}행
                        </th>
                    </tfoot>
                </table>
                <PaginationButton
                    v-model:currentPage="searchData.currentPage"
                    :perPage="searchData.perPage"
                    :totalCount="searchDataCount"
                    :maxRange="5"
                    :click="handlePageClick"
                />
            </div>

            <div class="content-titleZone">
                <p class="box-title">> 스케줄 상세 정보</p>
            </div>

            <div class="table-zone">
                <table class="list-table">
                    <colgroup>
                        <col style="width: 5%" />
                        <col style="width: 10%" />
                        <col style="width: 20%" />
                        <col style="width: 20%" />
                        <col style="width: 5%" />
                        <col style="width: 5%" />
                        <col style="width: 20%" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th :colspan="7">스케줄명 : {{ scheduleDetailList.schedule_nm }}</th>
                        </tr>
                        <tr>
                            <th>No.</th>
                            <th>작업명</th>
                            <th>시작일시</th>
                            <th>종료일시</th>
                            <th>진행시간(초)</th>
                            <th>작업상태</th>
                            <th>에러정보</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item, index) in scheduleDetailList" :key="index">
                            <td>
                                {{ index + 1 + (scheduleDetail.currentPage - 1) * scheduleDetail.perPage }}
                            </td>
                            <td>{{ item.task_nm }}</td>
                            <td>{{ item.task_start_tm }}</td>
                            <td>{{ item.task_end_tm }}</td>
                            <td>{{ item.duration_time }}</td>
                            <td>{{ item.task_state }}</td>
                            <td>{{ item.task_err_info }}</td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <th :colspan="7">총 {{ scheduleDetailCount }}건</th>
                    </tfoot>
                </table>
                <PaginationButton
                    v-model:currentPage="scheduleDetail.currentPage"
                    :perPage="scheduleDetail.perPage"
                    :totalCount="scheduleDetailCount"
                    :maxRange="5"
                    :click="handlePageClick"
                />
            </div>
        </div>
    </div>
</template>

<script>
import PageNavigation from '../../component/PageNavigation.vue';
import PaginationButton from '../../component/PaginationButton.vue';
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiMagnify } from '@mdi/js';
import axios from 'axios';
export default {
    data() {
        return {
            searchPath: mdiMagnify,
            inputValue: null,

            //체크박스 리스트
            checkboxList: [],
            selectedScheduleId: [],

            //SELECT한 데이터 저장 리스트
            scheduleManagerList: [],
            scheduleDetailList: [],

            //검색 조건 데이터
            searchData: {
                // user_id: $cookies.get("USER_ID"),
                schedule_id: '',
                schedule_nm: '', //스케줄명
                task_state: 'all', //작업상태
                duration_date: 'all', //기간
                duration_time: '', //진행시간(초)
                schedule_start_tm: '', //스케줄시작일시
                schedule_end_tm: '', //스케줄종료일시
                search_start_tm: '', //검색조건 상 시작일시
                search_end_tm: '', //검색 조건 상 종료일시
                currentPage: 1,
                perPage: 5,
            },
            searchDataCount: 0,

            //스케줄 상세 데이터
            scheduleDetail: {
                schedule_id: '',
                currentPage: 1,
                perPage: 5,
            },
            scheduleDetailCount: 0,
        };
    },
    methods: {
        /**
         * 내용 : 검색 조건 바탕으로 스케줄 SELECT
         * 작성자 : 임재현
         * 시간 : 2024.01.26
         */
        selectScheduleManager: function () {
            const vm = this;
            if (vm.$checkLogin() == true) {
                return;
            }
            axios({
                url: 'selectScheduleManager.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.searchData,
            })
                .then(function (response) {
                    vm.scheduleManagerList = response.data.table;
                    vm.searchDataCount = response.data.count;
                })
                .catch(function (error) {
                    vm.$showAlert('error', 'selectScheduleManager 오류');
                });
        },

        /**
         * 내용 : 선택한 행의 schedule_id에 대한 상세 정보 SELECT
         * 작성자 : 임재현
         * 시간 : 2024.01.26
         */
        selectScheduleDetail: function () {
            const vm = this;
            axios({
                url: 'selectScheduleDetail.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: vm.scheduleDetail,
            })
                .then(function (response) {
                    vm.scheduleDetailList = response.data.table;
                    vm.scheduleDetailCount = response.data.count;
                })
                .catch(function (error) {
                    vm.$showAlert('error', 'selectScheduleDetail 오류');
                });
        },

        /**
         * 내용 : 선택한 행 DELETE
         * 작성자 : 임재현
         * 시간 : 2024.01.26
         */
        deleteSchedule: function () {
            const vm = this;
            if (vm.$checkLogin() == true) {
                return;
            }
            let pureArray = this.toPureArray(vm.selectedScheduleId);
            axios({
                url: 'deleteSchedule.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: pureArray,
            })
                .then(function (response) {
                    vm.searchData.currentPage = 1;
                    vm.selectScheduleManager();
                    vm.selectedScheduleId = [];
                    vm.pureArray = [];
                    vm.$showAlert('Success', '삭제되었습니다');
                })
                .catch(function (error) {
                    vm.$showAlert('error', 'deleteSchedule 오류');
                });
        },

        //체크박스 변화 감시
        checkboxWatcher: function (index, schedule_id) {
            if (this.checkboxList[index]) {
                this.selectedScheduleId[index] = schedule_id;
            } else {
                delete this.selectedScheduleId[index];
            }
        },

        tableSelect: function (schedule_id) {
            this.scheduleDetail.schedule_id = schedule_id;
            this.selectScheduleDetail();
        },

        toPureArray: function (selectedId) {
            // 주어진 객체
            let objToDelete = selectedId;

            let keysArray = Object.keys(objToDelete);

            // MyBatis에 전달하기 위해 배열로 변환
            let idsToDelete = keysArray.map((key) => objToDelete[key]);

            return idsToDelete;
        },

        //페이지네이션 클릭 이벤트
        handlePageClick() {},
    },

    watch: {
        //dbmsOption이 선택되는지 감시
        'searchData.currentPage'() {
            this.selectScheduleManager();
        },

        'scheduleDetail.currentPage'() {
            this.selectScheduleDetail();
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
        this.selectScheduleManager();
    },
};
</script>

<style scoped>
.schedulManageTable tr td select {
    width: 100%;
}
.schedulManageTable tr td input {
    width: 100%;
}
</style>
