<template>
  <div wh-full flex-col overflow-hidden dark="text-white">
      <header
        border-b="1 solid #eee"
        class="bg-primary text-white sticky "
        dark="bg-dark border-0"
      >
        <div class="f-c-c container-w w-full m-auto" :style="`height: ${header.height}px`">
          <div flex items-center>
            <img src="@/assets/images/logo.png" height="44" class="mr-30" />
          </div>
          
          <div class="flex-1 flex items-stretch h-full gap-x-30px gap-y-20px">
            <router-link
              v-for="item,idx in [{label:'首页', to:'/user/index'},{label:'考试', to:'/user/exam'},{label:'加入班级', to:'/user/class'}]"
              :to="item.to" :key="idx" class="f-c-c px-4 hover:color-gray">
            {{ item.label }}
            </router-link>
            <n-dropdown
              trigger="hover"
              placement="bottom-start"
              :options="helpOptions"
              @select="handleSelect"
            >
            <div class="f-c-c px-4 cursor-pointer hover:color-gray group" > 
              <span>
                帮助
              </span>
              <SvgIcon icon="down-arrow" class="group-hover:-rotate-180 ease-in duration-200  ml-2" /> 
            </div>
            </n-dropdown>
          </div>

          <div v-if="userStore.role.length>0 && userStore.role.some(r=>r==='student')">
              <UserAvatar />  
          </div>
          <div class="f-c-c gap-x-10" v-else>
            
            <n-button type="default" color="#fff" ghost @click="userStore.openLoginModal">
              登录
            </n-button>
            <n-button type="default" color="#fff" ghost @click="$router.push({name:'Signup'})">
              注册
            </n-button>
            <!-- <n-button text color="#fff" h-full px-10 size="small">注册</n-button>
            <span px-8>或</span>
            <n-button text color="#fff" h-full px-10 size="small">登录</n-button> -->
          </div>
          
        </div>
      </header>

      <section flex-1 min-h-300 overflow-hidden flex-col items-center bg-hex-f5f6fb dark:bg-hex-101014>
        <!-- 登录Modal -->
        <n-modal
          v-model:show="userStore.showLoginModal"
          class="custom-card"
          :style="bodyStyle"
          title="卡片预设"
          size="huge"
          :bordered="false"
          :segmented="segmented"
        >
         
        <LoginForm :login="api.login" @close="userStore.closeLoginModal"  :title="isAdminEnd()?'OJ系统 - 管理端':'OJ系统 - 登录'"/>
         
        </n-modal>
        <router-view v-slot="{ Component, route }">
          <!-- TODO: keepAlive的处理 -->
          <!-- <KeepAlive :include="keepAliveNames"> -->
            <component :is="Component"  :key="route.fullPath" />
          <!-- </KeepAlive> -->
        </router-view>
      </section>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import { useAppStore, useUserStore } from '@/store'
import { header } from '~/settings'
import { useRouter } from 'vue-router';
import LoginForm from '../components/LoginForm.vue';
import UserAvatar from './components/header/components/UserAvatar.vue';
import api from '@/api/user';
import { getToken, isAdminEnd, isNullOrWhitespace } from '../utils';

const appStore = useAppStore()
const userStore = useUserStore()
const router = useRouter()

const helpOptions = [
  {
    label: '常见问题及解答',
    key: '/faq'
  },
  {
    label: 'C 手册',
    key: "0"
  },
  {
    label: 'C++ 手册',
    key: '1'
  },
  {
    label: 'JAVA 手册',
    key: '2'
  }
]
const handleSelect = (path)=>{
  console.log(path);
  if(path){
    router.push(`/user${path}`)
  }
}


onMounted(() => {
  const token = getToken()
  if(isNullOrWhitespace(token)){
    userStore.openLoginModal()
  }
  console.log('remember to open modal');
})

</script>
