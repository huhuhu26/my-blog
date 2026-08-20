<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { addArticleApi } from '@/api/article'
import { ElMessage, ElDrawer } from 'element-plus'
import { Folder } from '@element-plus/icons-vue'

const router = useRouter()
const article = ref({
  title: '',
  content: '',
  categoryId: ''
})

//分类抽屉
const drawer = ref(false);

//分类名
const categoryLabel = ref('全部')

const publish = async () => {
  if (!article.value.title || !article.value.content ) {
    alert('标题和内容不能为空')
    return
  }

  try {
    console.log(article.value);
    const res = await addArticleApi(article.value)

    if (res.code === 200) {
      ElMessage.success('发布成功')
      router.push('/articles')
    } else {
      alert(res.message)
      ElMessage.error('发布失败')
    }
  } catch (error) {
    ElMessage.error('发布失败')
  }
}

const selectCategory = (cid) => {
  article.value.categoryId = cid;
  drawer.value = false;
  // 根据id设置显示文字
  if(cid ===1) categoryLabel.value = '技术';
  if(cid ===2) categoryLabel.value = '生活';
  if(cid ===3) categoryLabel.value = '随笔';
  if(cid ===4) categoryLabel.value = '学习';
  if(cid ===5) categoryLabel.value = '游戏';
  if(cid ===0){
    queryParams.value.categoryId = null;
    categoryLabel.value = '全部';
  }
}


</script>

<template>
  <div class="publish-wrap">
    <el-card class="publish-card">
      <h2 class="title">✍️ 发布文章</h2>
      <el-form :model="article" label-width="80px">
        <el-form-item label="文章标题">
          <el-input v-model="article.title" placeholder="请输入文章标题" />
        </el-form-item>
        <el-form-item label="文章正文">
          <el-input
            v-model="article.content"
            type="textarea"
            :rows="12"
            placeholder="请开始撰写你的文章……"
          />
        </el-form-item>
        <el-form-item label="文章分类">
          <el-button text @click="drawer = true"><template #icon><el-icon><Folder /></el-icon></template>分类:{{ categoryLabel }}</el-button>

          <el-drawer v-model="drawer" direction="ttb" resizable>
            <div class="button-row">
              <el-button round @click="selectCategory(0)">全部</el-button>
              <el-button type="primary" round @click="selectCategory(1)">技术</el-button>
              <el-button type="success" round @click="selectCategory(2)">生活</el-button>
              <el-button type="info" round @click="selectCategory(3)">随笔</el-button>
              <el-button type="warning" round @click="selectCategory(4)">学习</el-button>
              <el-button type="danger" round @click="selectCategory(5)">游戏</el-button>
            </div>
          </el-drawer>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="publish">发布</el-button>
          <el-button @click="router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped>
.publish-wrap {
  padding: 40px 20px;
  display: flex;
  justify-content: center;
}
.publish-card {
  width: 700px;
}
.title {
  text-align: center;
  margin-bottom: 30px;
}
</style>
