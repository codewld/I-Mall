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
