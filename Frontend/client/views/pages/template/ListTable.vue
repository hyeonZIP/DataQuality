<template>
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
            </tbody>
        </table>
    </div>
</template>

<script>

export default {
    data() {
        return {
        }
    },
    methods: {

    },
    watch: {

    },
    computed: {

    },
    components: {
    },
    mounted() {
        console.log('main mounted');
    }
}
</script>
