import { createRouter, createWebHistory, createWebHashHistory } from 'vue-router'
import { setupRouterGuard } from './guard'
import {
  ADMIN_UNAUTH_ROUTE,
  basicRoutes,
  EMPTY_ROUTE,
  NOT_FOUND_ROUTE,
  USER_UNAUTH_ROUTE,
} from './routes'
import { getToken, isAdminEnd, isNullOrWhitespace } from '@/utils'
import { useUserStore, usePermissionStore } from '@/store'

const isHash = import.meta.env.VITE_USE_HASH === 'true'
export const router = createRouter({
  history: isHash ? createWebHashHistory('/') : createWebHistory('/'),
  routes: basicRoutes,
  scrollBehavior: () => ({ left: 0, top: 0 }),
})

export async function setupRouter(app) {
  await addDynamicRoutes()
  setupRouterGuard(router)
  app.use(router)
}

export async function resetRouter() {
  const basicRouteNames = getRouteNames(basicRoutes)
  router.getRoutes().forEach((route) => {
    const name = route.name
    if (!basicRouteNames.includes(name)) {
      router.removeRoute(name)
    }
  })

  await addDynamicRoutes()
}

export async function addDynamicRoutes() {
  const token = getToken()

  // 没有token情况
  if (isNullOrWhitespace(token)) {
    const permissionStore = usePermissionStore()
    const accessRoutes = permissionStore.generateRoutes()

    accessRoutes.forEach((route) => {
      !router.hasRoute(route.name) && router.addRoute(route)
    })

    if (isAdminEnd()) {
      router.addRoute(ADMIN_UNAUTH_ROUTE)
    } else {
      router.addRoute(USER_UNAUTH_ROUTE)
    }
    return
  }

  // 有token的情况
  try {
    const userStore = useUserStore()
    const permissionStore = usePermissionStore()
    !userStore.name && (await userStore.getUserInfo())
    const accessRoutes = permissionStore.generateRoutes(userStore.role)

    accessRoutes.forEach((route) => {
      router.addRoute(route)
    })

    router.hasRoute(EMPTY_ROUTE.name) && router.removeRoute(EMPTY_ROUTE.name)
    router.addRoute(NOT_FOUND_ROUTE)
  } catch (error) {
    console.error(error)
  }
}

export function getRouteNames(routes) {
  return routes.map((route) => getRouteName(route)).flat(1)
}

function getRouteName(route) {
  const names = [route.name]
  if (route.children && route.children.length) {
    names.push(...route.children.map((item) => getRouteName(item)).flat(1))
  }
  return names
}
