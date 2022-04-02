import { defineStore } from 'pinia';
import { getUserStr } from '@/utils/accountUtil';

interface StringArray {
  [index: string]: Websocket.msg[]
}

interface MsgState {
  value: StringArray
}

export const useMsgStore = defineStore({
  id: 'Msg',
  state: (): MsgState => {
    return {
      value: {}
    }
  },
  actions: {
    /**
     * 设值
     */
    set(map: Websocket.msg[]) {
      this.value[getUserStr()] = map
    }
  },
  getters: {
    /**
     * 取值
     */
    get(): Websocket.msg[] {
      return this.value[getUserStr()] ?? []
    }
  },
  persist: {
    enabled: true,
    strategies: [
      {
        storage: localStorage
      }
    ]
  }
})
