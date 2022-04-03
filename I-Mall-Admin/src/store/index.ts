import { createPinia } from 'pinia';
import { useJWTStore } from './modules/jwtState';
import { useRouterStore } from './modules/routerState';
import { createPersistedState } from 'pinia-plugin-persistedstate';

const store = createPinia()

// 持久化插件
store.use(createPersistedState({
  storage: sessionStorage
}))

export default store

export {
  useJWTStore,
  useRouterStore
}
