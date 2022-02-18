import request from '@/api/request';
import { Ref } from 'vue';

const PREFIX = '/account'

/**
 * 登录
 */
export function rLogin(data: Ref<Account.loginParam>): Promise<string> {
  return request(PREFIX + '/login', 'post', data)
}
