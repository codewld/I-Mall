import request from '@/api/index';

/**
 * hello
 */
export function rHello(): Promise<string> {
  return request('/hello', 'get')
}
