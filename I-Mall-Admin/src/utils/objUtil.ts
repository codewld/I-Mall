/**
 * 键值对对象接口
 */
interface KVObj {
  [key: string]: any
}

/**
 * 去除对象中的null值
 */
export function removeNull(obj: KVObj | undefined) {
  if (obj instanceof Array) {
    return obj
  }
  let res: KVObj = {}
  for (let key in obj) {
    if (obj[key] !== undefined && obj[key] !== null && obj[key] !== '') {
      res[key] = obj[key]
    }
  }
  return res
}

/**
 * 获取对象与原对象相比的改动
 */
export function getChange(newObj: KVObj, oldObj: KVObj) {
  let data: KVObj = {}
  for (let key in newObj) {
    if (newObj[key] !== undefined && newObj[key] !== null && newObj[key] !== '' && newObj[key] !== oldObj[key]) {
      data[key] = newObj[key]
    }
  }
  return data
}
