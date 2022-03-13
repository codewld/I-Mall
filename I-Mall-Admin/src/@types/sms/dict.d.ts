declare namespace Dict {
  /**
   * Dict实体
   */
  interface dict {
    id: number,
    code: string,
    name: string,
    note: string,
    createTime: string,
    updateTime: string
  }

  /**
   * Dict参数
   */
  interface dictParam {
    code?: string,
    name?: string,
    note?: string,
  }

  /**
   * Dict细节实体
   */
  interface dictDetail {
    id: number,
    dictId: number,
    value: string,
    label: string,
    createTime: string,
    updateTime: string
  }

  /**
   * Dict细节参数
   */
  interface dictDetailParam {
    value?: string,
    label?: string,
  }
}
