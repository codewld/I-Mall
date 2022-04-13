import useCurdApi from '@/components/iCurd/composables/useCurdApi';

const PREFIX = '/sms/log'

export const { rPage } = useCurdApi<Log.log, Log.log>(PREFIX)
