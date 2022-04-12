import { useWebSocket } from '@/composables/chat/useWebSocket';
import { useChatStore } from '@/store/modules/chatState';
import { computed, Ref } from 'vue';
import { parsePlus } from '@/utils/objUtil';

/**
 * chat
 */
export function useChat() {

  /**
   * 接收消息
   */
  const receiveMsg = (webSocketMsg: Websocket.webSocketMsg): void => {
    const chatState = useChatStore()
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
    return useChatStore().msgArchiveMap
  })

  /**
   * 联系人列表
   */
  const contactList: Ref<Chat.user[]> = computed(() => {
    let contactList: Chat.user[] = []
    allMsg.value.forEach((value, key) => {
      contactList.push(parsePlus(key))
    })
    return contactList
  })


  return {
    sendActiveStatusMsg,
    sendSessionEstablishMsg,
    sendCommunicateMsg,
    allMsg,
    contactList
  }
}
