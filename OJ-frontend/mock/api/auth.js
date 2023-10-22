import { resolveToken } from '../utils'

const token = {
  admin: 'admin123',
  student: 'student123',
}

export default [
  {
    url: '/api/auth/login',
    method: 'post',
    response: ({ body }) => {
      if (['student'].includes(body?.name)) {
        return {
          code: 0,
          data: {
            token: token[body.name],
            name: '学生用户',
            role: [body.name],
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
    url: '/api/auth/loginAdmin',
    method: 'post',
    response: ({ body }) => {
      if (['admin'].includes(body?.name)) {
        return {
          code: 0,
          data: {
            token: token[body.name],
            name: '管理员',
            role: [body.name],
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
