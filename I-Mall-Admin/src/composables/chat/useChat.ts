import { useWebSocket } from '@/composables/chat/useWebSocket';
import { useChatState } from '@/store/modules/chatState';
import { computed } from 'vue';
import { getUser } from '@/utils/chatUtil';
import { parsePlus, stringifyPlus } from '@/utils/objUtil';

/**
 * chat
 */
export function useChat() {

  /**
   * 接收消息
   */
  const receiveMsg = (webSocketMsg: Websocket.webSocketMsg): void => {
    const chatState = useChatState()
    const data = JSON.parse(webSocketMsg.data.toString())
    switch (webSocketMsg.type) {
      // 未读消息数
      case 'unreadCount':
        chatState.setUnreadCount(data.count)
        break
      // 消息
      case 'msg':
        chatState.addMsg(data.list)
        break
      default:
        break
    }
  }

  const { send } = useWebSocket(receiveMsg)


  /**
   * 发送"活跃状态"信息
   */
  const sendActiveStatusMsg = (active: boolean) => {
    let data = {
      active: active
    }
    send('activeStatus', data)
  }

  /**
   * 发送"会话建立"信息
   */
  const sendSessionEstablishMsg = (contact: Chat.user) => {
    send('sessionEstablish', contact)
  }

  /**
   * 发送"通信消息"消息
   */
  const sendCommunicateMsg = (msg: string) => {
    let data = {
      msg: msg
    }
    send('communicateMsg', data)
  }


  /**
  * 所有聊天消息
  */
  const allMsg = computed(() => {
    return useChatState().getMsgArchive
  })

  /**
   * 联系人列表
   */
  const contactList = computed(() => {
    const set = new Set(allMsg.value.map(o => {
      const userStr = stringifyPlus(getUser())
      const senderStr = stringifyPlus(o.sender)
      const recipientStr = stringifyPlus(o.recipient)
      if (senderStr !== userStr) {
        return senderStr
      } else if (recipientStr != userStr) {
        return recipientStr
      }
    }))
    return [...set].map(o => o && parsePlus(o))
  })


  return {
    sendActiveStatusMsg,
    sendSessionEstablishMsg,
    sendCommunicateMsg,
    allMsg,
    contactList
  }
}
