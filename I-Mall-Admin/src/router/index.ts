import { createRouter, createWebHashHistory } from 'vue-router'
import { useJWTStore } from '@/store';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';

const managementLayout = () => import('@/layouts/management/index.vue')
const login = () => import('@/views/login/index.vue')
const home = () => import('@/views/home/index.vue')
const admin = () => import('@/views/ums/admin/index.vue')
const role = () => import('@/views/ums/role/index.vue')
const menu = () => import('@/views/ums/menu/index.vue')

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      redirect: '/home'
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/home',
      component: managementLayout,
      children: [
        {
          path: '',
          name: 'home',
          component: home
        }
      ]
    },
    {
      path: '/ums',
      component: managementLayout,
      redirect: '/ums/admin',
      children: [
        {
          path: 'admin',
          name: 'admin',
          component: admin
        },
        {
          path: 'role',
          name: 'role',
          component: role
        },
        {
          path: 'menu',
          name: 'menu',
          component: menu
        }
      ]
    }
  ]
})

// 路由导航守卫
router.beforeEach((to, from, next) => {
  const JWTStore = useJWTStore()
  const JWT = JWTStore.value
  // 未登录
  if (!JWT) {
    if (to.name !== 'login') {
      ElMessage.warning('请登录')
      return next('login')
    } else {
      return next()
    }
  }
  // 已登录
  // 不允许已登录的情况下进入登录页
  if (to.name === 'login') {
    ElMessage.warning('您已登录')
    return next()
  }
  next()
})

export default router
