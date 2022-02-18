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
  let time = dateTimeString.slice(dateTimeString.indexOf('T') + 1)
  return `${date} ${time}`
}

