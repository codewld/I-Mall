import { defineComponent } from 'vue';
import { ElScrollbar, ElSpace } from 'element-plus';
import 'element-plus/es/components/scrollbar/style/css';
import 'element-plus/es/components/space/style/css';
import './index.css'

/**
 * 容器组件
 *
 * 集成了el-scrollbar和el-space，适用于布局多个垂直块元素
 */
export default defineComponent({
  components: {
    ElScrollbar,
    ElSpace
  },
  render() {
    return (
      <div class="scrollbar-container">
        <el-scrollbar>
          <el-space direction="vertical" fill>
            { (this.$slots.default!() as any[]).filter(o => typeof o.type !== 'symbol') }
          </el-space>
        </el-scrollbar>
      </div>
    )
  }
})
