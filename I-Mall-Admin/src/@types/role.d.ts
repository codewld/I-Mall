declare namespace Role {
  /**
   * Role实体
   */
  interface role {
    id: number,
    name: string,
    status: boolean,
    note: string,
    createTime: string,
    updateTime: string
  }

  /**
   * Role参数
   */
  interface roleParam {
    name?: string,
    status?: boolean
  }

}
