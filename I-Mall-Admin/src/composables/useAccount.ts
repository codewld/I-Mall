import { useJWTStore, useRouterStore } from '@/store';
import router from '@/router'


/**
 * Account相关
 */
export default function useAccount() {

  /**
   * 重置
   */
  const reset: () => Promise<void> = () => {
    // 重置store
    const JWTStore = useJWTStore()
    const RouterStore = useRouterStore()
    JWTStore.$reset()
    RouterStore.$reset()
    // 跳转至登录页
    return new Promise(resolve => {
      router.replace({ name: 'login' }).then(() => {
        resolve()
      })
    })
  }

  return {
    reset
  }
}
