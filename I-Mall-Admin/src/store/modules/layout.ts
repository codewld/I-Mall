import { defineStore } from 'pinia';

interface layoutState {
  /**
   * 侧边栏是否折叠
   */
  isSideBarFold: boolean
}

export const useLayoutStore = defineStore({
  id: 'layout',
  state: (): layoutState => {
    return {
      /**
       * 侧边栏是否折叠
       */
      isSideBarFold: false
    }
  },
  actions: {
    /**
     * 设置侧边栏是否折叠
     */
    setIsSideBarFold(value?: boolean) {
      this.isSideBarFold = value ?? !this.isSideBarFold
    }
  },
  persist: {
    enabled: true
  }
})
