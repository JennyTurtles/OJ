<template>
  <CommonPage show-footer :showHeader="false">
    <div text-center>比赛题目页</div>
    <div v-html="problem.description"></div>
  </CommonPage>
</template>

<script setup>
import {onMounted} from 'vue'
import {useRoute} from 'vue-router'
import api from '@/api/user'

const route = useRoute()
const {examId, problemId} = route.params

const problem = ref({})


const getProblemDetail = async ()=>{
  try {
    const res = await api.getProblemByIdAndExamId({
      examId,
      id: problemId
    })
    console.log(res);
    problem.value = res.data
  } catch (err) {
    console.log(err);
  }
}

getProblemDetail()


</script>