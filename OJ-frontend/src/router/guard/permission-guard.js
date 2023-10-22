import { getToken, refreshAccessToken, isNullOrWhitespace } from '@/utils'

// guard 控制“认证”
const WHITE_LIST = [
  '/user',
  '/user/index',
  '/user/faq',
  '/user/signup',
  '/login',
  '/404',
  '/admin-login',
]
export function createPermissionGuard(router) {
  router.beforeEach(async (to) => {
    const token = getToken()

    /** 没有token的情况 */
    if (isNullOrWhitespace(token)) {
      if (WHITE_LIST.includes(to.path)) return true
      return { name: 'UserLogin', query: { ...to.query, redirect: to.path } }
    }

    /** 有token的情况 */
    if (to.path === '/login') return { name: 'User' }

    refreshAccessToken()
    return true
  })
}
