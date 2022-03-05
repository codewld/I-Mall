import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import { useJWTStore, useRouterStore } from '@/store';
import { rGetRouter } from '@/api/account';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import useAccount from '@/composables/useAccount'

const managementLayout = () => import('@/layouts/management/index.vue')
const login = () => import('@/views/login/index.vue')
const home = () => import('@/views/home/index.vue')

const { reset } = useAccount()

/**
 * 根据布局组件路径信息获取布局组件
 *
 * 不使用直接"() => import()"，以避免相同的布局组件被视为不同，产生不必要的页面更新
 */
const getLayoutByStr = (str: string): () => Promise<{}> => {
  if (str === 'management') {
    return managementLayout
  }
  return managementLayout
}

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
      component: getLayoutByStr('management'),
      children: [
        {
          path: '',
          name: 'home',
          component: home
        }
      ]
    }
  ]
})

/**
 * 刷新页面后该变量会归零，用于判断是否添加了路由
 */
let loadFlag = 0

// 路由导航守卫
router.beforeEach(async (to, from, next) => {
  const JWTStore = useJWTStore()
  const JWT = JWTStore.value
  // 未登录
  if (!JWT) {
    if (to.name !== 'login') {
      await reset()
      ElMessage.warning('请登录')
      return next()
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
  const routerStore = useRouterStore()
  if (!routerStore.value) {
    try {
      let res = await rGetRouter()
      routerStore.set(res)
    } catch (e) {
      ElMessage.warning('路由获取失败')
    }
    return next()
  }
  if (loadFlag === 0) {
    loadFlag++
    if (routerStore.value.length !== 0) {
      routerStore.value.forEach(i => {
        router.addRoute(transformRouter(i))
      })
    }
    next({ ...to, replace: true })
  } else {
    next()
  }
})

/**
 * 将自定义Router转换为Vue Router所需的格式
 */
const transformRouter = (myRouter: Account.router): RouteRecordRaw => {
  return {
    path: myRouter.path,
    name: myRouter.name,
    component: myRouter.children ? getLayoutByStr(myRouter.component) : () => import(`../views${myRouter.component}`),
    children: myRouter.children?.map(o => transformRouter(o))
  }
}

export default router
