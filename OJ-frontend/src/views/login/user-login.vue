<template>
  <AppPage :show-footer="true" bg-cover :style="{ backgroundImage: `url(${bgImg})` }">
    <LoginForm :login="api.login" success-path="/user" :show-close-icon="false"/>
  </AppPage>
</template>

<script setup>
import bgImg from '@/assets/images/login_bg.webp'
import LoginForm from '@/components/LoginForm.vue';
import api from '@/api/user';
</script>
