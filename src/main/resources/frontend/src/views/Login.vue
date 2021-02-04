<template>
  <v-form
      ref="form"
      v-model="valid"
      lazy-validation
  >
    <v-alert
        border="top"
        color="red lighten-2"
        dark
        v-if="!validCreds"
    >
      Credentials are not valid :(
    </v-alert>

    <v-text-field
        @click="deleteAlert"
        v-model="username"
        :rules="rules"
        label="Username"
        required
    ></v-text-field>

    <v-text-field
        @click="deleteAlert"
        v-model="password"
        :rules="rules"
        :type="'password'"
        label="Password"
        required
    ></v-text-field>

    <div class="d-flex">
      <v-btn
          :disabled="!valid"
          color="success"
          class="mr-4"
          @click="submit"
      >
        Log in
      </v-btn>

      <v-btn
          :disabled="!valid"
          color="blue"
          class="mr-4 white--text"
          @click="$router.push(`/registration`)"
      >
        Register
      </v-btn>
    </div>
  </v-form>
</template>

<script>
import axios from "axios";

const endpoint = 'http://localhost:9005';

export default {
  name: "Login",

  data: () => ({
    valid: false,
    username: '',
    password: '',
    rules: [
      v => !!v || 'Is required',
    ],
    validCreds: true,
    redirect: ''
  }),

  created() {
    this.redirect = this.$route.params.redirect
  },

  methods: {
    submit() {
      if (this.$refs.form.validate()) {
        axios.post(`${endpoint}/auth/login`, {
          username: this.username,
          password: this.password
        }).then(response => {
          localStorage.setItem('jwt', response.data.jwt)
          localStorage.setItem('expirationDate', response.data.expirationDate)
          localStorage.setItem('username', response.data.username)
          if (this.redirect !== '' && this.redirect !== undefined) {
            this.$router.push(this.redirect)
          } else {
            this.$router.push('/')
          }
        }).catch(error => {
          this.validCreds = false
          console.log(error)
        });
      }
    },
    deleteAlert() {
      this.validCreds = true
    }
  },
}
</script>