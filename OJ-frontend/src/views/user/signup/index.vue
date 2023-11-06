<template>
  <CommonPage show-footer >
    <div class="f-c-c w-full">
      <div class="max-w-900 w-full">
        <n-form  ref="formRef" :model="formValue" :rules="formRules" >
          <div class="grid md:grid-cols-2 md:gap-x-30">
            <n-form-item label="选择学校" path="schoolId" >
              <n-select v-model:value="formValue.schoolId"  filterable placeholder="请选择学校" :options="schoolOptions"/>
            </n-form-item>
            <n-form-item label="学号" path="studentNo" >
              <n-input v-model:value="formValue.studentNo" placeholder="请输入学号"></n-input>
            </n-form-item>
            <n-form-item label="姓名" path="chineseName">
              <n-input v-model:value="formValue.chineseName" placeholder="请输入姓名"></n-input>
            </n-form-item>
            <n-form-item label="班级" path="banji">
              <n-input v-model:value="formValue.banji" placeholder="请输入班级"></n-input>
            </n-form-item>
            <n-form-item label="邮箱" path="email" >
              <n-input v-model:value="formValue.email" placeholder="请输入邮箱"></n-input>
            </n-form-item>
            <n-form-item label="手机号/邮箱（此为登录账号）" path="username" >
              <n-input v-model:value="formValue.username" placeholder="请输入账号名"></n-input>
            </n-form-item>
            <n-form-item label="密码" path="password">
              <n-input v-model:value="formValue.password" placeholder="请输入密码" type="password"></n-input>
            </n-form-item>
            <n-form-item label="确认密码" path="confirmPassword" >
              <n-input v-model:value="formValue.confirmPassword" placeholder="请确认密码" type="password"></n-input>
            </n-form-item>
            <n-form-item label="问题" path="question">
              <n-input v-model:value="formValue.question" placeholder="请输入问题"></n-input>
            </n-form-item>
            <n-form-item label="答案" path="answer">
              <n-input v-model:value="formValue.answer" placeholder="请输入答案"></n-input>
            </n-form-item>
          </div>
          
          <n-form-item  class="f-c-c">
            <n-button type="primary" @click="submitForm">注册</n-button>
          </n-form-item>
        </n-form>
      </div>
    </div>
  </CommonPage>
</template>

<script setup>
import { ref } from 'vue';
import {validatePhone, validateEmail} from '@/utils/validator'
import api from '@/api/user'
import schools from './school.json'
import { useRouter } from 'vue-router';


const schoolOptions = schools.map(item=>(
  {
    label: item.name,
    value: item.id+""
  }
))


const router = useRouter()

const formRef = ref(null)
const formValue = ref({
  schoolId: '',
  studentNo: '',
  chineseName: '',
  banji: '',
  email: '',
  username: '',
  password: '',
  confirmPassword: '',
  question: '',
  answer: '',
});

const formRules = {
  schoolId: {
    required: true,
    message: '学校不能为空',
    trigger: ['blur']
  },
  studentNo: {
    required: true,
    message: '学号不能为空',
    trigger: ['blur']
  },
  chineseName: {
    required: true,
    message: '姓名不能为空',
    trigger: ['blur']
  },
  banji: {
    required: true,
    message: '班级不能为空',
    trigger: ['blur']
  },
  email: { 
      trigger: ['blur', 'change'] , 
      validator: (rule, value)=> (!value || validateEmail(value)),
      message: '请输入有效的邮箱'
  },
  username:{ required: true, 
      trigger: ['blur', 'change'] , 
      validator: (rule, value)=> (validatePhone(value) || validateEmail(value)),
      message: '请输入有效的手机号/邮箱'
    },
  password:  { required: true, min: 6, message: '密码长度不能少于6位', trigger: 'blur' },
  confirmPassword: {
    required: true,
    trigger: ['blur', 'input'],
    validator: (rule, value) => (value === formValue.value.password),
    message: "两次密码输入不一致",
  },
  question: { required: true, message: '问题不能空', trigger: 'blur' },
  answer: { required: true, message: '答案不能空', trigger: 'blur' },
}

const loading = ref(false)



const submitForm = () => {
  
  formRef.value.validate(async (errors) => {
    if (!errors) {
      try {
        loading.value = true
        $message.loading('处理中...')
        const payload = {...formValue.value}
        delete payload.confirmPassword
        const res = await api.signUser(payload)
        $dialog.success({
          title: '学生注册信息提示',
          content: '恭喜您注册成功，请点击确定前往登录页面',
          positiveText: "确认",
          onPositiveClick: () => {router.push({name: 'UserLogin'})},
          closable:false
        })
      } catch (err) {
        if(err.code === 40000){
          console.log(err);
          const {username} = err.data
          $dialog.error({
            title: '注册失败',
            content: `你已经注册过一个用户，原来的用户名为：${username}，忘记密码请点击确认进入重置密码页面`,
            positiveText: "确认",
            negativeText: "取消",
            onPositiveClick: () => {router.push({name: 'RePwInfo'})}
          })
        }
      } finally{
        loading.value = false
        $message.removeMessage()
      }
    } else {
      $message.error('表单验证失败')
    }
  });
};
</script>

