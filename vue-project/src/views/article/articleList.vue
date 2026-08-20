<script setup>
import { ref, onMounted,nextTick } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { getArticleListApi,deleteArticleApi,updateArticleByIdApi,getArticleByIdApi } from '@/api/article'
import { ElMessage,ElMessageBox  } from 'element-plus'
import { Folder } from '@element-plus/icons-vue'

const router = useRouter()

const articleList = ref([])
const keyword = ref('')
const total = ref(0)
//当前登录用户名
const loginName = ref('');
const activeIndex = ref('');
const editDialogVisible = ref(false)
//分类名
const categoryLabel = ref('全部')

//分类抽屉

const drawer = ref(false);


const editForm = ref({
  id: null,
  title: '',
  content: '',
  categoryId: null,
})


const queryParams = ref({
  keyword: '',
  page: 1,
  pageSize: 10,
  onlyMine: false,
  categoryId: null
})



const handleSearch = () => {
  queryParams.value.page = 1
  // TODO: 实现搜索功能
  fetchArticles();
}




// 页面加载时请求列表
onMounted(async () => {
  loginName.value = JSON.parse(localStorage.getItem('userInfo')).username;
  activeIndex.value = 'all'
  fetchArticles();
})

const fetchArticles = async () => {
  try {
    const id = JSON.parse(localStorage.getItem('userInfo')).userId;
    console.log(queryParams.value);
    const res = await getArticleListApi(queryParams.value);
    console.log(res)
    if (res.code === 200) {
      articleList.value = res.data.rows
      total.value = res.data.total // 如果后端没返回 total，就先用当前页长度
    }
  } catch (error) {
    console.error('获取文章列表失败', error)
  }
}



const goDetail = (id) => {
  router.push('/article/' + id)
}

const goPublish = () => {
  router.push('/publish')
}

const handleSizeChange = (val) => {
  queryParams.value.page = 1
  queryParams.value.pageSize = val
  fetchArticles()
}

const handleCurrentChange = (val) => {
  queryParams.value.page = val
  fetchArticles()
}

const handleMenuSelect = (index)=>{
  if(index === 'all'){
    //全部文章
    queryParams.value.onlyMine = false
    queryParams.value.categoryId = null
    categoryLabel.value = '全部';
  }else if(index === 'my'){
    //我的文章
    queryParams.value.onlyMine = true
    queryParams.value.categoryId = null
  }
  queryParams.value.page = 1
  activeIndex.value = index;
  fetchArticles() //重新请求列表
}

//退出登录
const logout = () => {
  console.log('退出登录');
  localStorage.removeItem('userInfo');
  router.push('/login');
}

//删除文章
const deleteArticle = async (id) => {
  ElMessageBox.confirm('您确认删除这篇文章吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(async () => {
    const res = await deleteArticleApi(id);
  if (res.data.code === 200) {
    ElMessage.success('删除成功')
    queryParams.value.page = 1
    fetchArticles()
  } else {
    ElMessage(res.data.message)
  }
  }).catch(() => {
    ElMessage.info('您已取消删除');
  })
}

//编辑文章
const updateById = async (id) => {
  const res = await getArticleByIdApi(id);
  categoryLabel.value = res.data.category;
  editForm.value = res.data;
  editDialogVisible.value = true;
  editForm.value.id = id;
  
}

//提交修改
const submitEdit = async () => {
  try {
    const res = await updateArticleByIdApi(editForm.value.id,editForm.value);
    if (res.code === 200) {
      ElMessage.success('修改成功');
      editDialogVisible.value = false
      fetchArticles()
    } else {
      ElMessage.error('修改失败'+res.data.message)
    }
  } catch (error) {
    ElMessage.error('修改失败');
  }
}

