import request from '@/utils/request'

// 获取文章列表
export const getArticleListApi = (params) => request.get('/article/list', {params})

//发表文章
export const addArticleApi = (data) => request.post('/article/add', data)

//获取文章详情
export const getArticleByIdApi = (id) => request.get(`/article/detail/${id}`)

//修改文章
export const updateArticleByIdApi = (id, data) => request.put(`/article/${id}`, data)

//删除文章
export const deleteArticleApi = (id) => request.delete(`/article/${id}`)