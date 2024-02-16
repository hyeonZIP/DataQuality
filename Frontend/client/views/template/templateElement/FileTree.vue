<template>
    <div class="flex justfiy-center">
        <Tree :value="nodes" :loadingMode="'icon'" class="flex20"></Tree>
    </div>
</template>

<script>
import Tree from 'primevue/tree';
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiFolderOpenOutline, mdiFileOutline } from '@mdi/js';

export default {
    data() {
        return {
            nodes: [
                {
                    key: 0,
                    label: "test1",
                    nodeIcon:'<svg-icon type="mdi" :path="folderPath"></svg-icon>',
                    children: [
                        { key: 0 - 0, label: "test0.1", data: "https://www.google.com", type: 'url',nodeIcon:'  <svg-icon type="mdi" :path="filePath"></svg-icon>' },
                        { key: 0 - 1, label: "test0.2", data: "https://www.google.com", type: 'url',nodeIcon:'  <svg-icon type="mdi" :path="filePath"></svg-icon>' },
                        { key: 0 - 2, label: "test0.3", data: "https://www.google.com", type: 'url',nodeIcon:'  <svg-icon type="mdi" :path="filePath"></svg-icon>' },
                        { key: 0 - 3, label: "test0.4", data: "https://www.google.com", type: 'url',nodeIcon:'  <svg-icon type="mdi" :path="filePath"></svg-icon>' }
                    ]
                },
                {
                    key: 1,
                    label: "test2",
                    nodeIcon:'<svg-icon type="mdi" :path="folderPath"></svg-icon>',
                    children: [
                        { key: 1 - 0, label: "test1.1", data: "https://www.google.com", type: 'url',nodeIcon:'  <svg-icon type="mdi" :path="filePath"></svg-icon>' },
                        { key: 1 - 1, label: "test1.2", data: "https://www.google.com", type: 'url',nodeIcon:'  <svg-icon type="mdi" :path="filePath"></svg-icon>' },
                        { key: 1 - 2, label: "test1.3", data: "https://www.google.com", type: 'url',nodeIcon:'  <svg-icon type="mdi" :path="filePath"></svg-icon>' },
                        { key: 1 - 3, label: "test1.4", data: "https://www.google.com", type: 'url',nodeIcon:'  <svg-icon type="mdi" :path="filePath"></svg-icon>' }
                    ]
                },
            ],
            folderPath:mdiFolderOpenOutline,
            filePath:mdiFolderOpenOutline,
        }
    },
    methods: {
    },
    watch: {

    },
    computed: {

    },
    components: {
        'Tree': Tree,
        'SvgIcon':SvgIcon
    },
    mounted() {
        console.log('main mounted');
    }
}
</script>