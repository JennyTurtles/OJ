<template>
  <CommonPage show-footer >
    <div class="f-c-c w-full">
      <div class="max-w-900 w-full">
        <n-form  ref="formRef" :model="formValue" :rules="rules" class="flex flex-col mt-20 px-15" >
          <n-form-item label="账号" >
            {{ formValue.username }}
          </n-form-item>
          <n-form-item label="密码" path="password">
            <n-input  v-model:value="formValue.password" placeholder="请输入新密码" type="password"></n-input>
          </n-form-item>
          <n-form-item label="重复密码"  path="rePassword">
            <n-input v-model:value="formValue.rePassword" placeholder="请输入相同密码" type="password"></n-input>
          </n-form-item>
          <n-form-item>
            <n-button type="primary" @click.prevent="submitForm">确认</n-button>
          </n-form-item>
        </n-form>
      </div>
    </div>

  </CommonPage>
</template>

<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import api from '@/api/user'

const route = useRoute()
const router = useRouter()
const {account} = route.query
// 是否通过验证
if(history.state.canAccessResetPassword !== 'true' || !account){
  router.push({name:'RePwInfo'})
}

const defaultFormValue = {
  username: account,
  password: '',
  rePassword: '',
}
const formRef =  ref(null)
const formValue = ref({...defaultFormValue});



const rules = ref({
  password: [
    { 
      required: true,
      trigger: 'blur',
      min: 6,
      message: '密码的长度不能少于6位'
    },
  ],
  rePassword: [{
    required: true,
    trigger: ['blur', 'input'],
    validator: (rule, value) => {
      return value === formValue.value.password;
    },
    message: "两次密码输入不一致",
  }]
});




const submitForm = async (e) => {
  
  formRef.value?.validate(async (errors) => {
    if (!errors) {
      try {
        const res = await api.resetPassword({
          username: formValue.value.username,
          password: formValue.value.password
        })
        formValue.value = {...defaultFormValue}
        $message.success("重置密码成功")
        
      } catch (err) {
        $message.error(err.message)
      }
    } else {
      $message.error('表单验证失败')
      console.log('errors', errors)
    }
    
  })
  
}

</script>

