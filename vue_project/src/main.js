import App from './App.vue'
import router from './route/router.js'
import { createApp } from 'vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap.js"
import "./common.css"


createApp(App).use(router).mount('#app')
