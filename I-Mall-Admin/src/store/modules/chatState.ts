import { defineStore } from 'pinia';
import { parsePlus, stringifyPlus } from '@/utils/objUtil';
import { getContact, getUser } from '@/utils/chatUtil';

interface chatState {
  /**
   * 消息存档
   * <br>
   * 联系人 - 消息Set
   */
  msgArchiveMap: Map<string, Set<Chat.msg>>,
  /**
   * 未读消息数
   */
  unreadCount: number
}

export const useChatStore = defineStore({
  id: 'chat',
  state: (): chatState => {
    return {
      msgArchiveMap: new Map<string, Set<Chat.msg>>(),
      unreadCount: 0
    }
  },
  actions: {
    /**
     * 添加消息
     */
    addMsg(msgList: Chat.msg[]) {
      msgList.forEach(msg => {
        const contactStringify = stringifyPlus(getContact(msg))
        const msgSet = this.msgArchiveMap.get(contactStringify) ?? new Set<Chat.msg>()
        msgSet.add(msg)
        this.msgArchiveMap.set(contactStringify, msgSet)
      })
    },
    /**
     * 设置未读消息数
     */
    setUnreadCount(num: number) {
      this.unreadCount = num
    }
  },
  persist: {
    key: '__msg__' + stringifyPlus(getUser()) + '__msg__',
    storage: localStorage,
    serializer: {
      serialize: val => stringifyPlus(val),
      deserialize: val => parsePlus(val)
    }
  }
})
