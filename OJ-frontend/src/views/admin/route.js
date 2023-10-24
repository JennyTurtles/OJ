const Layout = () => import('@/layout/admin-layout.vue')

export default {
  name: 'Admin',
  path: '/admin',
  component: Layout,
  redirect: '/admin/index',
  meta: {
    title: '示例页面',
    icon: 'uil:pagelines',
    role: [],
    requireAuth: false,
    order: 3,
  },
  children: [
    {
      name: 'AdminIndex',
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
