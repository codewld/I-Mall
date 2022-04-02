import { useJWTStore } from '@/store';
import { system } from '@/config';

/**
 * 获取当前用户
 */
export function getUser(): Websocket.user {
  const jwtStore = useJWTStore()
  const id = jwtStore.getId
  return {
    id: id,
    system: system
  }
}

/**
 * 获取用户的字符串表示
 * <br>
 * 若传入用户，则转换指定用户；否则转换当前用户
 */
export function getUserStr(user: Websocket.user = getUser()): string {
  return user.system + '_' + user.id
}
