<template>
  <div :class="[b(),className]"
       :style="styleSizeName"
       @click="handleClick">
    <img :style="[styleChartName,styleImgName]"
         :src="dataChart.value"
         :class="b({'rotate': rotate})"
         draggable="false" />
  </div>
</template>

<script>
import create from "../../create";
export default create({
  name: "img",
  computed: {
    styleImgName () {
      return Object.assign(
        (() => {
          if (this.rotate) {
            return {
              animationDuration: this.duration / 1000 + "s"
            };
          }
          return {};
        })(),
        {
          width: '100%',
          height: '100%',
          borderRadius: this.setPx(this.option.borderRadius),
          opacity: this.option.opacity || 1
        }
      );
    },
    duration () {
      return this.option.duration || 3000;
    },
    rotate () {
      return this.option.rotate;
    }
  },
  methods: {
    handleClick () {
      this.clickFormatter && this.clickFormatter({
        data: this.dataChart
      }, this.getItemRefs());
    }
  }
});
</script>

