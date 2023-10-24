<template>
  <CommonPage show-footer >
    <n-data-table
      remote
      :single-line="false"
      ref="table"
      :columns="columns"
      :data="data"
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
import { NButton, useMessage, useNotification } from "naive-ui";
import api from '@/api/user';
import ExamActions from './exam-actions.vue';
import { getToken } from '@/utils';
import { useUserStore } from '@/store';
import { useRouter } from 'vue-router';


const userStore = useUserStore()
const router = useRouter()



const onStartExam = async (row)=>{
  if(!getToken()){ // 如果没有登录
    $notification.info({
      title: '未登录', 
      content: '必须登录才能参加！',
    })
    userStore.openLoginModal()
    return 
  }
  console.log(row);

  const {status, id, type} = row
  if(status == "考试进行中" || status == "考试已结束"){
    try {
      const res = await api.takeExam({id})
      if(type == 'iTraining'){
        router.push({name: 'Trainset', query:{examId: id}})
      }else{
        router.push({name: 'Problemset', query:{examId: id}})
      }
    } catch (err) {
      console.log(res);
    }
    
    
  }

  
}

const onCheckGrade = (row)=>{
  console.log('grad');
  console.log(row);
}




const columns = [
  {
    key: 'name',
    title: '考试名称',
    fixed: 'left'
  },{
    key: 'description',
    title: '描述信息',
  },{
    key: 'starttime',
    title: '开始时间',
    width: '108'
  },{
    key: 'endtime',
    title: '结束时间',
    width: '108'
  },{
    key: 'problemNum',
    title: '题目数量',
    width: '92'
  },{
    key: 'status',
    title: '比赛状态',
    width: '95'
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
          onClickGrade: () => onCheckGrade(row)
        }
      );
    }
  }
]

const loading =  ref(true)
const data = ref([])

const paginationReactive = reactive({
  page: 1,
  pageSize: 3,
  prefix ({ itemCount }) {
    return `Total is ${itemCount}.`
  }
})

onMounted(()=>{
  console.log('on Mount');
  queryByPage(paginationReactive.page)
})

const queryByPage = (currentPage)=>{
  loading.value = true
  api.getExamList({
    params:{pageNum:currentPage-1, pageSize:paginationReactive.pageSize}
  }).then((res) => {
    const {pageNum, pageSize, total, list} = res.data
    paginationReactive.page = pageNum
    paginationReactive.pageSize = pageSize
    paginationReactive.itemCount = total
    data.value = list
    loading.value = false
  })
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
    
</script>