<template>
  <CommonPage show-footer >
    <n-data-table
      remote
      :single-line="false"
      ref="table"
      :columns="columns"
      :data="examList"
      :loading="loading"
      :pagination="paginationReactive"
      :row-key="rowKey"
      :scroll-x="900"
      @update:page="onChangePage"
    />
  </CommonPage>
  
</template>

<script setup>
import {  ref, reactive, onMounted } from 'vue'
import api from '@/api/user';
import ExamActions from './components/exam-actions.vue';
import { formatDateTime, getToken } from '@/utils';
import { useUserStore } from '@/store';
import { useRouter } from 'vue-router';
import { useCountdown } from '@/composables';

const userStore = useUserStore()
const router = useRouter()

const loading =  ref(true)
const examList = ref([])
const countdownControllers = {}

const paginationReactive = reactive({
  page: 1,
  pageSize: 6,
  prefix ({ itemCount }) {
    return `总共 ${itemCount} 条`
  }
})


const onStartExam = async (exam)=>{
  if(!getToken()){ // 如果没有登录
    $notification.info({
      title: '未登录', 
      content: '必须登录才能参加！',
    })
    userStore.openLoginModal()
    return 
  }
  // console.log(exam);

  const {status, id, type} = exam
  if(status == "考试进行中" || status == "考试已结束"){
    try {
      const res = await api.addExamInfo({id})
      if(type == 'iTraining'){
        router.push({name: 'TrainingIndex', params:{examId: id}})
      }else{
        router.push({name: 'ContestIndex', params:{examId: id}})
      }
    } catch (err) {
      console.log(err);
    }
    
    
  }else{ //考试未开始
    $notification.info({
      title: '考试未开始', 
      content: '请耐心等待！',
      duration: 6000
    })
  }

  
}

const onViewScore = (exam)=>{
  console.log(exam.studentViewScore);
  const {id:examId} = exam
  switch(exam.studentViewScore){
    case 'System':
      router.push({name:'ContestScoreRankStatus',  params: {examId}})
      break;
    case 'RankAndStatus':
      router.push({name: 'ContestScoreRankStatus',  params: {examId} })
      break;
    case 'OnlyRank':
      router.push({name: 'ContestScoreOnlyRank',  params: {examId}})
      break;
    case 'Top10':
      router.push({name: 'ContestScoreTop10',  params: {examId}})
      break;
    case 'iTraining':
      router.push({name: 'TrainingScoreTrainRank', params: {examId}})
      break;
    default: ;
  }

      // if(studentViewScore=="System"){
			// 		// 如果题目数量少于10题
			// 		if(parseInt(problemNum) <= 10)
			// 		{
			// 			window.location.href="examScore2.jsp?examId=" + examId+"&fromclient="+fromclient;
			// 		}
			// 		else
			// 		{
			// 			window.location.href="examScore3.jsp?examId=" + examId+"&fromclient="+fromclient;
			// 		}
			// 	}else if(studentViewScore=="RankAndStatus"){ //"RankAndStatus"排名及题目状态
			// 		window.location.href="examScore2.jsp?examId=" + examId+"&fromclient="+fromclient;
			// 	}else if(studentViewScore=="OnlyRank"){ //"OnlyRank"完整排名
			// 		window.location.href="examScore.jsp?examId=" + examId+"&fromclient="+fromclient;
			// 	}else if(studentViewScore=="Top10"){ //"Top10"显示前10名 
			// 		window.location.href="examScore3.jsp?examId=" + examId+"&fromclient="+fromclient;
			// 	}else if(studentViewScore=="iTraining"){ //智能训练
			// 		window.location.href="examTrainingScore.jsp?examId=" + examId+"&fromclient="+fromclient;
			// 	}
  
}




const columns = [
  {
    key: 'name',
    title: '考试名称',
    fixed: 'left',
    width: '100'
  },{
    key: 'description',
    title: '描述信息',
  },{
    key: 'starttime',
    title: '开始时间',
    width: '108',
    render: (row) => {
      if (!row['starttime']) {
        return ''
      }
      return formatDateTime(row['starttime'])
    }
  },{
    key: 'endtime',
    title: '结束时间',
    width: '108',
    render: (row) => {
      if (!row['endtime']) {
        return ''
      }
      return formatDateTime(row['endtime'])
    }
  },{
    key: 'problemNum',
    title: '题目数量',
    width: '92',
    render: (row)=>{
      return row.problemNum + (row.type === 'iTraining' ? '关': '')
    }
  },{
    key: 'status',
    title: '比赛状态',
    width: '95',
    render: (row, idx) => {
      if(countdownControllers[row.id]){
        const {leftTime, minute, second} = countdownControllers[row.id]
        if(leftTime.value <=0){
          if(Date.now() < row.endtime){
            examList.value[idx].status = '考试进行中'
            return '考试进行中'
          }
          examList.value[idx].status = '考试已结束'
          return '考试已结束'
        }
        return `还有${minute.value}分${second.value}秒`
      }
      return row.status
    }
  },{
    key: 'actions',
    title: '操作',
    width: '80',
    fixed: 'right',
    render(row) {
      return h(
        ExamActions,
        {
          onClickStart: () => onStartExam(row),
          onClickGrade: () => onViewScore(row)
        }
      );
    }
  }
]



const queryByPage = async (currentPage)=>{
  loading.value = true
  //先清理所有定时器
  for(let key in countdownControllers){
      countdownControllers[key].clearCountdown()
    }
  try {
    const res = await api.getExamList({
      params:{pageNum:currentPage, pageSize:paginationReactive.pageSize}
    })
    const {pageNum, pageSize, total, list} = res.data
    paginationReactive.page = pageNum
    paginationReactive.pageSize = pageSize
    paginationReactive.itemCount = total
    // examList.value = list
    //TODO: mock数据
    const mockTime = [1699878411226, 1699775411226]
    examList.value = list.map((item,i)=>{
      if(i===0)
        return item;
      return {...item, 
        starttime:mockTime[i-1] ? mockTime[i-1] : item.starttime, 
        status:'还有xx分',
      }
    })
    // 添加倒计时控制
    examList.value.forEach(exam=>{
      //TODO: 暂时用本机时间戳, 之后用isNaN(Number('o'))转换时间
      // 距离考试不到1h,开始倒计时
      if(exam.status.startsWith('还有')){
        countdownControllers[exam.id] = useCountdown()
        
        const nowTime = Date.now()
        countdownControllers[exam.id].startCountdown(Math.floor((exam.starttime-nowTime)/1000)) 
      }
    })
  } catch (err) {
    
  }finally{
    loading.value = false
  }
  

}

const onChangePage =  (page) => {
  paginationReactive.page = page;
  if (!loading.value) {
    queryByPage(page)
  }
}
const onUpdatePageSize = (pageSize) => {
  paginationReactive.pageSize = pageSize;
  paginationReactive.page = 1;
  queryByPage(1)
}


const rowKey = (rowData) => {
  return rowData.id
}







onMounted(()=>{
  console.log('Mounted: exam-list');
  queryByPage(paginationReactive.page)


})

//销毁倒计时
onBeforeUnmount(()=>{
  console.log('beforeunmout: clearCountdown');
  for(let key in countdownControllers){
    countdownControllers[key].clearCountdown()
  }
})
    
</script>