import { request } from '@/utils'

export default {
  login: (data) => request.post('/login/admin', data, { noNeedToken: true }),
}
