import request from '@/api/request';
import { Ref } from 'vue';

const PREFIX = '/account'

/**
 * 登录
 */
export function rLogin(data: Ref<Account.loginParam>): Promise<string> {
  return request(PREFIX + '/login', 'post', data)
}

/**
 * 获取路由
 */
export const rGetRouter = (): Promise<Account.router[]> => {
  return request(PREFIX + '/router', 'get', undefined, true, '加载界面中')
}
