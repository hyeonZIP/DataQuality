<template>
    <div class="search-bar">
        <div class="flex-end">
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
</template>

<script>
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
        'SvgIcon': SvgIcon
    },
    mounted() {
        console.log('main mounted');
    }
}
</script>
