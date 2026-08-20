import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/login/login.vue'
import ArticleList from '../views/article/articleList.vue'
import ArticleDetail from '../views/article/articleDetail.vue'
import Register from '../views/register/register.vue'
import PublishArticle from '../views/article/publisharticle.vue'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/articles', component: ArticleList },
  { path: '/article/:id', component: ArticleDetail },
  { path: '/publish', component: PublishArticle },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router