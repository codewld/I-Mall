import { useWebSocket } from '@/composables/chat/useWebSocket';
import { useChatStore } from '@/store/modules/chatState';
import { computed, ref, Ref } from 'vue';
import { parsePlus, stringifyPlus } from '@/utils/objUtil';

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
   * 设置"活跃状态"
   */
  const setActiveStatus = (active: boolean) => {
    let data = {
      active: active
    }
    send('activeStatus', data)
  }

  /**
   * 当前联系人
   */
  const currentContact = ref()

  /**
   * 建立会话
   */
  const establishSession = (contact: Chat.user) => {
    currentContact.value = contact
    let data = {
      contact: contact
    }
    send('sessionEstablish', data)
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
    return useChatStore().getMsgArchiveMap
  })

  /**
   * 当前会话的聊天信息
   */
  const sessionMsg = computed(() => {
    return allMsg.value.get(stringifyPlus(currentContact.value))
  })

  /**
   * 未读消息数量
   */
  const unreadCount = computed(() => useChatStore().unreadCount)

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
    setActiveStatus,
    currentContact,
    establishSession,
    sendCommunicateMsg,
    allMsg,
    sessionMsg,
    unreadCount,
    contactList
  }
}
