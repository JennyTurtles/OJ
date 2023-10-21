<template>
  <CommonPage show-footer>
    Home Index
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
