<!-- 240116 임재현 메인메뉴 -->
<template>
  <div class="container">
    <div class="content">
      <div class="table-zone">
        <table class="form-table">
          <tbody>
            <tr>
              <th>진단 대상 DBMS</th>
              <td colspan="5">
                <div class="flex justify-center">
                  <!-- 상단 선택 바 -->
                  <select class="flex95" v-model="selectDbms" @change="schemaListAllCount">
                    <option value="" disabled selected>-- DBMS명 --</option>
                    <option v-for="db in dbNameList" :key="db.dbms_id"
                      :value="{ dbms_id: db.dbms_id, dbms_name: db.dbms_name }">
                      {{ db.dbms_name }}
                    </option>
                  </select>
                </div>
              </td>
            </tr>
            <tr>
              <!-- 선택 한 DBMS에 해당하는 정보 출력 -->
              <th>진단 대상 스키마 (개)</th>
              <td class="text-ct">{{  schemaCount }}</td>
              <th>진단 대상 테이블 (개)</th>
              <td class="text-ct">{{ tableCount }}</td>
              <th>진단 대상 컬럼 (개)</th>
              <td class="text-ct">{{ columnCount }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="content-box flex justify-between">
        <div class="table-zone left-content flex65">
          <table class="form-table">
            <tbody>
              <tr>
                <!-- 앞의 메뉴에서 진행한 검사 결과 간략 표기 -->
                <th>상위품질지표명</th>
                <th>품질지표명</th>
                <th>전체건수</th>
                <th>오류건수</th>
                <th>오류율</th>
              </tr>
              <!-- 상위품질지표명 rowspan -->
              <tr class="text-ct" v-for="(item, index) in dataList" :key="item.dqi_nm_high">
                <td v-if="index === 0 ||
                  item.dqi_nm_high !== dataList[index - 1].dqi_nm_high
                  " :rowspan="getRowSpan(dataList, item)">
                  {{ item.dqi_nm_high }}
                </td>
                <td>{{ item.dqi_nm }}</td>
                <td>전체건수</td>
                <td>오류건수</td>
                <td>오류율</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="right-content">
          <div class="title">값진단 결과</div>
          <div class="chart">CHART</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      dataList: [],
      dbNameList: [],
      selectDbms: '',
      user_id: $cookies.get("USER_ID"),
      schemaCount: '',
      tableCount: '',
      columnCount: '',
    };
  },
  methods: {
    getRowSpan: function (arr, data) {
      let rowspan = 0;

      arr.forEach((item) => {
        if (item.dqi_nm_high == data.dqi_nm_high) rowspan++;
      });

      return rowspan;
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
          this.$showAlert('error', 'DBMS명 조회 에러.');
        });
    },
    // 검색 - 스키마(DB) 숫자 조회
    schemaListAllCount() {
      const vm = this;
      if (vm.$checkLogin() == true) {
        return;
      }
      axios({
        url: '/schemaListAllCount.json',
        method: 'post',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
        },
        data: { user_id: vm.user_id },
      })
        .then((res) => {
          vm.schemaCount = res.data;
          vm.tableListAllCount();
        })
        .catch((err) => {
          this.$showAlert('error', '스키마 조회 에러.');
        });
    },
    // 검색 - 테이블(DB) 숫자 조회
    tableListAllCount() {
      const vm = this;
      axios({
        url: '/tableListAllCount.json',
        method: 'post',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
        },
        data: { user_id: vm.user_id },
      })
        .then((res) => {
          vm.tableCount = res.data;
          vm.selectDomainRuleCount();
        })
        .catch((err) => {
          this.$showAlert('error', '테이블 조회 에러.');
        });
    },

    // 검색 - 컬럼(DB) 숫자 조회
    selectDomainRuleCount(){
      const vm = this;
      axios({
        url: '/selectDomainRuleCount.json',
        method: 'post',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
        },
        data: { user_id: vm.user_id },
      })
        .then((res) => {
          vm.columnCount = res.data;
        })
        .catch((err) => {
          this.$showAlert('error', '컬럼 조회 에러.');
        });
    }

  },
  mounted() {
    if (this.$checkLogin() == true) {
      return;
    }
    this.selectDbName();
  },
};
</script>
