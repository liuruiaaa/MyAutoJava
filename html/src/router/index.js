/**
 * 全站路由配置
 *
 * 建议:
 * 1. 代码中路由统一使用name属性跳转(不使用path属性)
 */
import Vue from 'vue'
import Router from 'vue-router'
import http from '@/utils/httpRequest'
import { isURL } from '@/utils/validate'
import { clearLoginInfo } from '@/utils'

Vue.use(Router)

// 开发环境不使用懒加载, 因为懒加载页面太多的话会造成webpack热更新太慢, 所以只有生产环境使用懒加载
const _import = require('./import-' + process.env.NODE_ENV)

// 全局路由(无需嵌套上左右整体布局)
const globalRoutes = [
  { path: '/404', component: _import('common/404'), name: '404', meta: { title: '404未找到' } },
  { path: '/login', component: _import('common/login'), name: 'login', meta: { title: '登录' } }
]

// 主入口路由(需嵌套上左右整体布局)
const mainRoutes = {
  path: '/',
  component: _import('main'),
  name: 'main',
  redirect: { name: 'home' },
  meta: { title: '主入口整体布局' },
  children: [
    // 通过meta对象设置路由展示方式
    // 1. isTab: 是否通过tab展示内容, true: 是, false: 否
    // 2. iframeUrl: 是否通过iframe嵌套展示内容, '以http[s]://开头': 是, '': 否
    // 提示: 如需要通过iframe嵌套展示内容, 但不通过tab打开, 请自行创建组件使用iframe处理!
    { path: '/home', component: _import('common/home'), name: 'home', meta: { title: '首页' } },
    { path: '/theme', component: _import('common/theme'), name: 'theme', meta: { title: '主题' } },
    { path: '/demo-echarts', component: _import('demo/echarts'), name: 'demo-echarts', meta: { title: 'demo-echarts', isTab: true } },
    { path: '/demo-ueditor', component: _import('demo/ueditor'), name: 'demo-ueditor', meta: { title: 'demo-ueditor', isTab: true } },
    // 这个是我后来加的
    { path: '/testt-test', component: _import('testt/test'), name: 'testt-test', meta: { title: 'testt-test', isTab: true } }
  ],
  beforeEnter (to, from, next) {
    let token = Vue.cookie.get('token')
    if (!token || !/\S/.test(token)) {
      clearLoginInfo()
      next({ name: 'login' })
    }
    next()
  }
}

const router = new Router({
  mode: 'hash',
  scrollBehavior: () => ({ y: 0 }),
  isAddDynamicMenuRoutes: false, // 是否已经添加动态(菜单)路由
  routes: globalRoutes.concat(mainRoutes)
})

router.beforeEach((to, from, next) => {
  // 添加动态(菜单)路由
  // 1. 已经添加 or 全局路由, 直接访问
  // 2. 获取菜单列表, 添加并保存本地存储
  if (router.options.isAddDynamicMenuRoutes || fnCurrentRouteType(to, globalRoutes) === 'global') {
    next()
  } else {
    http({
      url: http.adornUrl('/sys/menu/nav'),
      method: 'get',
      params: http.adornParams()
    }).then(({data}) => {
      if (data && data.code === 0) {
        fnAddDynamicMenuRoutes(data.menuList)
        router.options.isAddDynamicMenuRoutes = true
        sessionStorage.setItem('menuList', JSON.stringify(data.menuList || '[]'))
        sessionStorage.setItem('permissions', JSON.stringify(data.permissions || '[]'))
        next({ ...to, replace: true })
      } else {
        sessionStorage.setItem('menuList', '[]')
        sessionStorage.setItem('permissions', '[]')
        next()
      }
    }).catch((e) => {
      console.log(`%c${e} 请求菜单列表和权限失败，跳转至登录页！！`, 'color:blue')
      router.push({ name: 'login' })
    })
  }
})

/**
 * 判断当前路由类型, global: 全局路由, main: 主入口路由
 * @param {*} route 当前路由
 */
function fnCurrentRouteType (route, globalRoutes = []) {
  var temp = []
  for (var i = 0; i < globalRoutes.length; i++) {
    if (route.path === globalRoutes[i].path) {
      return 'global'
    } else if (globalRoutes[i].children && globalRoutes[i].children.length >= 1) {
      temp = temp.concat(globalRoutes[i].children)
    }
  }
  return temp.length >= 1 ? fnCurrentRouteType(route, temp) : 'main'
}

/**
 * 添加动态(菜单)路由
 * @param {*} menuList 菜单列表
 * @param {*} routes 递归创建的动态(菜单)路由
 */
// 定义用于添加动态菜单路由的函数，接受菜单列表和已递归创建的动态路由作为参数，并提供默认值
function fnAddDynamicMenuRoutes(menuList = [], routes = []) {
  // 声明一个空数组用于临时存储
  var temp = [];
  // 遍历菜单列表
  for (var i = 0; i < menuList.length; i++) {
    // 如果当前菜单项有子列表并且子列表长度大于等于1
    if (menuList[i].list && menuList[i].list.length >= 1) {
      // 将子列表与临时数组合并
      temp = temp.concat(menuList[i].list);
      // 如果菜单项有url且url不为空（/\S/ 检测是否包含非空白字符）
    } else if (menuList[i].url && /\S/.test(menuList[i].url)) { 
      // 去除url开头的斜杠
      menuList[i].url = menuList[i].url.replace(/^\//, '');
      // 定义一个路由对象，将url中的斜杠转换为短横线作为路由的path和name
      var route = {
        path: menuList[i].url.replace('/', '-'),
        component: null,
        name: menuList[i].url.replace('/', '-'),
        meta: {
          menuId: menuList[i].menuId,
          title: menuList[i].name,
          isDynamic: true,
          isTab: true,
          iframeUrl: ''
        }
      };
      // 如果url以http[s]开始，设置路由为iframe形式展现
      if (isURL(menuList[i].url)) {
        route['path'] = `i-${menuList[i].menuId}`;
        route['name'] = `i-${menuList[i].menuId}`;
        route['meta']['iframeUrl'] = menuList[i].url;
      } else {
        // 否则尝试导入组件，如果失败则组件为空(这里相当于做了检查！！！)
        try {
          route['component'] = _import(`modules/${menuList[i].url}`) || null;
        } catch (e) {}
      }
      // 将构建的路由添加到路由数组中
      routes.push(route);
    }
  }
  // 如果临时数组长度大于等于1，递归调用函数处理子菜单
  if (temp.length >= 1) {
    fnAddDynamicMenuRoutes(temp, routes);
  } else { // 否则表示所有菜单项处理完成，开始配置路由
    mainRoutes.name = 'main-dynamic'; // 设置主路由的名称
    mainRoutes.children = routes; // 将构造的路由设置为主路由的子路由
    // 将主路由及404重定向路由添加到路由器
    router.addRoutes([
      mainRoutes,
      { path: '*', redirect: { name: '404' } }
    ]);
    // 将动态路由信息保存到sessionStorage，便于页面刷新后恢复
    sessionStorage.setItem('dynamicMenuRoutes', JSON.stringify(mainRoutes.children || '[]'));
    // 控制台输出动态路由信息，用于调试
    console.log('\n');
    console.log('%c!<-------------------- 动态(菜单)路由 s -------------------->', 'color:blue');
    console.log(mainRoutes.children);
    console.log('%c!<-------------------- 动态(菜单)路由 e -------------------->', 'color:blue');
  }
}

export default router
