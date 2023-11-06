<template>
  <transition name="fade-slide" mode="out-in" appear>
    <section class="container-w min-h-full  flex-col bg-[#f5f6fb]  dark:bg-hex-121212" m-auto>
      <slot />
      <AppFooter v-if="showFooter" mt-15 />
      <n-back-top :bottom="20" />
    </section>
  </transition>
</template>

<script setup>
defineProps({
  showFooter: {
    type: Boolean,
    default: false,
  },
})
</script>
