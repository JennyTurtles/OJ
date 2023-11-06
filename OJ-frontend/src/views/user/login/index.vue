<template>
  <section class="flex-1 min-h-full flex-col  items-center bg-hex-f5f6fb cus-scroll-y "  dark:bg-hex-101014 bg-cover  :style="{ backgroundImage: `url(${bgImg})` }">
    <AppPage :show-footer="true">
      <LoginForm :login="api.login" success-path="/user" :show-close-icon="false" />
    </AppPage>
  </section>
</template>

<script setup>
import bgImg from '@/assets/images/login_bg.webp'
import LoginForm from '@/components/LoginForm.vue'
import api from '@/api/user'
</script>
