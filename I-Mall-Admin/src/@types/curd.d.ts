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
  type pageFunction<T> = (pageParam: Ref<pageParam>, searchParam: Ref<object> | null) => Promise<dataList<T>>

  /**
   * 分页参数
   */
  interface pageParam {
    pageNum: number,
    pageSize: number
  }

  /**
   * 数据列表
   */
  interface dataList<T> {
    total: number,
    list: T[]
  }

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
