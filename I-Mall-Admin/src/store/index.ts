import { createPinia } from 'pinia';
import piniaPluginPersist from 'pinia-plugin-persist'
import { useJWTStore } from './modules/jwt';
import { useRouterStore } from './modules/router';

const store = createPinia()

// 持久化插件
store.use(piniaPluginPersist)

export default store

export {
  useJWTStore,
  useRouterStore
}
