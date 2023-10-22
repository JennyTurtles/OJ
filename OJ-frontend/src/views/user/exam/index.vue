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
import { NButton, useMessage } from "naive-ui";
import api from '@/api';
import ExamActions from './exam-actions.vue';


const onStartExam = (row)=>{
  console.log('exam');
  console.log(row);
}

const onCheckGrad = (row)=>{
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
          onClickGrade: () => onCheckGrad(row)
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