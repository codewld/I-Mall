import { defineStore } from 'pinia';

interface RouterState {
  value: Account.router[]
}

export const useRouterStore = defineStore({
  id: 'router',
  state: (): RouterState => {
    return {
      value: []
    }
  },
  actions: {
    /**
     * 设值
     */
    set(list: Account.router[]) {
      this.value = list
    },
    /**
     * 清空
     */
    reset() {
      this.value = []
    }
  },
  persist: {
    enabled: true
  }
})
