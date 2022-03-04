import { defineStore } from 'pinia';

interface RouterState {
  value: Account.router[] | undefined
}

export const useRouterStore = defineStore({
  id: 'router',
  state: (): RouterState => {
    return {
      value: undefined
    }
  },
  actions: {
    /**
     * 设值
     */
    set(list: Account.router[]) {
      this.value = list
    }
  },
  persist: {
    enabled: true
  }
})