const selectCategory = (cid) => {
  queryParams.value.categoryId = cid;
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

<div class="container">  
    <el-container>
      <el-header>
        <div class="left_group">
          <span class="title">博客文章</span>
        
          <el-button type="primary" @click="goPublish">写文章</el-button>
        </div>

        <span class="right_tool">
          <a href="">
            <el-icon><EditPen /></el-icon> 修改密码
          </a>
          <el-button type="primary" @click="logout">退出登录【{{ loginName }}】</el-button>
        </span>
      </el-header>

      <el-container>
        <el-aside width="400px">
            <el-aside width="260px">
              <div class="aside-wrap">
                <!-- 1.个人信息 -->
                <div class="aside-item">
                  <h3>👤 个人中心</h3>
                  <p>昵称：{{ loginName }}</p>
                  <p>欢迎来到我的博客</p>
                  
                </div>
              
                <!-- 2.文章分类 -->
                <div class="aside-item">
                  
                  <el-menu @select="handleMenuSelect" :default-active="activeIndex">
                    <el-menu-item index="all">全部文章</el-menu-item>
                    <el-menu-item index="my">我的文章</el-menu-item>
                  </el-menu>
                </div>
              
                <!-- 3.热门文章 -->
                <div class="aside-item">
                  <h3>🔥 热门文章</h3>
                  <div class="hot-item">文章2</div>
                  <div class="hot-item">文章3</div>
                </div>
              </div>
            </el-aside>

        </el-aside>

        <el-main>
            <!-- 搜索框（简单版） -->
            <!-- 外层flex容器，横向并排，不换行 -->
            <div style="display:flex; gap:12px; align-items:center; margin-bottom:20px;">
              <el-input
                v-model="queryParams.keyword"
                placeholder="搜索标题..."
                style="width: 400px;"
                clearable
              />
              <!-- 这里放你要加的：按钮 / 下拉框等 -->
              

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

              <el-button type="primary" @click="handleSearch">搜索</el-button>
            </div>

            <!-- 文章卡片列表 -->
            <div v-for="article in articleList" :key="article.id" style="margin-bottom: 10px; padding-right: 200px;">
              <el-card shadow="hover" @click="goDetail(article.id)" style="cursor: pointer;">
                <div style="display: flex; justify-content: space-between; align-items: center;">
                  <h3 style="margin: 0;">{{ article.title }}</h3>
                  <span style="color: #999; font-size: 13px;">{{ article.createAt }}</span>
                  <div v-if="activeIndex === 'my'">
                    <el-button size="small" type="primary" @click.stop="updateById(article.id)">编辑</el-button>
                    <el-button size="small" type="primary" @click.stop="deleteArticle(article.id)">删除</el-button>
                  </div>
                </div>
                <p style="color: #666; font-size: 14px; margin-top: 10px;">
                  <el-tag size="small" v-if="article.categoryId">分类: {{ article.category }}</el-tag>
                  <span style="margin-left: 10px;">作者昵称: {{ article.author }}</span>
                </p>
              </el-card>
            </div>

               <!-- 空状态 -->
            <el-empty v-if="articleList.length === 0" description="暂无文章" />

            <div class="demo-pagination-block">
              <el-pagination
                v-model:current-page="queryParams.page"
                v-model:page-size="queryParams.pageSize"
                :page-sizes="[5, 10, 20, 30]"
                :size="size"
                :disabled="disabled"
                :background="background"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
              />
            </div>     
        
        </el-main>

        <!-- 编辑文章 -->
        <!-- 编辑弹窗 -->
        <el-dialog v-model="editDialogVisible" title="编辑文章" width="600px">
          <el-form :model="editForm" label-width="80px">
            <el-form-item label="标题">
              <el-input v-model="editForm.title"></el-input>
            </el-form-item>

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
            <el-form-item label="内容">
              <el-input v-model="editForm.content" type="textarea" rows="6"></el-input>
            </el-form-item>
          </el-form>
          <template #footer>
            <el-button @click="editDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitEdit">保存修改</el-button>
          </template>
        </el-dialog>

      </el-container>
    </el-container>
</div>
</template>

<style scoped>
.el-header {
  display: flex;
  align-items: center;
  justify-content: space‑between;
  padding: 0 24px;
  /* 头部背景色，浅蓝灰，和element风格适配 */
  background-color: #ecf5ff;
  border-bottom: 1px solid #d9ecff;
}

.left_group {
  display: flex;
  align-items: center;
  gap: 24px;
}

.title {
  font-size: 24px;
  font-weight: 600;
  letter-spacing: 2px;
  color: #303133;
}

.right_tool {
  display: flex;
  gap:24px;
  align-items:center;
  margin-left: auto;
}

.aside-wrap {
  padding: 20px 12px;
}
.aside-item {
  margin-bottom: 30px;
}
.aside-item h3 {
  margin-bottom:12px;
  font-size:17px;
}
.hot-item {
  padding:8px 4px;
  cursor: pointer;
}
.hot-item:hover {
  background-color:#f5f7fa;
}

.button-row {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  align-items: center;
}


</style>
