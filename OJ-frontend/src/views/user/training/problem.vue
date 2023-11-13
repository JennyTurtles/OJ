<template>
  <CommonPage show-footer :showHeader="false">
    <div text-center>训练题目页</div>
  </CommonPage>
</template>

<script setup>
import {useRoute} from 'vue-router'

const route = useRoute()
console.log(route.params);
</script>