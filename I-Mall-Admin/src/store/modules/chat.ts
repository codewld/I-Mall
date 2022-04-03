import { defineStore } from 'pinia';
import { getUserStr } from '@/utils/chatUtil';
import { parsePlus, stringifyPlus } from '@/utils/serializeUtil';

interface chatState {
  /**
   * 消息存档
   * <br>
   * 对象中每一个属性对应一个用户的聊天记录
   */
  msgArchiveMap: Map<string, Set<Chat.msg>>,
  /**
   * 未读消息数
   */
  unreadCount: number
}

export const useChatState = defineStore({
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
      let msgSet = this.msgArchiveMap.get(getUserStr()) ?? new Set()
      msgList.forEach(o => msgSet.add(o))
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
      return [...this.msgArchiveMap.get(getUserStr()) ?? []]
    }
  },
  persist: {
    serializer: {
      serialize: val => stringifyPlus(val),
      deserialize: val => parsePlus(val),
    }
  }
})
