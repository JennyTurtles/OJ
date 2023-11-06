<template>
  <CommonPage show-footer >
    <div>题目列表</div>
    <div v-if="leftTimeRef>0">离考试结束还有 {{ day }} 天 {{ hour }}:{{ minute }}:{{ second }}</div>
    <div v-else>考试已经结束了</div>
  </CommonPage>
  
</template>
<script setup>
import {ref, computed, onUnmounted} from 'vue'
import api from '@/api/user'

const leftTimeRef = ref(0)


//TODO: 待优化
const day = computed(()=>{
  if(leftTimeRef.value > 0){
    return addZeroBeforeTime(Math.floor(leftTimeRef.value  / 60 / 60 / 24));
  }else{
    return '0'
  }
})
const hour = computed(()=>{
  if(leftTimeRef.value > 0){
    return addZeroBeforeTime(Math.floor(leftTimeRef.value  / 60 / 60 % 24));
  }else{
    return '00'
  }
})
const minute = computed(()=>{
  if(leftTimeRef.value > 0){
    return addZeroBeforeTime(Math.floor(leftTimeRef.value  / 60 % 60));
  }else{
    return '00'
  }
})
const second = computed(()=>{
  if(leftTimeRef.value > 0){
    return addZeroBeforeTime(Math.floor(leftTimeRef.value  % 60));
  }else{
    return '00'
  }
})

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


	
const autoSubmitWhenAC = true // 默认AC之后自动提交
const EXAM_SYNC_INTERVAL = 600000  // 每10分钟同步一次考试剩余时间




let timer = null //剩余时间timer
let syncExamTimer = null//同步时间的timer
//倒计时
const countdown = ()=>{
  if(leftTimeRef.value <= 0 && timer){ //考试结束
		clearInterval(timer);
    timer = null
    clearInterval(syncExamTimer);
    syncExamTimer = null
	}
  leftTimeRef.value--
}


function addZeroBeforeTime(value)
{
	if(value<10)
	{
		return '0' + value;
	}
	return value;
}



const syncExamTime = async ()=>{
  try {
    const res = await api.getSyncTime()
    const {leftTime} = res.data
    // 先停止每秒钟倒计时定时器
    if(timer){
      clearInterval(timer);
      timer = null
    }
    leftTimeRef.value = leftTime/1000; //舍弃毫秒

    timer = setInterval(countdown, 1000); //等1s后开始，间隔1s调用1次


  } catch (error) {
    console.log(error);
  }
}


console.log('setup ...');
syncExamTime()
syncExamTimer = setInterval(syncExamTime, EXAM_SYNC_INTERVAL)

// 退出页面
onUnmounted(()=>{
  console.log('clear timer syncExamTimer');
  clearInterval(timer) 
  timer = null
  clearInterval(syncExamTimer)
  syncExamTimer = null
})

</script>