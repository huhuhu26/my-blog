import axios from "axios";
import { ElMessage } from "element-plus";
import router from "../router";

//创建axios事例对象
const request = axios.create({
  baseURL: "/api",
  timeout: 600000,
});

//axios响应拦截器
request.interceptors.response.use(
  (response) => {
    // console.log(config);
    return response.data;
  },
  (error) => {
    if (error.response.status === 401) {
      ElMessage.error("登录已过期，请重新登录");
      localStorage.removeItem("token");
      router.push("/login");
    }else{
      ElMessage.error("接口访问异常");
    }

    return Promise.reject(error);
  }
);

//axios请求拦截器
request.interceptors.request.use(
  (config) => { 
    const loginUser = JSON.parse (localStorage.getItem('userInfo') ) ;
    
    if (loginUser && loginUser.token) {
      config.headers.Authorization = `Bearer ${loginUser.token}`;
      
    }
    return config;
  },
  (error) => {
    
  }
);
export default request;