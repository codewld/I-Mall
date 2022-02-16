import request from '@/api/request';

/**
 * hello
 */
export const rHello = () => {
  return request<string>("/hello", 'get')
}
