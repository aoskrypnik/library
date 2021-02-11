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
          depressed
          class="mr-4"
          @click="submit"
      >
        Log in
      </v-btn>
    </div>
  </v-form>
</template>

<script>
import {mapActions} from "vuex";

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
    ...mapActions([
      'loginAction',
    ]),
    async submit() {
      if (this.$refs.form.validate()) {
        this.validCreds = await this.loginAction({username: this.username, password: this.password})
      }
    },
    deleteAlert() {
      this.validCreds = true
    }
  },
}
</script>