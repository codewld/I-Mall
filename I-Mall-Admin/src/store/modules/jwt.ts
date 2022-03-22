import { defineStore } from 'pinia';

interface JWTState {
  value: string | undefined
}

export const useJWTStore = defineStore({
  id: 'JWT',
  state: (): JWTState => {
    return {
      value: undefined
    }
  },
  actions: {
    /**
     * 设值
     */
    set(value: string) {
      this.value = value
    }
  },
  getters: {
    /**
     * 获取当前用户的ID
     */
    getId() {
      if (this.value === undefined) {
        return null
      }
      let decodeJWT: Account.decodeJWT = JSON.parse(window.atob(this.value.split('.')[1]))
      return decodeJWT.aud[0]
    }
  },
  persist: {
    enabled: true
  }
})
