module.exports = {
  publicPath: process.env.VUE_APP_PATH,
  lintOnSave: false,
  css: {
    loaderOptions: {
      sass: {
        implementation: require('sass'), // This line must in sass option
      }
    }
  },
  transpileDependencies: [
    'monaco-editor',
    '@jiaminghi/data-view'
  ],
  chainWebpack: (config) => {
    //忽略的打包文件
    config.externals({
      'vue': 'Vue',
      'vue-router': 'VueRouter',
      'vuex': 'Vuex',
      'axios': 'axios',
      'element-ui': 'ELEMENT',
    })
  }
}
