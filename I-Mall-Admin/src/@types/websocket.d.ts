declare namespace Websocket {

  /**
   * 用户
   */
  interface User {
    system: string
    id: number,
  }

  /**
   * websocket 消息类
   */
  interface webSocketMsg {
    type: string,
    data: unknown
  }

}
