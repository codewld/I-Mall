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

/**
 * 将对象序列化为字符串
 * <br>
 * JSON.stringify的升级版，扩展了对Map和Set的支持
 */
export function stringifyPlus(obj: object) {
  return JSON.stringify(
    obj,
    (k, v) => {
      if (v instanceof Map) {
        return {
          type: 'Map',
          value: [...v]
        }
      }
      if (v instanceof Set) {
        return {
          type: 'Set',
          value: [...v]
        }
      }
      return v
    }
  )
}

/**
 * 将字符串反序列化为对象
 * <br>
 * JSON.parse的升级版，扩展了对Map和Set的支持
 */
export function parsePlus(str: string) {
  return JSON.parse(
    str,
    (k, v) => {
      if (v?.type === 'Map') {
        return new Map(v.value)
      }
      if (v?.type === 'Set') {
        return new Set(v.value)
      }
      return v
    }
  )
}

/**
 * 判断两个对象是否相同
 * <br>
 * 通过stringifyPlus判断
 */
export function isSame(obj1: object, obj2: object) {
  return stringifyPlus(obj1) === stringifyPlus(obj2)
}
