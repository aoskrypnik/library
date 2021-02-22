import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";
import router from '../router'

Vue.use(Vuex)

const endpoint = 'http://localhost:9005';

export default new Vuex.Store({
    state: {
        username: null,
    },
    getters: {
        usernameGetter: state => {
            return state.username
        },
    },
    mutations: {
        usernameMutation(state, username) {
            state.username = username
        },
    },
    actions: {
        loginAction({commit}, {username, password}) {
            axios.post(`${endpoint}/auth/login`, {
                username: username,
                password: password
            }).then(response => {
                localStorage.setItem('jwt', response.data.jwt)
                localStorage.setItem('expirationDate', response.data.expirationDate)
                localStorage.setItem('username', response.data.username)
                commit("usernameMutation", response.data.username)
                if (this.redirect !== '' && this.redirect !== undefined) {
                    router.push(this.redirect)
                } else {
                    router.push('/')
                }
                return true
            }).catch(error => {
                console.log(error)
                return false
            });
        },
        registerAction({commit}, {username, password, confirmationPassword, realName, surname, phoneNumber, birthDate, email}) {
            axios.post(`${endpoint}/readers/register`, {
                username: username,
                password: password,
                confirmationPassword:confirmationPassword,
                realName: realName,
                surname: surname,
                phoneNumber: phoneNumber,
                birthDate: birthDate,
                email: email,
            }).then(response => {
                localStorage.setItem('jwt', response.data.jwt)
                localStorage.setItem('expirationDate', response.data.expirationDate)
                localStorage.setItem('username', response.data.username)
                commit("usernameMutation", response.data.username)
                if (this.redirect !== '' && this.redirect !== undefined) {
                    router.push(this.redirect)
                } else {
                    router.push('/')
                }
                return true
            }).catch(error => {
                console.log(error)
                return false
            });
        },
        logoutAction({commit}) {
            localStorage.removeItem("jwt")
            localStorage.removeItem("username")
            localStorage.removeItem("expirationDate")
            commit("usernameMutation", null)
            if (router.currentRoute.path !== '/') {
                router.push('/');
            }
        },
        orderAction(){},

    },
})
