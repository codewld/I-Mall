import { defineStore } from 'pinia';
import { getUserStr } from '@/utils/chatUtil';

interface StringMsgArray {
  [index: string]: Set<Chat.msg>
}

interface chatState {
  /**
   * 消息存档
   * <br>
   * 对象中每一个属性对应一个用户的聊天记录
   */
  msgArchiveMap: StringMsgArray,
  /**
   * 未读消息数
   */
  unreadCount: number
}

export const useChatState = defineStore({
  id: 'chat',
  state: (): chatState => {
    return {
      msgArchiveMap: {},
      unreadCount: 0
    }
  },
  actions: {
    /**
     * 添加消息
     */
    addMsg(msgList: Chat.msg[]) {
      if (!this.msgArchiveMap[getUserStr()]) {
        this.msgArchiveMap[getUserStr()] = new Set()
      }
      msgList.forEach(o => this.msgArchiveMap[getUserStr()].add(o))
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
      return [...(this.msgArchiveMap?.[getUserStr()] ?? [])]
    }
  },
  persist: true
})
