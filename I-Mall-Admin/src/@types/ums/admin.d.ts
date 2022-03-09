declare namespace Admin {
  /**
   * Admin实体
   */
  interface admin {
    id: number,
    username: string,
    status: boolean,
    nickName: string,
    email: string,
    note: string,
    createTime: string,
    updateTime: string,
    loginTime: string
  }

  /**
   * Admin参数
   */
  interface adminParam {
    username?: string,
    password?: string,
    status?: boolean,
    nickName?: string,
    email?: string,
    note?: string,
  }

}
