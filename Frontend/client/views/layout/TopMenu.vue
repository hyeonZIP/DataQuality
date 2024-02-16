<template>
    <nav class="top-menu">
        <ul class="main-menu">
            <li v-for="(mainMenu, idx) in menuList" :key="idx" @mouseover="showAllSubMenus" @mouseout="hideAllSubMenus">
                <router-link
                    :to="mainMenu.path"
                    v-if="mainMenu.path"
                    class="depth1"
                    :class="{ active: isMainMenuActive(mainMenu) }"
                    >{{ mainMenu.pathName }}</router-link
                >
                <p class="depth1" v-else :class="{ active: isMainMenuActive(mainMenu) }">{{ mainMenu.pathName }}</p>
                <ul
                    v-if="mainMenu.subMenu"
                    class="sub-menu"
                    :style="
                        subShow
                            ? 'max-height:500px; opacity:1;transition: all 1s ease-in-out;'
                            : 'max-height:0px; opacity:0'
                    "
                >
                    <li
                        v-for="(subMenu, idx) in mainMenu.subMenu"
                        :key="idx"
                        :class="{ active: isSubMenuActive(subMenu) }"
                    >
                        <router-link :to="subMenu.path">{{ subMenu.pathName }}</router-link>
                    </li>
                </ul>
            </li>
        </ul>
        <div class="navbg" :style="subShow ? `height: ${maxSubMenuHeight}px` : `height: 0px`"></div>
    </nav>
</template>

<script>
export default {
    data() {
        return {
            menuList: [
                // { path: "/", pathName: "Dashboard", },
                {
                    pathName: '진단대상관리',
                    subMenu: [
                        { path: '/dbmsManagement.page', pathName: '진단대상DB관리' },
                        { path: '/tableManagement.page', pathName: '진단대상테이블관리' },
                        { path: '/columnManagement.page', pathName: '컬럼분석' },
                        { path: '/dqiList.page', pathName: '품질지표조회' },
                    ],
                },
                {
                    pathName: '진단규칙설정',
                    subMenu: [
                        { path: '/valRuleManagement.page', pathName: '검증룰 관리' },
                        { path: '/codeManagement.page', pathName: '코드 관리' },
                        { path: '/domainRuleManagement.page', pathName: '도메인 진단 규칙 관리' },
                        { path: '/refRuleManagement.page', pathName: '데이터 규칙 관리' },
                        { path: '/workRuleManagement.page', pathName: '업무 규칙 관리' },
                        { path: '/duplRuleManagement.page', pathName: '중복 분석 관리' },
                    ],
                },
                {
                    pathName: '진단실행',
                    subMenu: [
                        { path: '/execRuleManagement.page', pathName: '진단항목별 실행' },
                        { path: '/scheduleManagement.page', pathName: '스케줄 실행 결과 조회' },
                    ],
                },
                {
                    pathName: '진단결과',
                    subMenu: [
                        { path: '/execRuleResult.page', pathName: '진단상세결과현황' },
                        { path: '/totalRuleResult.page', pathName: '품질진단종합현황' },
                    ],
                },
                {
                    pathName: '마이페이지',
                    subMenu: [{ path: '/myPage.page', pathName: '회원정보수정' }],
                },
                // {
                //     pathName: "템플릿",
                //     subMenu: [
                //         { path: "/searchbar.page", pathName: "서치바" },
                //         { path: "/table.page", pathName: "테이블" },
                //         { path: "/btnPosition.page", pathName: "버튼별 위치" },
                //         { path: "/formModal.page", pathName: "form modal" },
                //         { path: "/alertModal.page", pathName: "alert modal" },
                //     ],
                // },
                // {
                //     pathName: "레이아웃 템플릿",
                //     subMenu: [
                //         { path: "/vertical.page", pathName: "수직 레이아웃" },
                //         { path: "/horizontal.page", pathName: "수평 레이아웃" },
                //     ],
                // },
                // {
                //     pathName: "가이드",
                //     subMenu: [
                //         { path: "/guide.page", pathName: "가이드" },
                //     ],
                // },
            ],
            currentRoute: null,
            subShow: false,
        };
    },
    methods: {
        showAllSubMenus() {
            this.subShow = true;
        },
        hideAllSubMenus() {
            this.subShow = false;
        },

        isMainMenuActive(mainMenu) {
            return (
                this.currentRoute &&
                (this.currentRoute.path === mainMenu.path || this.isSubMenuActive(mainMenu.subMenu))
            );
        },

        isSubMenuActive(subMenu) {
            if (!subMenu) return false;
            for (let i = 0; i < subMenu.length; i++) {
                if (this.currentRoute.path === subMenu[i].path) {
                    return true;
                }
            }
            return false;
        },
    },
    watch: {
        $route(to, from) {
            this.currentRoute = to;
        },
    },
    computed: {
        maxSubMenuHeight: function () {
            let maxSubItems = Math.max(
                ...this.menuList.map((menu) => {
                    // subMenu가 존재하는 경우에만 길이를 반환
                    return menu.subMenu ? menu.subMenu.length : 0;
                })
            );
            return maxSubItems * 50; // 48px는 각 메뉴 항목의 높이로 가정
        },
    },
    components: {},
    mounted() {
        console.log('Menu mounted');
    },
};
</script>
