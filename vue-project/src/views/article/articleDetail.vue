<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getArticleByIdApi } from '@/api/article'

const route = useRoute()
const article = ref({})

const getDetail = async () => {
  // 获取路由上面的id
  const id = route.params.id
  const res = await getArticleByIdApi(id)
  article.value = res.data
}

onMounted(() => {
  getDetail()
})
</script>

<template>
<div class="detail-wrap">
    <el-card class="detail-card">
      <div class="header">
        <h2 class="article-title">{{ article.title }}</h2>
        <div class="info">
          <span>作者：{{ article.author }}</span>
          <span>发布时间：{{ article.createAt }}</span>
          <span>更新时间：{{ article.updateAt }}</span>
          <span>分类：{{ article.category }}</span>
        </div>
      </div>
      <div class="content">
        {{ article.content }}
      </div>
      <div class="btn-box">
        <el-button @click="$router.push('/articles')">返回列表</el-button>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.detail-wrap {
  padding: 40px 20px;
  display: flex;
  justify-content: center;
}
.detail-card {
  width:820px;
}
.article-title{
  text-align:center;
  margin-bottom:16px;
}
.info{
  display:flex;
  gap:24px;
  justify-content:center;
  color:#666;
  margin-bottom:30px;
  padding-bottom:20px;
  border-bottom:1px solid #eee;
}
.content{
  line-height:2;
  font-size:16px;
  color:#333;
}
.btn-box{
  margin-top:40px;
  text-align:center;
}
</style>
