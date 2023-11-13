<template>
  <CommonPage show-footer >
    <div v-if="leftTime>0"  >
      <n-h4 prefix="bar" class="p-10 rounded-2 " >
        离考试结束还有 {{ day }} 天 {{ hour }}:{{ minute }}:{{ second }}
      </n-h4>
      
    </div>
    <div v-else>考试已经结束了</div>

    <n-data-table
      :single-line="false"
      ref="table"
      :columns="columns"
      :data="exam.problems"
      :loading="loading"
      :row-key="rowKey"
      :scroll-x="900"
      @update:page="onChangePage"
    />
  </CommonPage>
  
</template>
<script setup>
import {ref, computed, onUnmounted, onMounted} from 'vue'
import { useRoute, useRouter} from 'vue-router';
import { NButton } from "naive-ui";
import api from '@/api/user'
import {useCountdown} from '@/composables'


console.log('setup: contest-index');

const EXAM_SYNC_INTERVAL = 60//600 // 每10分钟同步一次考试剩余时间
let syncExamTimer = null//同步时间的timer


const {
    leftTime,
    startCountdown,
    clearCountdown,
    day,
    hour,
    minute,
    second,
} = useCountdown()

const route = useRoute()
const router = useRouter()
const {examId} = route.params
const exam = reactive({
  self: {},
  problems:[]
})

const loading =  ref(true)


const columns  = [
  {
    key: 'index',
    title: '题号',
    fixed: 'left'
  },
  {
    key: 'title',
    title: '标题',
    render: (row) => {
      return h(
        NButton, 
        { text:true, type:'primary', onClick: () => {
          router.push({ name: 'ContestProblemDetail', params: {problemId:row.id}})
        } },
        row.title
      );
    }
  },
  {
    key: 'difficulty',
    title: '难度'
  },
  {
    key: 'problemScore',
    title: '分数',
  },
  {
    key: 'status',
    title: '状态',
  }
  
]



const rowKey = (rowData) => {
  return rowData.id
}

// 使用变量保存这场考试(Exam)的相关信息
// var thisExam = null,
// 	// 使用变量保存当前显示的题目的ID
// 	thisProblemId = null,
// 	// 左边题目列表状态的定时器
// 	leftTimer = null,
// 	// 向后台请求左侧题目列表的状态的频率为6秒钟	
// 	const LeftListFrequency = 6000,
// 	// 点击其中错误案例，用来保存页面中的wrongcasesId和problemtestcasesId
// 	theWrongcasesId = null,
// 	theProblemtestcasesId = null,



const syncExamTime = async ()=>{
  try {
    const res = await api.getSyncTime({
      params:{examId}
    })
   
    startCountdown(Math.floor(res.data.leftTime/1000)); //舍弃毫秒, 开始倒计时

  } catch (error) {
    console.log(error);
  }
}

const getProblems = async ()=>{
  console.log('获取题目列表');
  try {
    const res = await api.getProblemsList({
      examId
    })
    exam.self  = res.data.exam
    exam.problems = res.data.problemsList.map((item,index)=>({index, ...item}))
  } catch (error) {
    console.log(error);
  } finally{
    loading.value = false
  }
}


// 清除计时
onBeforeUnmount(()=>{
  clearInterval(syncExamTimer)
  syncExamTimer = null
  clearCountdown()
})

//同步时间
syncExamTime()
syncExamTimer = setInterval(syncExamTime, EXAM_SYNC_INTERVAL*1000)

//拉取题目列表
getProblems()
onMounted(()=>{
	console.log('Mounted: contest-index');
})



</script>