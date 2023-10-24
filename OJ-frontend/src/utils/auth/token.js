import { isAdminEnd, lStorage } from '@/utils'

const DURATION = 6 * 60 * 60 //单位：秒

function _getTokenCode() {
  return `ACCESS_TOKEN_${isAdminEnd() ? 'ADMIN' : 'USER'}`
}

export function getToken() {
  return lStorage.get(_getTokenCode())
}

export function setToken(token) {
  lStorage.set(_getTokenCode(), token, DURATION)
}

export function removeToken() {
  lStorage.remove(_getTokenCode())
}

export async function refreshAccessToken() {
  // const tokenItem = lStorage.getItem(_getTokenCode())
  // if (!tokenItem) {
  //   return
  // }
  // const { time } = tokenItem
  // // token生成或者刷新后30分钟内不执行刷新
  // if (Date.now() - time <= 1000 * 60 * 30) return
  // try {
  //   const res = await api.refreshToken()
  //   setToken(res.data.token)
  // } catch (error) {
  //   console.error(error)
  // }
}
