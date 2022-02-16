import request from '@/api/request';
import { Ref } from 'vue';

/**
 * 登录
 */
export function rLogin(data: Ref<Account.loginParam> | Account.loginParam): Promise<string> {
  return request('/account/login', 'post', data)
}
