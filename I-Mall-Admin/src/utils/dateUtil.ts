/**
 * 根据日期字符串，获取yyyy-MM-dd格式的日期
 */
export function getFormattedDate(dateTimeString: string): string {
  if (!dateTimeString || dateTimeString.indexOf('T') !== 10) {
    return dateTimeString
  }
  return dateTimeString.slice(0, dateTimeString.indexOf('T'))
}

/**
 * 根据日期字符串，获取yyyy-MM-dd HH:mm:ss格式的日期时间
 */
export function getFormattedDateTime(dateTimeString: string): string {
  if (!dateTimeString || dateTimeString.indexOf('T') !== 10) {
    return dateTimeString
  }
  let date = dateTimeString.slice(0, dateTimeString.indexOf('T'))
  let time = dateTimeString.slice(dateTimeString.indexOf('T') + 1, 19)
  return `${date} ${time}`
}

/**
 * 格式化日期
 */
export function format(date: Date, format: string): string {
  const o: any = {
    'M+': date.getMonth() + 1, // 月份
    'd+': date.getDate(), // 日
    'h+': date.getHours(), // 小时
    'm+': date.getMinutes(), // 分
    's+': date.getSeconds(), // 秒
    'q+': Math.floor((date.getMonth() + 3) / 3), // 季度
    S: date.getMilliseconds() // 毫秒
  }

  if (/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (date.getFullYear() + '').slice(4 - RegExp.$1.length))
  }

  for (const k in o) {
    if (new RegExp('(' + k + ')', 'i').test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : ('00' + o[k]).slice(('' + o[k]).length))
    }
  }

  return format
}
