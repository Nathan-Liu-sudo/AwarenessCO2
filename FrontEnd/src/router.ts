import { createRouter, createWebHashHistory  } from 'vue-router';
import Welcome from './components/Welcome.vue';
import Cal from './components/Calculator.vue';

const router = createRouter({
  history: createWebHashHistory (),
  routes: [
    // 通配符路由，将所有未匹配的路由重定向到根路由
    { path: '/:pathMatch(.*)*', redirect: '/' },
    { path: '/', component: Welcome },
    { path: '/cal', component: Cal }
  ]
});

export default router;
