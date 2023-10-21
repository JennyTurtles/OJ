<template>
  <n-layout wh-full>
    <article flex-col flex-1 overflow-hidden>
      <header
        border-b="1 solid #eee"
        class="f-c-c bg-primary text-white"
        dark="bg-dark border-0"
        :style="`height: ${header.height}px`"
      >
        <div class="f-c-c container-w w-full h-full">
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
          
          <div class="f-c-c gap-x-10">
            <n-button type="default" color="#fff" ghost>
              注册
            </n-button>
            <n-button type="default" color="#fff" ghost @click="showModal = true">
              登录
            </n-button>
            <!-- <n-button text color="#fff" h-full px-10 size="small">注册</n-button>
            <span px-8>或</span>
            <n-button text color="#fff" h-full px-10 size="small">登录</n-button> -->
          </div>
        </div>
      </header>
      


      <section flex-1 overflow-hidden bg-hex-f5f6fb dark:bg-hex-101014>
        <!-- 登录Modal -->
        <n-modal
          v-model:show="showModal"
          class="custom-card"
          :style="bodyStyle"
          title="卡片预设"
          size="huge"
          :bordered="false"
          :segmented="segmented"
        >
         
        <LoginForm />
         
        </n-modal>
        <router-view v-slot="{ Component, route }">
          <KeepAlive :include="keepAliveNames">
            <component :is="Component"  :key="route.fullPath" />
          </KeepAlive>
        </router-view>
      </section>
    </article>
  </n-layout>
</template>

<script setup>
import {ref} from 'vue'
import { useAppStore } from '@/store'
import { header } from '~/settings'
import { useRouter } from 'vue-router';
import LoginForm from '../components/LoginForm.vue';

const appStore = useAppStore()
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

const showModal=ref(false)

</script>
