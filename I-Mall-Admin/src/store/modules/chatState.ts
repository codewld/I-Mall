import { defineStore } from 'pinia';
import { parsePlus, stringifyPlus } from '@/utils/objUtil';
import { getContact, getUser } from '@/utils/chatUtil';

interface chatState {
  /**
   * 消息存档Map的Map
   * <br>
   * 消息存档Map的Map<用户, 消息存档Map<联系人, 消息Set<Chat.msg>>>
   */
  msgArchiveMapMap: Map<string, Map<string, Set<string>>>,
  /**
   * 未读消息数
   */
  unreadCount: number
}

export const useChatStore = defineStore({
  id: 'chat',
  state: (): chatState => {
    return {
      msgArchiveMapMap: new Map<string, Map<string, Set<string>>>(),
      unreadCount: 0
    }
  },
  actions: {
    /**
     * 设置消息存档Map
     */
    setMsgArchiveMap(msgArchiveMap: Map<string, Set<string>>) {
      this.msgArchiveMapMap.set(stringifyPlus(getUser()), msgArchiveMap)
    },
    /**
     * 添加消息
     */
    addMsg(msgList: Chat.msg[]) {
      const msgArchiveMap = this.getMsgArchiveMap
      msgList.forEach(msg => {
        const contactStringify = stringifyPlus(getContact(msg))
        const msgSet = msgArchiveMap.get(contactStringify) ?? new Set<string>()
        msgSet.add(stringifyPlus({ msg: msg.msg, time: msg.time }))
        msgArchiveMap.set(contactStringify, msgSet)
      })
      this.setMsgArchiveMap(msgArchiveMap)
    },
    /**
     * 设置未读消息数
     */
    setUnreadCount(num: number) {
      this.unreadCount = num
    }
  },
  getters: {
    /**
     * 获取消息存档Map
     */
    getMsgArchiveMap(): Map<string, Set<string>> {
      return this.msgArchiveMapMap.get(stringifyPlus(getUser())) ?? new Map<string, Set<string>>()
    }
  },
  persist: {
    storage: localStorage,
    serializer: {
      serialize: val => stringifyPlus(val),
      deserialize: val => parsePlus(val)
    }
  }
})
