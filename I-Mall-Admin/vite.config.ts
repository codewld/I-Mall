import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import vueJsx from '@vitejs/plugin-vue-jsx'
import OptimizationPersist from 'vite-plugin-optimize-persist'
import PkgConfig from 'vite-plugin-package-config'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()]
    }),
    Components({
      dirs: ['element-plus'],
      resolvers: [ElementPlusResolver()]
    }),
    vueJsx({
      // options are passed on to @vue/babel-plugin-jsx
    }),
    PkgConfig(),
    OptimizationPersist()
  ],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
  }

})
