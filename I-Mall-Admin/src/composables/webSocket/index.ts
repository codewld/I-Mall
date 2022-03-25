import { onMounted, onUnmounted } from 'vue';
import { baseURL } from '@/config';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import { useJWTStore } from '@/store';

/**
 * webSocket
 */
export function useWebSocket() {

  const jwtStore = useJWTStore()

  /**
   * webSocket连接
   */
  let socket: WebSocket

  /**
   * mounted时进行连接
   */
  onMounted(() => {
    socket = new WebSocket(`ws://${ baseURL }/websocket/${ jwtStore.getId }?authorization=${ jwtStore.value }`)

    socket.onerror = err => {
      ElMessage.warning('即时通信服务连接失败')
    }

    socket.onopen = event => {
      console.log(event)
    }

    socket.onmessage = event => {
      console.log(event)
    }

    socket.onclose = () => {
      console.log('连接关闭')
    }
  })

  /**
   * unMounted时关闭连接
   */
  onUnmounted(() => {
    socket.close()
  })

  /**
   * 发送
   */
  const send = (type: string, data: object) => {
    let webSocketMsg: Websocket.webSocketMsg = {
      type: type,
      data: data
    }
    socket.send(JSON.stringify(webSocketMsg))
  }

  /**
   * 关闭连接
   */
  const close = () => {
    socket.close()
  }

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
  const sendSessionEstablishMsg = (id: number) => {
    let data = {
      contact: {
        system: 'WEB',
        id: id
      }
    }
    send('sessionEstablish', data)
  }

  /**
   * 发送"发送消息"消息
   */
  const sendSessionMsg = (msg: string) => {
    let data = {
      msg: msg
    }
    send('sendMsg', data)
  }

  return {
    send,
    close,
    sendActiveStatusMsg,
    sendSessionEstablishMsg,
    sendSessionMsg
  }
}
