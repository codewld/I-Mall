declare namespace Chat {

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
    receiver: user,
    msg: string,
    time: string
  }

}
