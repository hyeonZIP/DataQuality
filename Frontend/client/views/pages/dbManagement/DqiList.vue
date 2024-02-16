<!-- 240116 임재현 dqi_table 정보  -->

<template>
    <div class="container">
        <div class="page-titleZone flex justify-between">
            <p class="main-title">데이터품질지표 조회</p>
            <PageNavigation />
        </div>

        <div class="content">
            <div class="table-zone">
                <table class="form-table dqi-table">
                    <colgroup>
                        <col width="5%" />
                        <col width="15%" />
                        <col width="15%" />
                        <col width="15%" />
                        <col width="50%" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>상위품질지표명</th>
                            <th>품질지표명</th>
                            <th>검증유형</th>
                            <th>데이터품질지표설명</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- 상위품질지표명 rowspan -->
                        <tr class="text-ct" v-for="(item, index) in dataList" :key="item.dqi_nm_high">
                            <td>{{ index + 1 }}</td>
                            <td
                                v-if="index === 0 || item.dqi_nm_high !== dataList[index - 1].dqi_nm_high"
                                :rowspan="getRowSpan(dataList, item)"
                            >
                                {{ item.dqi_nm_high }}
                            </td>
                            <td>{{ item.dqi_nm }}</td>
                            <td>
                                <span v-if="item.dqi_type !== 'none'">{{ item.dqi_type }}</span>
                            </td>
                            <td class="explain">{{ item.dqi_explain }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            dataList: [],
        };
    },
    methods: {
        selectDqiTable: function () {
            const vm = this;
            axios({
                url: 'selectDqiTable.json',
                method: 'post',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                },
                data: {},
            })
                .then(function (response) {
                    vm.dataList = response.data;
                })
                .catch(function (error) {
                    alert('selectDqiTable 오류');
                });
        },
        getRowSpan: function (arr, data) {
            let rowspan = 0;

            arr.forEach((item) => {
                if (item.dqi_nm_high == data.dqi_nm_high) rowspan++;
            });

            return rowspan;
        },
    },
    mounted() {
        this.selectDqiTable();
    },
};
</script>

<style>
.explain {
    text-align: left;
}
</style>
