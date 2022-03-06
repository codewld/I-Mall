import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import { useJWTStore, useRouterStore } from '@/store';
import { rGetRouter } from '@/api/account';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import useAccount from '@/composables/useAccount'

const managementLayout = () => import('@/layouts/management/index.vue')
const login = () => import('@/views/login/index.vue')
const home = () => import('@/views/home/index.vue')
const fourZeroFour = () => import('@/views/error/404.vue')

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
      path: '/redirect',
      component: getLayoutByStr('management'),
      children: [
        {
          path: '/redirect/:path(.*)',
          component: () => import('@/views/redirect/index.vue')
        }
      ]
    },
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
    },
    {
      path: '/error',
      component: getLayoutByStr('management'),
      children: [
        {
          path: '404',
          name: '404',
          component: fourZeroFour
        }
      ]
    },
    {
      path: '/:pathMatch(.*)',
      redirect: '/error/404'
    }
  ]
})

/**
 * 刷新页面后该变量会归零，用于判断是否添加了路由
 */
let loadFlag = 0

/**
 * 路由导航守卫
 */
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
      loadFlag++
      addRouter(routerStore.value)
      next({ ...to, replace: true })
    } catch (e) {
      ElMessage.warning('路由获取失败')
    }
  } else {
    if (loadFlag === 0) {
      loadFlag++
      addRouter(routerStore.value)
      next({ ...to, replace: true })
    } else {
      next()
    }
  }
})

/**
 * 添加路由
 * @param myRouters
 */
const addRouter = (myRouters: Account.router[] | undefined) => {
  if (myRouters && myRouters.length !== 0) {
    myRouters.forEach(i => {
      router.addRoute(transformRouter(i))
    })
  }
}

/**
 * 将自定义Router转换为Vue Router所需的格式
 */
const transformRouter = (myRouter: Account.router): RouteRecordRaw => {
  return {
    path: myRouter.path,
    name: myRouter.name,
    component: myRouter.children ? getLayoutByStr(myRouter.component) : () => import(`../views${ myRouter.component }`),
    children: myRouter.children?.map(o => transformRouter(o))
  }
}

/**
 * 重新加载路由
 */
const reloadRouter = () => {
  // 原始地址
  const originPath = router.currentRoute.value.path;
  // 清除路由
  const routerStore = useRouterStore()
  routerStore.value?.forEach(i => {
    router.removeRoute(i.name)
  })
  // 重置全局存储
  routerStore.$reset()
  // 利用redirect刷新页面，重新获取路由
  router.replace({
    path: '/redirect' + originPath
  }).then(() => {
    ElMessage.info('已自动为您重新加载路由')
  })
}

export default router

export {
  reloadRouter
}
