const users = {
  admin123: {
    id: 1,
    username: 'admin',
    name: '老师(admin)',
    avatar: 'https://assets.qszone.com/images/avatar.jpg',
    role: 'admin',
  },
  student123: {
    id: 2,
    username: 'student',
    name: '学生(student)',
    avatar: 'https://assets.qszone.com/images/avatar.jpg',
    role: 'student',
  },
}
export default [
  {
    url: '/api/account',
    method: 'get',
    response: ({ headers }) => {
      const token = headers?.authorization
      console.log(token)
      return {
        code: 0,
        data: {
          ...(users[token] || users.guest),
        },
      }
    },
  },
]
