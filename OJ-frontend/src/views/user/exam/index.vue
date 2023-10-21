<template>
  <CommonPage show-footer :showHeader="false">
    <h2 text-center>考试</h2>
  </CommonPage>
</template>

<script setup>


function notify(type) {
  $notification[type]({
    content: '说点啥呢',
    meta: '想不出来',
    duration: 2500,
    keepAliveOnHover: true,
  })
}
</script>
