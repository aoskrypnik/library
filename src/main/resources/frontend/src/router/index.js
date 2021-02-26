import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import SprintPlan from "@/views/SprintPlan";
import Login from "@/views/Login";
import Registration from "@/views/Registration";
import BookSaveForm from "@/views/BookSaveForm";
import BookEditForm from "@/views/BookEditForm";
import BookOrder from "@/views/BookOrderCreate";
import OrdersList from "@/views/OrdersList";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/sprint-plan',
        name: 'SprintPlan',
        component: SprintPlan
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/registration',
        name: 'Registration',
        component: Registration
    },
    {
        path: '/save-book',
        name: 'BookSaveForm',
        component: BookSaveForm
    },
    {
        path: '/edit-book',
        name: 'BookEditForm',
        component: BookEditForm
    },
    {
        path: '/book-order',
        name: 'BookOrder',
        component: BookOrder
    },
    {
        path: '/orders-list',
        name: 'OrdersList',
        component: OrdersList
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
