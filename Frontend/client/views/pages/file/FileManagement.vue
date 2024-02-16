<template>
    <div class="container">
        <div class="page-titleZone flex justify-between">
            <p class="main-title">페이지 제목을 적어주세요</p>
            <PageNavigation />
        </div>
        <div class="content-box flex justify-between">
            <div class="left-content flex20">
                <div class="flex-column justify-between">
                    <div class="host-zone flex15">
                        <div class="content-titleZone">
                            <p class="box-title">호스트 선택</p>
                        </div>
                        <select name="" id="" class="only">
                            <option value="">172.0.0.1</option>
                        </select>
                    </div>
                    <div class="file-zone flex85">
                        <div class="content-titleZone flex justify-between">
                            <p class="box-title">폴더 리스트</p>
                            <button class="darkg-border-btn small-btn">폴더추가</button>
                        </div>
                        <div class="content-zone">
                        </div>
                    </div>
                </div>
            </div>
            <div class="right-content flex80">
                <div class="searchbar-zone">
                    <div class="flex justify-end align-center">
                        <input type="date" name="" id="" class="square-date">
                        <span class="coupler">~</span>
                        <input type="date" name="" id="" class="square-date">
                        <select name="" id="" class="square-select">
                            <option value="all">전체</option>
                        </select>
                        <div class="search-square">
                            <input type="text" class="square-input" placeholder="Search">
                            <button class="square-button">
                                <svg-icon type="mdi" :path="searchPath" class="square-icon"></svg-icon>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="flex-column">
                    <div class="table-zone">
                        <table class="list-table">
                            <colgroup>
                                <col style="width: ;">
                                <col style="width: ;">
                                <col style="width: ;">
                                <col style="width: ;">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>user ID</th>
                                    <th>이름</th>
                                    <th>생년월일</th>
                                    <th>성별</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <PaginationButton />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import PageNavigation from '../../component/PageNavigation.vue';
import PaginationButton from '../../component/PaginationButton.vue';
import FileTree from '../../component/FileTree.vue';
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiMagnify } from '@mdi/js';
export default {
    data() {
        return {
            searchPath: mdiMagnify
        }
    },
    methods: {

    },
    watch: {

    },
    computed: {

    },
    components: {
        'PageNavigation': PageNavigation,
        'FileTree': FileTree,
        'SvgIcon': SvgIcon,
        'PaginationButton': PaginationButton
    },
    mounted() {
        console.log('main mounted');
    }
}
</script>