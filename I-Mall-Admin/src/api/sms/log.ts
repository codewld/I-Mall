import useCurdApi from '@/composables/curd/useCurdApi';

const PREFIX = '/sms/log'

export const { rPage } = useCurdApi<Log.log, Log.log>(PREFIX)
