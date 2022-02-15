import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router'
import store from '@/store'

const app = createApp(App);

// 样式
import '@/assets/css/base.css'
import '@/assets/css/tailwind.css'

app.use(router)

app.use(store)

app.mount('#app')
