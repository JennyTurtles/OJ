<template>
  <AppProvider>
    <!-- 取当前组件，动态渲染，优点：更灵活渲染匹配的组件 -->
    <router-view v-slot="{ Component }">
      <component :is="Component" />
    </router-view>
  </AppProvider>
</template>

<script setup>
import AppProvider from '@/components/common/AppProvider.vue'
</script>
