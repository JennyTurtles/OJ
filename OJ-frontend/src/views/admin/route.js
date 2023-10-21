const Layout = () => import('@/layout/admin-layout.vue')

export default {
  name: 'Admin',
  path: '/admin',
  component: Layout,
  redirect: '/admin/index',
  meta: {
    title: '示例页面',
    icon: 'uil:pagelines',
    role: ['admin'],
    requireAuth: true,
    order: 3,
  },
  children: [
    {
      name: 'AdminHome',
      path: 'index',
      component: () => import('./home/index.vue'),
      meta: {
        title: 'CRUD表格',
        icon: 'ic:baseline-table-view',
        role: ['admin'],
        requireAuth: true,
        keepAlive: true,
      },
    },
  ],
}
