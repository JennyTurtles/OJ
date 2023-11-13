const Layout = () => import('@/layout/user-layout.vue')

import { RouterView } from 'vue-router'

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
      name: 'TakeClass',
      path: 'take-class',
      component: () => import('./take-class/index.vue'),
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
    {
      name: 'RePwInfo',
      path: 're-pw-info',
      component: () => import('./re-pw-info/index.vue'),
      meta: {
        title: '学生重置密码',
        icon: 'ic:baseline-table-view',
        role: [],
        requireAuth: false,
        keepAlive: true,
      },
    },
    {
      name: 'ResetPassword',
      path: 'reset-password',
      component: () => import('./reset-password/index.vue'),
      meta: {
        title: '学生重置密码',
        icon: 'ic:baseline-table-view',
        role: [],
        requireAuth: false,
        keepAlive: true,
      },
    },
    {
      name: 'PersonSetting',
      path: 'person-setting',
      component: () => import('./person-setting/index.vue'),
      meta: {
        title: '个人设置',
        icon: 'ic:baseline-table-view',
        role: ['student'],
        requireAuth: true,
        keepAlive: false,
      },
    },
    {
      name: 'ExamList',
      path: 'exam-list',
      component: () => import('./exam-list/index.vue'),
      meta: {
        title: '考试列表',
        icon: 'ic:baseline-table-view',
        role: ['student'],
        requireAuth: true,
        keepAlive: true,
      },
    },

    {
      name: 'Contest',
      path: 'contest/:examId',
      component: RouterView,
      meta: {
        role: ['student'],
        requireAuth: true,
        keepAlive: false,
      },
      children: [
        {
          name: 'ContestIndex',
          path: '',
          component: () => import('./contest/index.vue'),
          meta: {
            title: '题目列表',
            icon: 'ic:baseline-table-view',
            role: ['student'],
            requireAuth: true,
            keepAlive: false,
          },
        },
        {
          name: 'ContestProblems',
          path: 'problems',
          component: RouterView,
          meta: {
            title: 'only rank',
            icon: 'ic:baseline-table-view',
            role: ['student'],
            requireAuth: true,
            keepAlive: false,
          },
          children: [
            {
              name: 'ContestProblemDetail',
              path: ':problemId',
              component: () => import('./contest/problem.vue'),
              meta: {
                title: '题目详情',
                icon: 'ic:baseline-table-view',
                role: ['student'],
                requireAuth: true,
                keepAlive: false,
              },
            },
          ],
        },
        {
          name: 'ContestScoreOnlyRank',
          path: 'score-only-rank',
          component: () => import('./contest/score-only-rank.vue'),
          meta: {
            title: 'only rank',
            icon: 'ic:baseline-table-view',
            role: ['student'],
            requireAuth: true,
            keepAlive: false,
          },
        },
        {
          name: 'ContestScoreRankStatus',
          path: 'score-rank-status',
          component: () => import('./contest/score-rank-status.vue'),
          meta: {
            title: 'rank status',
            icon: 'ic:baseline-table-view',
            role: ['student'],
            requireAuth: true,
            keepAlive: false,
          },
        },
        {
          name: 'ContestScoreTop10',
          path: 'score-top10',
          component: () => import('./contest/score-top10.vue'),
          meta: {
            title: 'top10',
            icon: 'ic:baseline-table-view',
            role: ['student'],
            requireAuth: true,
            keepAlive: false,
          },
        },
      ],
    },
    {
      name: 'Training',
      path: 'training/:examId',
      component: RouterView,
      meta: {
        role: ['student'],
        requireAuth: true,
      },
      children: [
        {
          name: 'TrainingIndex',
          path: '',
          component: () => import('./training/index.vue'),
          meta: {
            title: '训练考试',
            icon: 'ic:baseline-table-view',
            role: ['student'],
            requireAuth: true,
            keepAlive: false,
          },
        },
        {
          name: 'TrainingScoreTrainRank',
          path: 'score-train-rank',
          component: () => import('./training/score-train-rank.vue'),
          meta: {
            title: '训练成绩',
            icon: 'ic:baseline-table-view',
            role: ['student'],
            requireAuth: true,
            keepAlive: false,
          },
        },
      ],
    },
  ],
}
