import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router'

const app = createApp(App);

// 样式
import '@/assets/css/base.css'
import '@/assets/css/tailwind.css'

app.use(router)

app.mount('#app')
