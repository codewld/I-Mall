import request from '@/api/request';

/**
 * hello
 */
export function rHello(): Promise<string> {
  return request('/hello', 'get')
}
