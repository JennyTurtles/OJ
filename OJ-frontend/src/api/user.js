import { request } from '@/utils'

export default {
  login: (data) => request.post('/login/student', data, { noNeedToken: true }),
  getExamList: (config) => request.get('/exam', config),
  getUser: () => request.get('/account'),
  takeExam: (data) => request.post('/exam/take', data),
  getSyncTime: () => request.get('/exam/sync_time'),
  // refreshToken: () => request.post('/auth/refreshToken', null, { noNeedTip: true }),
}
