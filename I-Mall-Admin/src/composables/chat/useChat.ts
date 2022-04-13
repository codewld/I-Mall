import { useWebSocket } from '@/composables/chat/useWebSocket';
import { useChatStore } from '@/store/modules/chatState';
import { computed, ref, Ref, toRaw } from 'vue';
import { parsePlus, stringifyPlus } from '@/utils/objUtil';
import { getUser } from '@/utils/chatUtil';
import { format } from '@/utils/dateUtil';

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
        chatState.addMsg(...data.list)
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
    let aMsg: Chat.msg = {
      msg: msg,
      receiver: toRaw(currentContact.value),
      sender: getUser(),
      time: format(new Date(), 'yyyy-MM-ddTHH:mm:ss.S')
    }
    useChatStore().addMsg(aMsg)
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
  const sessionMsg = computed((): Chat.msg[] => {
    let stringSet = allMsg.value.get(stringifyPlus(currentContact.value)) ?? new Set<string>()
    return [...stringSet]
      .sort((a, b) => parsePlus(a).time - parsePlus(b).time)
      .map(string => parsePlus(string))
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
