import { request } from '@/utils'

export default {
  login: (data) => request.post('/login/student', data, { noNeedToken: true }),
  getUser: () => request.get('/account'),
  getExamList: (config) => request.get('/exam/examList', config),

  findStudentByusername: (data) =>
    request.post('/user/findStudentByusername', data, { noNeedToken: true }),
  confirmQuestionAnswer: (data) =>
    request.post('/user/confirmQuestionAnswer', data, { noNeedToken: true }),
  resetPassword: (data) =>
    request.post('/user/updatePasswordByUserName', data, { noNeedToken: true }),

  updateUserInfo: (data) => request.post('/user/editUserInfo', data),
  updateUserPwd: (data) => request.post('/user/editUserPassword', data),
  signUser: (data) => request.post('/user/signUser', data, { noNeedToken: true }),

  takeExam: (data) => request.post('/exam/take', data),
  getSyncTime: () => request.get('/exam/sync_time'),

  // refreshToken: () => request.post('/auth/refreshToken', null, { noNeedTip: true }),
}
