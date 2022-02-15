import { defineStore } from 'pinia';

interface jwt {
  value: string | undefined
}

export const useJWTStore = defineStore({
  id: 'JWT',
  state: (): jwt => {
    return {
      value: undefined
    }
  },
  actions: {
    set(value: string) {
      this.value = value
    }
  },
  persist: {
    enabled: true
  }
})
