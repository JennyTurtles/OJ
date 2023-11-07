<template>
  <CommonPage show-footer >
    <div class="f-c-c w-full">
      <div class="max-w-900 w-full">
        <n-form  ref="formRef" :model="formValue" :rules="rules" class="flex flex-col mt-20 px-15" >
          <n-form-item label="登录账号" path="username">
            <n-input v-model:value="formValue.username" placeholder="请输入登录账号"></n-input>
          </n-form-item>
          <n-form-item label="问题" path="question">
            <n-input v-model:value="formValue.question" disabled placeholder="填入登录账号后自动获取"></n-input>
          </n-form-item>
          <n-form-item label="答案"  path="answer">
            <n-input v-model:value="formValue.answer" placeholder="请输入答案"></n-input>
          </n-form-item>
          <n-form-item>
            <n-button type="primary" @click="submitForm">确认</n-button>
          </n-form-item>
        </n-form>
      </div>
    </div>
  </CommonPage>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter} from 'vue-router';
import api from '@/api/user'

const router = useRouter()
const {username} = history.state
console.log(username);

const formRef =  ref(null)
const formValue = ref({
  username: username||'',
  question: '',
  answer: '',
});



const rules = ref({
  username: { 
      key: 'username',
      required: true,
      trigger: 'blur',
      validator: async (rule, value) => {
        if(!value) {
          return Promise.reject(new Error("用户名不能为空"))
        }
        console.log('trigger by validate');
        try {
          const res = await api.findStudentByusername({username:value})
          const {question} = res.data
          console.log(formValue.value.question);
          if(question){
            formValue.value.question =  question
            return
          }
          return Promise.reject(new Error("无问题，请至教师处重置密码"))
          
        } catch (err) {
          return  Promise.reject(new Error(err.message))
        }
        
      }
    },
  answer: [{
    required: true,
    trigger: 'blur',
    message: '答案不能为空'
  }]
});




const submitForm = (e) => {
  // console.log('push');
  // router.push({
  //         name: 'ResetPassword',
  //         query: {account: 'felix'},
  //         state: { canAccessResetPassword: 'true' }
  //       })
  e.preventDefault()
  $message.loading('验证中')
  const {username, answer} = formValue.value
  formRef.value?.validate((errors) => {
    if (!errors) {
      api.confirmQuestionAnswer({
          "username": username,
          "answer": answer
      }).then(res=>{
        $message.success("验证成功",{duration:1})
        router.push({
          name: 'ResetPassword',
          query: {account: username},
          state: { canAccessResetPassword: 'true' }
        })
      }).catch(err=>{
        $message.error(err.message)
      })
    } else {
      $message.error('表单验证失败')
      console.log('errors', errors)
    }
    
  })
};

onMounted(() => {
  if(formValue.value.username){
    console.log('run');
    formRef.value?.validate((errors) => {
            if (errors) {
              console.error(errors);
            }else{
              console.log('yes');
            }
          }, (rule) => {
      return rule?.key === "username";
    })
  }
})


</script>

