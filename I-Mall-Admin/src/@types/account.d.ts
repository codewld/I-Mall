declare namespace Account {
  /**
   * 登录参数
   */
  interface loginParam {
    username: string,
    password: string
  }

  /**
   * Router实体
   */
  interface router {
    path: string,
    name: string,
    component: string,
    children?: router[]
  }

}
