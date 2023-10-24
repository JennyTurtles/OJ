import { resolveToken } from '../utils'

const token = {
  admin: 'admin123',
  student: 'student123',
}

export default [
  {
    url: '/api/login/student',
    method: 'post',
    response: ({ body }) => {
      if (['student'].includes(body?.username)) {
        return {
          code: 0,
          data: {
            token: token[body.username],
            name: '学生用户',
            role: body.username,
            username: body.username,
          },
        }
      } else {
        return {
          code: -1,
          message: '没有此用户',
        }
      }
    },
  },
  {
    url: '/api/login/admin',
    method: 'post',
    response: ({ body }) => {
      if (['admin'].includes(body?.username)) {
        return {
          code: 0,
          data: {
            token: token[body.username],
            name: '管理员',
            role: body.username,
            username: body.username,
          },
        }
      } else {
        return {
          code: -1,
          message: '没有此用户',
        }
      }
    },
  },
  {
    url: '/api/auth/refreshToken',
    method: 'post',
    response: ({ headers }) => {
      return {
        code: 0,
        data: {
          token: resolveToken(headers?.authorization),
        },
      }
    },
  },
]
