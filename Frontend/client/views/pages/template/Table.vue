<template>
    <div class="content">
        <div class="box">
            <p style="font-size:2rem; font-weight:800; margin-bottom:10px">기본 List Table</p>
            <ListTable />
        </div>
        <div class="box">
            <p style="font-size:2rem; font-weight:800; margin-bottom:10px">기본 form Table</p>
            <FormTable />
        </div>
    </div>
</template>

<script>
import ListTable from './ListTable.vue';
import FormTable from './FormTable.vue';
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
        'ListTable': ListTable,
        'FormTable': FormTable
    },
    mounted() {

    }
}
</script>

<style scoped>
.box {
    padding: 50px;
    border: 1px solid #eee;
    margin-bottom: 30px;
}
</style>