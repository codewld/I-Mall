import axios, { Method } from 'axios';
import { ElLoading } from 'element-plus'
import 'element-plus/es/components/loading/style/css'
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import { baseURL } from '@/config';
import { unref } from 'vue';
import { useJWTStore } from '@/store';
import { removeNull } from '@/utils/objUtil'
import useAccount from '@/composables/system/useAccount';
import { reloadRouter } from '@/router';

const { reset } = useAccount()


/**
 * axios实例
 */
const instance = axios.create({
  baseURL: `http://${baseURL}`,
  timeout: 5000
})


/**
 * 请求拦截器
 */
instance.interceptors.request.use(
  (config: any) => {
    config.headers.Authorization = useJWTStore().value
    return config
  }, () => {
    ElMessage.error('请检查网络')
    return Promise.reject('网络错误')
  }
)


/**
 * 响应拦截器
 */
instance.interceptors.response.use(
  res => {
    // 成功
    if (res.data.code === 0) {
      return res.data.data
    }
    // 身份验证错误
    if (res.data.code >= 9100 && res.data.code < 9200) {
      reset().then(() => {
        ElMessage.error('身份验证错误，请重新登录')
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
 * 加载动画实例
 */
let loadingInstance: any


/**
 * 开始加载动画
 * @param text 全局加载动画的提示文字
 */
const startLoading = (text: string) => {
  let loadingConfig = {
    text: text
  }
  loadingInstance = ElLoading.service(loadingConfig)
}


/**
 * 停止加载动画
 */
const endLoading = () => {
  loadingInstance.close()
}


/**
 * 发送网络请求
 * @param path 请求地址后缀
 * @param method HTTP 方法
 * @param data 传递的数据
 * @param isLoading 是否展示全局加载动画
 * @param loadingText 全局加载动画的提示文字
 * @return Promise
 */
function request<P, R>(path: string, method: Method = 'get', data ?: P,
                    isLoading: boolean = false, loadingText: string = '加载中'): Promise<R> {
  let removeNullData = removeNull(unref(data))
  let requestConfig = {
    url: path,
    method: method,
    params: method === 'get' || method === 'GET' ? removeNullData : undefined,
    data: method === 'get' || method === 'GET' ? undefined : removeNullData
  }
  return new Promise((resolve, reject) => {
    isLoading && startLoading(loadingText)
    instance.request<any, R>(requestConfig)
      .then(res => {
        resolve(res)
      })
      .catch(err => {
        reject(err)
      })
      .finally(() => {
        isLoading && endLoading()
      })
  })
}

export default request
