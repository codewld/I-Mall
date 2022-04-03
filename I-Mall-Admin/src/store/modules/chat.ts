import { defineStore } from 'pinia';
import { getUserStr } from '@/utils/chatUtil';
import { parsePlus, stringifyPlus } from '@/utils/serializeUtil';

interface chatState {
  /**
   * 消息存档
   * <br>
   * 对象中每一个属性对应一个用户的聊天记录
   */
  msgArchiveMap: Map<string, Set<string>>,
  /**
   * 未读消息数
   */
  unreadCount: number
}

export const useChatState = defineStore({
  id: 'chat',
  state: (): chatState => {
    return {
      msgArchiveMap: new Map<string, Set<string>>(),
      unreadCount: 0
    }
  },
  actions: {
    /**
     * 添加消息
     */
    addMsg(msgList: Chat.msg[]) {
      let msgSet = this.msgArchiveMap.get(getUserStr()) ?? new Set()
      // 以字符串存储，以避免重复
      msgList.forEach(o => msgSet.add(stringifyPlus(o)))
      this.msgArchiveMap.set(getUserStr(), msgSet)
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
     * 获取消息存档
     */
    getMsgArchive(): Chat.msg[] {
      return [...this.msgArchiveMap.get(getUserStr()) ?? []].map(o => parsePlus(o))
    }
  },
  persist: {
    serializer: {
      serialize: val => stringifyPlus(val),
      deserialize: val => parsePlus(val)
    }
  }
})
