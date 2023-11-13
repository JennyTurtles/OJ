import { ref } from 'vue'

/**
 *  倒计时
 */
export default function useCountdown() {
  const leftTimeRef = ref(0)
  let timer = ref(null)

  function addZeroBeforeTime(value) {
    if (value < 10) {
      return '0' + value
    }
    return value
  }

  //倒计时
  const startCountdown = (leftTime) => {
    //清理之前的timer
    if (timer.value) {
      clearInterval(timer.value)
      timer.value = null
    }
    leftTimeRef.value = leftTime
    //开始倒计时
    timer.value = setInterval(() => {
      if (leftTimeRef.value <= 0 && timer.value) {
        //考试结束
        clearInterval(timer.value)
        timer.value = null
        return
      }
      leftTimeRef.value--
    }, 1000) //等1s后开始，间隔1s调用1次
  }

  const clearCountdown = () => {
    console.log('clear timer syncExamTimer')
    clearInterval(timer.value)
    timer.value = null
  }

  const day = computed(() => {
    if (leftTimeRef.value > 0) {
      return addZeroBeforeTime(Math.floor(leftTimeRef.value / 60 / 60 / 24))
    } else {
      return '0'
    }
  })
  const hour = computed(() => {
    if (leftTimeRef.value > 0) {
      return addZeroBeforeTime(Math.floor((leftTimeRef.value / 60 / 60) % 24))
    } else {
      return '00'
    }
  })
  const minute = computed(() => {
    if (leftTimeRef.value > 0) {
      return addZeroBeforeTime(Math.floor((leftTimeRef.value / 60) % 60))
    } else {
      return '00'
    }
  })
  const second = computed(() => {
    if (leftTimeRef.value > 0) {
      return addZeroBeforeTime(Math.floor(leftTimeRef.value % 60))
    } else {
      return '00'
    }
  })

  return {
    leftTime: leftTimeRef,
    startCountdown,
    clearCountdown,
    day,
    hour,
    minute,
    second,
  }
}
