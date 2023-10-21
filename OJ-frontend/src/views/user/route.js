const Layout = () => import('@/layout/user-layout.vue')

export default {
  name: 'User',
  path: '/user',
  component: Layout,
  redirect: '/user/index',
  meta: {
    title: '示例页面',
    icon: 'uil:pagelines',
    role: [],
    requireAuth: false,
    order: 1,
  },
  children: [
    {
      name: 'UserIndex',
      path: 'index',
      component: () => import('./index.vue'),
      meta: {
        title: '学生',
        icon: 'ic:baseline-table-view',
        role: [],
        requireAuth: false,
        keepAlive: true,
      },
    },
    {
      name: 'Exam',
      path: 'exam',
      component: () => import('./exam/index.vue'),
      meta: {
        title: '考试',
        icon: 'ic:baseline-table-view',
        role: [],
        requireAuth: false,
        keepAlive: true,
      },
    },
    {
      name: 'Class',
      path: 'class',
      component: () => import('./class/index.vue'),
      meta: {
        title: '加入班级',
        icon: 'ic:baseline-table-view',
        role: ['student'],
        requireAuth: true,
        keepAlive: true,
      },
    },
    {
      name: 'FAQ',
      path: 'faq',
      component: () => import('./faq/index.vue'),
      meta: {
        title: '加入班级',
        icon: 'ic:baseline-table-view',
        role: [],
        requireAuth: false,
        keepAlive: true,
      },
    },
  ],
}
