<template>
  <CommonPage show-footer >
    <div class="f-c-c w-full">
      <div class="max-w-900 w-full flex justify-between gap-x-28">
        <div class="flex-1 p-20 rounded-10" border="solid 1px #ccc">
          <h3 class="text-center m-b-20">修改信息</h3>
          <n-form  ref="userInfoRef" :model="userInfoValue" :rules="userInofRules"  label-placement="left" label-align="right" label-width="58px" :show-require-mark="false">
            <n-form-item label="用户名" path="username">
              <n-input v-model:value="userInfoValue.username" ></n-input>
            </n-form-item>
            <n-form-item label="学号" >
              <n-input :value="disabledValue.studentNo" disabled></n-input>
            </n-form-item>
            <n-form-item label="中文名" path="chineseName">
              <n-input v-model:value="userInfoValue.chineseName" ></n-input>
            </n-form-item>
            <n-form-item label="班级" >
              <n-input :value="disabledValue.banji" disabled></n-input>
            </n-form-item>
            <n-form-item label="邮箱" path="email">
              <n-input v-model:value="userInfoValue.email" ></n-input>
            </n-form-item>
            <n-form-item label="问题" path="question">
              <n-input v-model:value="userInfoValue.question" ></n-input>
            </n-form-item>
            <n-form-item label="答案"  path="answer">
              <n-input v-model:value="userInfoValue.answer"></n-input>
            </n-form-item>
            <n-form-item class="f-c-c">
              <n-button type="primary" @click="onSaveUserInfo">保存</n-button>
            </n-form-item>
          </n-form>
        </div>
        <div class="flex-1 p-20 rounded-10" border="solid 1px #ccc">
          <h3 class="text-center m-b-20">修改密码</h3>
          <n-form  ref="pwdInfoRef"  :model="pwdInfoValue" :rules="pwdInforRules"  label-placement="left" label-align="right" label-width="68px" :show-require-mark="false">
            <n-form-item label="当前密码" path="password">
              <n-input type="password" v-model:value="pwdInfoValue.password" placeholder="请输入密码"></n-input>
            </n-form-item>
            <n-form-item label="新密码" path="newPassword">
              <n-input type="password" v-model:value="pwdInfoValue.newPassword" placeholder="请输入新密码" ></n-input>
            </n-form-item>
            <n-form-item label="确认密码"  path="confirmPassword">
              <n-input type="password" v-model:value="pwdInfoValue.confirmPassword" placeholder="请重复输入新密码"></n-input>
            </n-form-item>
            <n-form-item class="f-c-c">
              <n-button type="primary" @click="onSavePwd">保存</n-button>
            </n-form-item>
          </n-form>
        </div>
       
      </div>
    </div>
  </CommonPage>
</template>

<script setup>
import { ref,reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/api/user'
import {validateEmail} from '@/utils/validator'
import { useUserStore } from '@/store';


const DEFAULT_PWD_VALUE = {
  password:'',
  newPassword:'',
  confirmPassword:''
}

const router = useRouter()
const userStore = useUserStore()

const userInfoRef = ref(null)
const pwdInfoRef = ref(null)

const userInfoValue = ref({
  username: '',
  chineseName: '',
  email: '',
  question: '',
  answer: ''
});
const disabledValue = ref({
  banji:'',
  studentNo:'',
})
const pwdInfoValue = ref({...DEFAULT_PWD_VALUE})


const userInofRules = ref({
  username: {
      required: true,
      trigger: 'blur',
      message: '请输入用户名'
  },
  chineseName: {
    required: true,
    trigger: 'blur',
    min: 2,
    message: '中文名不能少于2位'
  },
  email: {
    required: true,
    trigger: 'blur',
    validator: (rule, value) => {
      console.log(validateEmail(value));
      return validateEmail(value)
    },
    message: "邮箱格式不正确",
  },
  question: {
    required: true,
    trigger: 'blur',
    message: "请输入问题",
  },
  answer: {
    required: true,
    trigger: 'blur',
    message: "请输入答案",
  }
});


const pwdInforRules = ref({
  password: [
    { 
      required: true,
      trigger: 'blur',
      message: '密码不能为空'
    },
  ],
  newPassword: [
    { 
      required: true,
      trigger: 'blur',
      min: 6,
      message: '密码的长度不能少于6位'
    },
  ],
  confirmPassword: [{
    required: true,
    trigger: ['blur', 'input'],
    validator: (rule, value) => {
      return value === pwdInfoValue.value.newPassword;
    },
    message: "两次密码输入不一致",
  }]
});


//获取用户数据
const getUserInfo = async (myUsername)=>{
  try {
    const res = await api.findStudentByusername({
      username: myUsername || userStore.username 
    })

    const {banji, studentNo, username, chineseName, email,question} = res.data
    disabledValue.value = {
      banji,
      studentNo
    }
    userInfoValue.value = {
      username,
      chineseName,
      email,
      question,
      answer:''
    }
  } catch (err) {
    (err)=>$message.error(err.message)
  }
}



const onSaveUserInfo = ()=>{
  userInfoRef.value?.validate(async (errors) => {
    if(!errors){
      try {
        const res = await api.updateUserInfo({
          ...userInfoValue.value
        })
        $message.success("修改用户信息成功")
        getUserInfo(userInfoValue.value.username)
      } catch (err) {
        $message.error(err.message)
      }
    }
  })
}
const onSavePwd = ()=>{
  
  pwdInfoRef.value?.validate(async (errors) => {
    if(!errors){
      try {
        const res = await api.updateUserPwd({
          password: pwdInfoValue.value.password,
          newPassword: pwdInfoValue.value.newPassword
        })
        $message.success("修改密码成功")
        pwdInfoValue.value = {...DEFAULT_PWD_VALUE}

      } catch (err) {
        $message.error(err.message)
      }
    }
  })
}


// 执行
getUserInfo()


</script>

