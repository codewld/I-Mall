import { Ref, ref } from 'vue';
import { ElMessage } from 'element-plus';
import 'element-plus/es/components/message/style/css';
import { CURD } from '@/@types/curd/curd';
import { getChange } from '@/utils/objUtil';

/**
 * 表单CURD
 * @param doLoad 加载方法
 * @param fieldList 字段列表
 * @param originData 原始数据
 * @param addFunction 新增方法
 * @param delFunction 删除方法
 * @param updateFunction 修改方法
 */
export default function useFormCurd(
  doLoad: () => void,
  fieldList: CURD.field[],
  originData: Ref,
  addFunction: CURD.addFunction<unknown> | undefined,
  delFunction: CURD.delFunction | undefined,
  updateFunction: CURD.updateFunction<unknown> | undefined) {

  /**
   * dialog是否可见
   */
  const dialogVisible = ref(false)

  /**
   * 表单数据
   */
  let formData = ref()

  /**
   * 当前正在进行的操作 [增、改、查]
   */
  const actionType: Ref<string | undefined> = ref(undefined)

  /**
   * 准备添加
   */
  const beforeAdd = () => {
    formData.value = {}
    // 设置默认值
    fieldList.forEach(i => {
      if (i?.formConf?.addDefault !== undefined) {
        formData.value[i.code] = i.formConf.addDefault
      }
    })
    actionType.value = 'add'
    dialogVisible.value = true
  }

  /**
   * 执行添加
   */
  const doAdd = () => {
    addFunction && addFunction(formData.value).then(() => {
      ElMessage.success('操作成功')
      dialogVisible.value = false
      doLoad()
    }).catch(err => {
      ElMessage.warning(err)
    })
  }

  /**
   * 执行删除
   */
  const doDel = () => {
    delFunction && delFunction(originData.value.id).then(() => {
      ElMessage.success('操作成功')
      doLoad()
    }).catch(err => {
      ElMessage.warning(err)
    })
  }

  /**
   * 准备修改
   */
  const beforeUpdate = () => {
    formData.value = { ...originData.value }
    actionType.value = 'update'
    dialogVisible.value = true
  }

  /**
   * 执行修改
   */
  const doUpdate = () => {
    const data = getChange(formData.value, originData.value)
    if (Object.getOwnPropertyNames(data).length === 0) {
      ElMessage.warning('请修改')
      return
    }
    updateFunction && updateFunction(originData.value.id, data).then(() => {
      ElMessage.success('操作成功')
      dialogVisible.value = false
      doLoad()
    }).catch(err => {
      ElMessage.warning(err)
    })
  }

  /**
   * 准备查看
   */
  const beforeSee = () => {
    formData.value = originData.value
    actionType.value = 'see'
    dialogVisible.value = true
  }

  return {
    dialogVisible,
    formData,
    actionType,
    beforeAdd,
    doAdd,
    doDel,
    beforeUpdate,
    doUpdate,
    beforeSee
  }
}
