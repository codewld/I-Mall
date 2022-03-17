declare namespace Log {
  /**
   * Log实体
   */
  interface log {
    summary?: string,
    status?: boolean
    uri?: string,
    method?: string,
    username?: string,
    ip?: string,
    parameter?: string,
    time?: string,
    spendTime?: number
  }
}
