import axios, { Method } from 'axios';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import { baseURL } from '@/config';
import { Ref, unref } from 'vue';
import { useJWTStore } from '@/store';
import { removeNull } from '@/utils/objUtil'
import useAccount from '@/composables/useAccount';
import { reloadRouter } from '@/router';

const { reset } = useAccount()

const instance = axios.create({
  baseURL: baseURL,
  timeout: 5000
})

// 请求拦截器
instance.interceptors.request.use(
  (config: any) => {
    const JWTStore = useJWTStore()
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
    if (res.data.code === 0) {
      return res.data.data
    }
    // 身份验证错误
    if (res.data.code >= 9100 && res.data.code < 9200) {
      reset().then(() => {
        ElMessage.error("身份验证错误，请重新登录")
      })
    }
    // 未授权
    if (res.data.code >= 9200 && res.data.code < 9300) {
      reloadRouter()
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
function request<T>(path: string, method: Method = 'get', data ?: Ref<object> | object): Promise<T> {
  let removeNullData = removeNull(unref(data))
  if (method === 'get' || method === 'GET') {
    return instance.request({
      url: path,
      method: method,
      params: removeNullData
    })
  }
  return instance.request({
    url: path,
    method: method,
    data: removeNullData
  })
}

export default request
