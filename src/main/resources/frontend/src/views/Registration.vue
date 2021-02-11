<template>
  <!--  <h1>Registration page</h1>-->
  <v-form
      ref="form_Reg"
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
        v-model="new_username"
        :rules="rules"
        label="Username"
        required
    ></v-text-field>

    <v-text-field
        @click="deleteAlert"
        v-model="new_password"
        :rules="rules"
        :type="'password'"
        label="Password"
        required
    ></v-text-field>

    <v-text-field
        @click="deleteAlert"
        v-model="re_password"
        :rules="[rules, passwordConfirmationRule]"
        :type="'password'"
        label="Confirm password"
        required
    ></v-text-field>

    <v-text-field
        v-model="name"
        :counter="10"
        :rules="rules"
        label="Name"
        required
    ></v-text-field>

    <v-text-field
        v-model="surname"
        :counter="15"
        :rules="rules"
        label="Surname"
        required
    ></v-text-field>

    <v-text-field
        v-model="email"
        :rules="emailRules"
        label="E-mail"
        required
    ></v-text-field>
    <v-text-field
        v-model="phoneNumber"
        :rules="phoneRules"
        :counter="10"
        :error-messages="errors"
        label="Phone Number"
        required
    ></v-text-field>

    <v-menu
        ref="menu"
        v-model="menu"
        :close-on-content-click="false"
        transition="scale-transition"
        offset-y
        min-width="auto"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-text-field
            v-model="birth_date"
            label="Date of birth"
            prepend-icon="mdi-calendar"
            readonly
            :rules="rules"
            v-bind="attrs"
            v-on="on"
        ></v-text-field>
      </template>
      <v-date-picker
          ref="picker"
          v-model="birth_date"
          color="green lighten-1"
          :max="new Date().toISOString().substr(0, 10)"
          min="1950-01-01"
          @change="save"
      ></v-date-picker>
    </v-menu>
    <div class="d-flex">
      <v-btn
          :disabled="!valid"
          color="success"
          depressed
          class="mr-4"
          @click="submit"
      >
        Register
      </v-btn>

    </div>
  </v-form>
</template>

<script>
import {mapActions} from "vuex";

export default {
  name: "Registration",
  data: () => ({
    valid: false,
    new_username: '',
    new_password: '',
    re_password: '',
    name: '',
    surname: '',
    email: '',
    phoneNumber: '',
    birth_date: null,
    menu: false,
    errors:'',
    emailRules: [
      v => !!v || 'E-mail is required',
      v => /.+@.+\..+/.test(v) || 'E-mail must be valid',

    ],
    phoneRules: [
      v => !!v || 'Phone number is required',
      v => /^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/im.test(v) || 'Phone number must be valid',
    ],
    rules: [
      v => !!v || 'Is required',
    ],
    validCreds: true,
    redirect: ''
  }),
  computed: {
    passwordConfirmationRule() {
      return () => (this.new_password === this.re_password) || 'Password must match'
    }
  },
  watch: {
    menu(val) {
      val && setTimeout(() => (this.$refs.picker.activePicker = 'YEAR'))
    },
  },

  created() {
    this.redirect = this.$route.params.redirect
  },

  methods: {
    ...mapActions([
      'registerAction',
    ]),
    save(date) {
      this.$refs.menu.save(date)
    },

    async submit() {
      if (this.$refs.form_Reg.validate()) {
        this.validCreds = await this.registerAction({
          username: this.new_username,
          password: this.new_password,
          confirmationPassword: this.re_password,
          realName: this.name,
          surname: this.surname,
          phoneNumber: this.phoneNumber,
          birthDate: this.birth_date,
          email: this.email,
        })
      }
    },
    deleteAlert() {
      this.validCreds = true
    }
  },
}
</script>
