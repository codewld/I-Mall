import { onMounted, onUnmounted } from 'vue';
import { baseURL } from '@/config';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import { useJWTStore } from '@/store';

/**
 * webSocket
 * @param onmessage 接收消息的回调方法
 */
export function useWebSocket(onmessage?: (webSocketMsg: Websocket.webSocketMsg) => void) {

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
      let webSocketMsg = JSON.parse(event.data)
      onmessage && onmessage(webSocketMsg)
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

  return {
    send,
    close
  }
}
