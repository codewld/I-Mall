declare namespace Role {
  /**
   * Role实体
   */
  interface role {
    id: number,
    name: string,
    note: string,
    createTime: string,
    updateTime: string
  }

  /**
   * Role参数
   */
  interface roleParam {
    name?: string
  }

}
