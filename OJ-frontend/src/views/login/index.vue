<template>
  <AppPage :show-footer="true" bg-cover :style="{ backgroundImage: `url(${bgImg})` }">
    <LoginForm />
  </AppPage>
</template>

<script setup>
import bgImg from '@/assets/images/login_bg.webp'
import LoginForm from '~/src/components/LoginForm.vue';
</script>
