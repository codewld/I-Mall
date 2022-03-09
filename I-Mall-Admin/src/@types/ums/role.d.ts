declare namespace Role {
  /**
   * Role实体
   */
  interface role {
    id: number,
    code: string,
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
    code: string,
    name: string
  }

  /**
   * Role参数
   */
  interface roleParam {
    code?: string,
    name?: string,
    note?: string
  }

}
