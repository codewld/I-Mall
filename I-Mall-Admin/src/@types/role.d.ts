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
   * Role标记
   */
  interface roleMark {
    id: number,
    name: string
  }

  /**
   * Role参数
   */
  interface roleParam {
    name?: string
  }

}
