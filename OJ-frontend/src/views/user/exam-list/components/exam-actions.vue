<template>
  <div flex-col gap-y-10 >
    <n-button type="primary" size="small" @click="onStart">
      参加
    </n-button>
    <n-button type="success" size="small" @click="onGrade">
      成绩
    </n-button>
  </div>
</template>

<script setup>
// import {} from 'vue'
import { isNullOrWhitespace } from '@/utils'
const emit = defineEmits(['clickStart', 'clickGrade'])
const onStart = ()=>{
  emit('clickStart')
}
const onGrade = ()=>{
  emit('clickGrade')
}
</script>
