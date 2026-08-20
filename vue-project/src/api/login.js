import request from '@/utils/request'

//登录接口
export const loginApi = (data) => request.post('/user/login',data);