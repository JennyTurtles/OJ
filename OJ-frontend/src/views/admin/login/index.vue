<template>
  <AppPage :show-footer="true" bg-cover :style="{ backgroundImage: `url(${bgImg})` }">
    <LoginForm :login="api.login" success-path="/admin" :show-close-icon="false" title="OJ系统 - 管理端"/>
  </AppPage>
</template>

<script setup>
import bgImg from '@/assets/images/login_bg.webp'
import LoginForm from '@/components/LoginForm.vue';
import api from '@/api/admin';
</script>
