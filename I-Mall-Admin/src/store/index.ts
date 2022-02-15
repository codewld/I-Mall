import { createPinia } from 'pinia';
import {useJWTStore} from './modules/jwt';

const store = createPinia()

export default store

export {
  useJWTStore
}
