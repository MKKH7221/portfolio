import { createRouter, createWebHistory } from 'vue-router'
import Home from '../pages/Home.vue'
import Search from '../pages/app/Search.vue'
import Detail from '../pages/app/Edit.vue'
import Add from '../pages/app/Add.vue'


const routes = [
    { 
        path: '/', 
        name: 'Home',
        component: Home
    },
    {
        path: '/search', 
        name: 'Search',
        component: Search
    },
    {
        path: '/edit/:id', 
        name: 'Edit',
        component: Detail,
        // props: route => ({id: route.params.id})
    },
    {
        path: '/add', 
        name: 'Add',
        component: Add
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
