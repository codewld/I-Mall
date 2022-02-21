import { Ref } from 'vue';

declare namespace CURD {

  /**
   * 添加方法
   */
  type addFunction<T> = (data: T) => Promise<string>

  /**
   * 删除方法
   */
  type delFunction = (id: number) => Promise<string>

  /**
   * 修改方法
   */
  type updateFunction<T> = (id: number, data: T) => Promise<string>

  /**
   * 分页查询方法
   */
  type listFunction<T> = (data: Ref<pageParam>) => Promise<pageData<T>>

  /**
   * 分页参数
   */
  interface pageParam {
    pageNum: number,
    pageSize: number
  }

  /**
   * 分页数据
   */
  interface pageData<T> {
    total: number,
    list: T[]
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
    readOnly?: boolean,
    add?: boolean,
    update?: boolean,
    see?: boolean,
  }

  /**
   * 字段信息
   */
  interface field {
    code: string,
    name: string,
    tableConf?: tableConf,
    formConf?: formConf
  }

}
