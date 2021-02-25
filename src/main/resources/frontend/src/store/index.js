import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";
import router from '../router'

Vue.use(Vuex)

const endpoint = 'http://localhost:9005';

export default new Vuex.Store({
    state: {
        username: null,
        role: null,
        genres: [],
        authors: [],
    },

    getters: {
        usernameGetter: state => {
            return state.username
        },
        roleGetter: state => {
            return state.role
        },
        genresGetter: state => {
            return state.genres

        },
        authorsGetter: state => {
            return state.authors
        },
    },

    mutations: {
        userMutation(state, {username, role}) {
            state.username = username
            state.role = role
        },
        genresMutation(state, {genres}) {
            state.genres = genres
        },
        authorsMutation(state, {authors}) {
            state.authors = authors
        },
    },

    actions: {
        loadGenresAction({commit}) {
            axios.get(`${endpoint}/genres`).then(response => {
                let genres = response.data
                commit("genresMutation", {genres: genres})
            })
        },
        loadAuthorsAction({commit}) {
            axios.get(`${endpoint}/authors`).then(response => {
                let authors = response.data
                commit("authorsMutation", {authors: authors})
            })
        },
        loginAction({commit}, {username, password}) {
            return new Promise((resolve, reject) => {
                axios.post(`${endpoint}/auth/login`, {
                    username: username,
                    password: password
                }).then(response => {
                    localStorage.setItem('jwt', response.data.jwt)
                    localStorage.setItem('expirationDate', response.data.expirationDate)
                    localStorage.setItem('username', response.data.username)
                    localStorage.setItem('role', response.data.role)
                    commit("userMutation", {username: response.data.username, role: response.data.role})
                    if (this.redirect !== '' && this.redirect !== undefined) {
                        router.push(this.redirect)
                    } else {
                        router.push('/')
                    }
                    resolve(true)
                }).catch(error => {
                    console.log(error)
                    reject(false)
                });
            })
        },

        registerAction({commit}, {
            username,
            password,
            confirmationPassword,
            realName,
            surname,
            phoneNumber,
            birthDate,
            email
        }) {
            return new Promise((resolve, reject) => {
                axios.post(`${endpoint}/readers/register`, {
                    username: username,
                    password: password,
                    confirmationPassword: confirmationPassword,
                    realName: realName,
                    surname: surname,
                    phoneNumber: phoneNumber,
                    birthDate: birthDate,
                    email: email,
                }).then(response => {
                    localStorage.setItem('jwt', response.data.jwt)
                    localStorage.setItem('expirationDate', response.data.expirationDate)
                    localStorage.setItem('username', response.data.username)
                    localStorage.setItem('role', response.data.role)
                    commit("userMutation", {username: response.data.username, role: response.data.role})
                    if (this.redirect !== '' && this.redirect !== undefined) {
                        router.push(this.redirect)
                    } else {
                        router.push('/')
                    }
                    resolve(true)
                }).catch(error => {
                    console.log(error)
                    reject(false)
                });
            })
        },

        logoutAction({commit}) {
            localStorage.removeItem("jwt")
            localStorage.removeItem("username")
            localStorage.removeItem("expirationDate")
            localStorage.removeItem("role")
            commit("userMutation", {username: null, role: null})
            if (router.currentRoute.path !== '/') {
                router.push('/');
            }
        },

        orderAction() {
        },

    },
})
