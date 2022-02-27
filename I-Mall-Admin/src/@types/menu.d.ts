declare namespace Menu {
  /**
   * Menu实体
   */
  interface menu {
    id: number,
    parentId: number,
    code: string,
    name: string,
    sort: number,
    nonLeaf: boolean,
    component: string,
    path: string,
    note: string,
    createTime: string,
    updateTime: string
  }

  /**
   * Menu标记
   */
  interface menuMark {
    id: number,
    name: string,
    children: menuMark[]
  }

  /**
   * Menu参数
   */
  interface menuParam {
    parentId?: number,
    code?: string,
    name?: string,
    sort?: number,
    nonLeaf?: boolean,
    component?: string,
    path?: string,
    note?: string
  }

}
