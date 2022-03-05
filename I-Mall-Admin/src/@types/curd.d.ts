import { Ref } from 'vue';

declare namespace CURD {

  /**
   * 添加
   */
  type addFunction<T> = (data: T) => Promise<string>

  /**
   * 删除
   */
  type delFunction = (id: number) => Promise<string>

  /**
   * 修改
   */
  type updateFunction<T> = (id: number, data: T) => Promise<string>

  /**
   * 分页查询
   */
  type pageFunction<T> = (pageParam: Ref<pageParam>, searchParam: Ref<object> | undefined) => Promise<pageData<T>>

  /**
   * 分页参数
   */
  interface pageParam {
    pageNum: number,
    pageSize: number
  }

  /**
   * 分页查询数据列表
   */
  interface pageData<T> {
    total: number,
    list: T[]
  }

  /**
   * 批量查询
   */
  type listFunction<T> = (searchParam: Ref<object> | undefined) => Promise<listData<T>>

  /**
   * 批量查询数据列表
   */
  type listData<T> = T[]

  /**
   * 搜索配置信息
   */
  interface searchConf {
    display: boolean
  }

  /**
   * 表格配置信息
   */
  interface tableConf {
    display?: boolean,
    width?: number,
    minWidth?: number,
    fixed?: string,
    align?: string
  }

  /**
   * 表单配置信息
   */
  interface formConf {
    add?: boolean,
    addDefault?: any,
    update?: boolean,
    see?: boolean
  }

  /**
   * 字段信息
   */
  interface field {
    code: string,
    name: string,
    tableConf?: tableConf,
    formConf?: formConf,
    searchConf?: searchConf
  }

}
