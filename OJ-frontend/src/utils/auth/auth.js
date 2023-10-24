import { router } from '@/router'
import { isAdminEnd } from '../common'

export function toLogin() {
  const currentRoute = unref(router.currentRoute)
  const needRedirect =
    !currentRoute.meta.requireAuth && !['/404', '/login'].includes(router.currentRoute.value.path)
  router.replace({
    path: isAdminEnd() ? '/admin/login' : '/user/login',
    query: needRedirect ? { ...currentRoute.query, redirect: currentRoute.path } : {},
  })
}

export function toFourZeroFour() {
  router.replace({
    path: '/404',
  })
}
