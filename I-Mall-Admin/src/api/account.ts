import request from '@/api/request';

/**
 * 登录
 */
export const rLogin = (data : object) => {
  return request<string>("/account/login", 'post', data)
}
