import request from '@/utils/request'

//注册接口
export const registerApi = (data) => request.post('/user/register',data)