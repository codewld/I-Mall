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
    /**
     * 设置JWT值
     */
    set(value: string) {
      this.value = value
    },
    /**
     * 清空JWT值
     */
    reset() {
      this.value = undefined
    }
  },
  persist: {
    enabled: true
  }
})
