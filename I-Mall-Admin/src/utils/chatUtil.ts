import { useJWTStore } from '@/store';
import { system } from '@/config';
import { stringifyPlus } from '@/utils/objUtil';

/**
 * 获取当前用户
 */
export function getUser(): Chat.user {
  const jwtStore = useJWTStore()
  const id = jwtStore.getId
  return {
    id: id,
    system: system
  }
}

/**
 * 获取联系人
 */
export function getContact(msg: Chat.msg): Chat.user {
  const userStringify = stringifyPlus(getUser())
  if (userStringify === stringifyPlus(msg.sender)) {
    return msg.receiver
  } else if (userStringify === stringifyPlus(msg.receiver)) {
    return msg.sender
  } else {
    throw new Error('联系人不存在')
  }
}
