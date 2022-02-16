import axios, { Method } from 'axios';
import router from '@/router';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import { baseURL, statusCode } from '@/config';
import { unref } from 'vue';
import { useJWTStore } from '@/store';

const JWTStore = useJWTStore()

const instance = axios.create({
  baseURL: baseURL,
  timeout: 5000
})

// 请求拦截器
instance.interceptors.request.use(
  (config: any) => {
    config.headers.Authorization = JWTStore.value
    return config
  }, () => {
    ElMessage.error('请检查网络')
    return Promise.reject('网络错误')
  }
)

// 响应拦截器
instance.interceptors.response.use(
  res => {
    // 成功
    if (res.data.code === statusCode.SUCCESS) {
      return res.data.data
    }
    // 未登录
    if (res.data.code == statusCode.UNAUTHORIZED) {
      ElMessage.error('身份验证失败，跳转至登录页')
      JWTStore.reset()
      return new Promise((resolve, reject) => {
        router.replace('login').then(() => {
          reject('请重新登录')
        })
      })
    }
    // 未授权
    if (res.data.code == statusCode.FORBIDDEN) {

      // todo 刷新权限

      return Promise.reject(res.data.msg)
    }
    // 其它错误
    return Promise.reject(res.data.msg)
  }, () => {
    ElMessage.error('请检查网络')
    return Promise.reject('响应错误')
  }
)

/**
 * 发送网络请求
 * @param path 请求地址后缀
 * @param method HTTP 方法
 * @param data 传递的数据
 * @return Promise
 */
function request<T>(path: string, method: Method = 'get', data ?: object): Promise<T> {
  return instance.request({
    url: path,
    method: method,
    data: unref(data)
  })
}

export default request
