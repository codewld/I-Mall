declare namespace Admin {
  /**
   * Admin实体
   */
  interface admin {
    id: number,
    username: string,
    icon: string,
    email: string,
    nickName: string,
    note: string,
    createTime: string,
    updateTime: string,
    loginTime: string,
    status: boolean
  }

  /**
   * Admin参数
   */
  interface adminParam {
    username?: string,
    password?: string,
    icon?: string,
    email?: string,
    nickName?: string,
    note: string,
    status?: boolean
  }

}
