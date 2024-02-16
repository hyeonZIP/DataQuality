import { createWebHistory, createRouter } from "vue-router";

// import Main from "../pages/main/Main.vue"; //240116 임재현 메인메뉴

// 진단 대상 관리
import DqiList from "../pages/dbManagement/DqiList.vue"; 
import DbSchema from "../pages/dbManagement/DbSchemaManagement.vue";
import TableManage from "../pages/dbManagement/TableManagement.vue"; 
import ColumnManagement from "../pages/dbManagement/ColumnManagement.vue"; 

// 진단 규칙 설정
import ValRuleManagement from "../pages/rule/ValRuleManagement.vue"; 
import CodeManagement from "../pages/rule/CodeManagement.vue"; 
import DomainRuleManagement from "../pages/rule/DomainRuleManagement.vue";
import RefRuleManagement from "../pages/rule/RefRuleManagement.vue";
import WorkRuleManagement from "../pages/rule/WorkRuleManagement.vue"; 
import DuplRuleManagement from "../pages/rule/DuplRuleManagement.vue"; 

// 진단 실행
import ScheduleManagement from "../pages/schedule/ScheduleManagement.vue";
import ScheduleLogManagement from "../pages/schedule/ScheduleLogManagement.vue";
import ExecRuleManagement from "../pages/schedule/ExecRuleManagement.vue"; 

// 진단 결과
import ExecRuleResult from "../pages/result/ExecRuleResult.vue"; 
import TotalRuleResult from "../pages/result/TotalRuleResult.vue"; 


// 그 외
import MainMenu from "../pages/main/MainMenu.vue"; 
import Join from "../pages/join/Join.vue";
import Login from "../pages/login/Login.vue";
import Register from "../pages/login/Register.vue";
import Mypage from "../pages/mypage/MyPage.vue";

import FileManagement from "../pages/file/FileManagement.vue";
import HostManagement from "../pages/file/HostManagement.vue";

// 템플릿
import Searchbar from "../template/templateElement/Searchbar.vue";
import Table from "../template/templateElement/Table.vue";
import BtnPosition from "../template/templateElement/BtnPosition.vue";
import Horizontal from "../template/layoutTemplate/Horizontal.vue";
import Vertical from "../template/layoutTemplate/Vertical.vue";
import FormModal from "../template/templateElement/FormModal.vue";
import AlertModal from "../template/templateElement/AlertModal.vue";
import Guide from "../template/guide/TemplateGuide.vue";

const routes = [
  /* 메인화면 */
  // { path: '/', name: 'Main', component: Main }, //240116 임재현 메인메뉴
  {
    path: "/fileManagement.page",
    name: "FileManagement",
    component: FileManagement,
  },
  {
    path: "/hostManagement.page",
    name: "HostManagement",
    component: HostManagement,
  },
  {
    path: "/scheduleManagement.page",
    name: "ScheduleManagement",
    component: ScheduleManagement,
  },
  {
    path: "/scheduleLogManagement.page",
    name: "ScheduleLogManagement",
    component: ScheduleLogManagement,
  },
  { path: "/", name: "Login", component: Login },
  { path: "/join.page", name: "Join", component: Join }, 
  { path: "/register.page", name: "Register", component: Register },
  { path: "/myPage.page", name: "Mypage", component: Mypage },
  { path: "/main.page", name: "MainMenu", component: MainMenu }, //240116 임재현 메인메뉴
  { path: "/dqiList.page", name: "DqiList", component: DqiList }, //240116 임재현 품질지표조회
  { path: "/dbmsManagement.page", name: "DbManagement", component: DbSchema },
  { path: "/tableManagement.page", name: "TableManage", component: TableManage },
  { path: "/columnManagement.page", name: "ColumnManagement", component: ColumnManagement },    //240123 임재현 컬럼분석
  { path: "/valRuleManagement.page", name: "ValRulManagement", component: ValRuleManagement }, //240117 임재현 검증룰 관리
  { path: "/codeManagement.page", name: "CodeManagement", component: CodeManagement }, // 코드 관리
  { path: "/domainRuleManagement.page", name: "DomainRule", component: DomainRuleManagement }, // 도메인 관리
  { path: "/refRuleManagement.page", name: "RefRuleManagement", component: RefRuleManagement }, // 데이터규칙관리
  { path: "/duplRuleManagement.page", name: "DuplRuleManagement", component: DuplRuleManagement }, //중복 분석
  { path: "/workRuleManagement.page", name: "WorkRuleManagement",   component: WorkRuleManagement }, // 업무 규칙
  { path: "/execRuleManagement.page", name: "ExecRuleManagement", component: ExecRuleManagement }, //240126 임재현 진단항목별 실행
  { path: "/execRuleResult.page", name: "ExecRuleResult", component: ExecRuleResult, }, //240130 임재현 진단상세결과현황
  { path: "/totalRuleResult.page", name: "TotalRuleResult", component: TotalRuleResult }, //240131 임재현 품질진단종합현황

  // 템플릿 화면
  { path: "/searchbar.page", name: "Searchbar", component: Searchbar },
  { path: "/table.page", name: "Table", component: Table },
  { path: "/btnPosition.page", name: "BtnPosition", component: BtnPosition },
  { path: "/horizontal.page", name: "Horizontal", component: Horizontal },
  { path: "/vertical.page", name: "Vertical", component: Vertical },
  { path: "/formModal.page", name: "FormModal", component: FormModal },
  { path: "/alertModal.page", name: "AlertModal", component: AlertModal },
  { path: "/guide.page", name: "Guide", component: Guide },
];

const AppRouter = createRouter({
  history: createWebHistory(),
  routes,
});

export default AppRouter;
