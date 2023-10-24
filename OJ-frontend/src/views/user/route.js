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
        title: '首页',
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
        role: ['student'],
        requireAuth: true,
        keepAlive: true,
      },
    },
    {
      name: 'Problemset',
      path: 'problemset',
      component: () => import('./problemset/index.vue'),
      meta: {
        title: '考试题目列表',
        icon: 'ic:baseline-table-view',
        role: ['student'],
        requireAuth: true,
        keepAlive: false,
      },
    },
    {
      name: 'Trainset',
      path: 'trainset',
      component: () => import('./trainset/index.vue'),
      meta: {
        title: '训练题目列表',
        icon: 'ic:baseline-table-view',
        role: ['student'],
        requireAuth: true,
        keepAlive: false,
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
        title: '常见问题及解答',
        icon: 'ic:baseline-table-view',
        role: [],
        requireAuth: false,
        keepAlive: true,
      },
    },
    {
      name: 'Signup',
      path: 'signup',
      component: () => import('./signup/index.vue'),
      meta: {
        title: '注册',
        icon: 'ic:baseline-table-view',
        role: [],
        requireAuth: false,
        keepAlive: true,
      },
    },
  ],
}
