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
        storage: [],
        storageNum: 0
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
        storageGetter: state => {
            return state.storage
        },
        storageNumGetter: state => {
            return state.storageNum
        }
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
        storageAddMutation(state, {book}) {
            state.storage.push(book)
            state.storageNum++
            localStorage.setItem("storage", JSON.stringify(state.storage))
            localStorage.setItem("storageNum",  JSON.stringify(state.storageNum))
        },
        storageRestoreMutation(state) {
            if (localStorage.getItem("storage")) {
                state.storage = JSON.parse(localStorage.getItem("storage"))
            }
            if (localStorage.getItem("storageNum")) {
                state.storageNum = JSON.parse(localStorage.getItem("storageNum"))
            }
        },
        storageRemoveAllMutation(state) {
            state.storage = []
            state.storageNum = 0
            localStorage.removeItem("storage")
            localStorage.removeItem("storageNum")
        }
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
