/**
 * 判断两个数组是否相等
 * @param array1
 * @param array2
 */
export function isSame(array1: any[], array2: any[]): boolean {
  return array1?.sort()?.toString() === array2?.sort()?.toString();
}
