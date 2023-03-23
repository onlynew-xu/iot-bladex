// import { Loading } from 'element-ui';
import { checkUrl } from '@/utils/utils'
import axios from 'axios';
window.$glob = {
  url: '',
  params: {},
  query: {},
  header: {}
};
function getGlobParams () {
  var query = window.location.search.substring(1);
  query = query.split("&");
  query.forEach(ele => {
    var pair = ele.split("=");
    window.$glob.params[pair[0]] = pair[1]
  })
}
getGlobParams();
axios.defaults.timeout = 10000;
//返回其他状态吗
axios.defaults.validateStatus = function (status) {
  return status >= 200 && status <= 500; // 默认的
};
//跨域请求，允许保存cookie
// let loadingInstance = '';
// axios.defaults.withCredentials = true;
axios.interceptors.request.use(config => {
  // loadingInstance = Loading.service({
  //   text: '拼命加载中',
  //   background: 'rgba(0,0,0,0)',
  //   spinner: 'el-icon-loading'
  // });
  if (!checkUrl(config.url)) config.url = window.$glob.url + config.url;
  let header = window.$glob.header || {};
  config.headers = Object.assign(config.headers, header);
  let data = window.$glob.query || {}
  let key;
  if (config.method == 'get') {
    key = "params"
  } else if (config.method == 'post') {
    key = "data"
  }
  if (typeof (config[key]) === 'object') {
    config[key] = Object.assign(config[key] || {}, data)
  }
  return config
}, error => {
  return Promise.reject(error)
});
//HTTPrequest拦截
axios.interceptors.response.use(config => {
  // loadingInstance.close();
  return config;
}, error => {
  // loadingInstance.close();
  return Promise.reject(new Error(error));
})

export default axios;
