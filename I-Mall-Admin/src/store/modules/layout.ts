import { defineStore } from 'pinia';

interface layoutState {
  isSideBarFold: boolean
}

export const useLayoutStore = defineStore({
  id: 'layout',
  state: (): layoutState => {
    return {
      isSideBarFold: false
    }
  },
  actions: {
    /**
     * 设置侧边栏是否折叠
     */
    setIsSideBarFold(value?: boolean) {
      if (value === undefined) {
        this.isSideBarFold = !this.isSideBarFold
      } else {
        this.isSideBarFold = value
      }
    }
  },
  persist: {
    enabled: true
  }
})
