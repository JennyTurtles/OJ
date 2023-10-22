import { request } from '@/utils'

export default {
  login: (data) => request.post('/auth/loginAdmin', data, { noNeedToken: true }),
}
