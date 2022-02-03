import {createRouter, createWebHashHistory} from 'vue-router'

const login = () => import('@/views/login/index.vue')
const home = () => import('@/views/home/index.vue')

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/home',
      name: 'home',
      component: home
    }
  ]
})

export default router
