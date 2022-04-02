declare namespace Websocket {

  /**
   * 用户
   */
  interface user {
    system: string
    id: string,
  }

  /**
   * 消息
   */
  interface msg {
    sender: user,
    recipient: user,
    msg: string,
    time: string
  }

  /**
   * websocket 消息类
   */
  interface webSocketMsg {
    type: string,
    data: object
  }

}
