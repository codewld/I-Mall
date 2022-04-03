import { createPinia } from 'pinia';
import { useJWTStore } from './modules/jwt';
import { useRouterStore } from './modules/router';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

const store = createPinia()

// 持久化插件
store.use(piniaPluginPersistedstate)

export default store

export {
  useJWTStore,
  useRouterStore
}
