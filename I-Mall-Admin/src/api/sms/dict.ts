import useCurdApi from '@/composables/curd/useCurdApi';
import request from '@/api/request';
import { CURD } from '@/@types/curd';

const PREFIX = '/sms/dict'

export const { rAdd, rDel, rUpdate, rPage } = useCurdApi<Dict.dict, Dict.dictParam>(PREFIX)

const PREFIX2 = '/sms/dict/detail'

export const { rDel: rDelDetail, rUpdate: rUpdateDetail } = useCurdApi<Dict.dictDetail, Dict.dictDetailParam>(PREFIX2)

/**
 * 添加字典细节
 * @param id 字典ID
 * @param data 字典细节参数
 */
export const rAddDetail = (id: number, data: Dict.dictDetailParam): Promise<string> => {
  return request(PREFIX2, 'post', { dictId: id, ...data })
}

/**
 * 批量查询字典细节
 * @param id 字典ID
 */
export const rListDetail = (id: number): Promise<CURD.listData<Dict.dictDetail>> => {
  return request(`${ PREFIX2 }/list`, 'get', { id: id })
}
