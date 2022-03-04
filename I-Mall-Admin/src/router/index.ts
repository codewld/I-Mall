import { createRouter, createWebHashHistory } from 'vue-router'
import { useJWTStore, useRouterStore } from '@/store';
import { rGetRouter } from '@/api/account';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import useAccount from '@/composables/useAccount'

const managementLayout = () => import('@/layouts/management/index.vue')
const login = () => import('@/views/login/index.vue')
const home = () => import('@/views/home/index.vue')
const admin = () => import('@/views/ums/admin/index.vue')
const role = () => import('@/views/ums/role/index.vue')
const menu = () => import('@/views/ums/menu/index.vue')

const { reset } = useAccount()

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
      reset().then(() => {
        ElMessage.warning('请登录')
      })
      return
    } else {
      return next()
    }
  }
  // 已登录
  // 不允许已登录的情况下进入登录页
  if (to.name === 'login') {
    ElMessage.warning('您已登录')
    return next({ name: 'home' })
  }
  // 如果登录，应该获取路由
  const routerStore = useRouterStore();
  const router = routerStore.value
  if (!router) {
    rGetRouter().then(res => {
      let router = [...res]
      router.unshift({
        name: '首页',
        component: 'home',
        path: '/home'
      })
      routerStore.set(router)
    }).catch(() => {
      ElMessage.warning('路由获取失败')
    }).finally(() => {
      return next()
    })
  } else {
    return next()
  }
})

export default router
