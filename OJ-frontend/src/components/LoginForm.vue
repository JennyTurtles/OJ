<template>
  <div
      style="transform: translateY(25px)"
      class="m-auto max-w-700 min-w-345 f-c-c rounded-10 bg-white bg-opacity-60 p-15 card-shadow"
      dark:bg-dark
    >
   
    <div w-320 flex-col px-20 py-35>
      <h5 f-c-c text-24 font-normal color="#6a6a6a" mb-30>
        <img src="@/assets/images/logo.png" height="50" class="mr-10" />
        {{ title }}
      </h5>

    
      <n-form ref="formRef" :model="loginInfo" :rules="rules">
        <n-form-item path="name" :show-label="false" mb-10>
          <n-input
            v-model:value="loginInfo.name"
            autofocus
            class="h-48 items-center text-16"
            placeholder="手机号/学号/邮箱地址"
            :maxlength="20"
          >
            <template #prefix>
              <icon-material-symbols:account-circle-outline class="mr-8 text-20 opacity-40" />
            </template>
          </n-input>
        </n-form-item>
        <n-form-item path="password" :show-label="false" >
          <n-input
            v-model:value="loginInfo.password"
            class="h-48 items-center text-16"
            type="password"
            show-password-on="mousedown"
            placeholder="请输入密码"
            :maxlength="20"
            @keydown.enter="handleLogin"
          >
            <template #prefix>
              <icon-ri:lock-password-line class="mr-8 text-20 opacity-40" />
            </template>
          </n-input>
       </n-form-item>

       <div  flex gap-x-50>
        <n-button text color="white" p-10>注册</n-button>
        <n-button text color="white" p-10>忘记密码</n-button>
      </div>

        <div mt-20>
          <n-button attr-type="submit"
            h-50
            w-full
            rounded-5
            text-16
            type="primary"
            :loading="loading"
            @click="handleLogin"
          >
          登录
        </n-button>
         
      </div>
      </n-form>
     
    </div>
  </div>
</template>
<script setup>
import { lStorage, setToken } from '@/utils'
import { useStorage } from '@vueuse/core'
import { addDynamicRoutes } from '@/router'
import api from '@/api'


const title = import.meta.env.VITE_TITLE

const router = useRouter()
const { query } = useRoute()

const loginInfo = ref({
  name: '',
  password: '',
})
const formRef = ref(null);
const rules = {
      name: [
        {
          required: true,
          validator(rule, value) {
            if (!value.trim()) {
              return new Error("不能为空");
            } 
            return true;
          },
          trigger: ["blur"]
        }
      ],
      password: [
        {
          required: true,
          validator(rule, value) {
            if (!value.trim()) {
              return new Error("不能为空");
            } 
            return true;
          },
          trigger: ["blur"]
        }
      ],
     
    };

initLoginInfo()

function initLoginInfo() {
  const localLoginInfo = lStorage.get('loginInfo')
  if (localLoginInfo) {
    loginInfo.value.name = localLoginInfo.name || ''
    loginInfo.value.password = localLoginInfo.password || ''
  }
}

const loading = ref(false)
async function handleLogin() {
  console.log(loginInfo.value);
  const { name, password } = loginInfo.value
  try {
    await formRef.value.validate()
    try {
      loading.value = true
      $message.loading('正在验证...')
      const res = await api.login({ name, password: password.toString() })
      console.log(res);
      $message.success('登录成功')
      setToken(res.data.token)

      await addDynamicRoutes()
      if (query.redirect) {
        const path = query.redirect
        Reflect.deleteProperty(query, 'redirect')
        router.push({ path, query })
      } else {
        router.push('/')
      }
    } catch (error) {
      console.error(error)
      $message.removeMessage()
    }
    loading.value = false
  } catch (error) {
    console.error(error) //未通过前端验证
  }

  
  
}
</script>
