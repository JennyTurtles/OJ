import { request } from '@/utils'

export default {
  login: (data) => request.post('/auth/login', data, { noNeedToken: true }),
  getUser: () => request.get('/user'),
  refreshToken: () => request.post('/auth/refreshToken', null, { noNeedTip: true }),
}
