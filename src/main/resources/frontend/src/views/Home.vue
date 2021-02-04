<template>
  <div>
    <h1>Main page</h1>
    <div v-if="username !== 'null' && username !== undefined">
      <h4>You are logged in as {{ username }}</h4>
      <v-btn
          class="mr-4"
          color="red"
          @click="doLogout"
      >
        Logout
      </v-btn>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data: () => ({
    username: undefined
  }),

  created() {
    this.username = localStorage.username
  },

  methods: {
    doLogout() {
      localStorage.setItem("jwt", null)
      localStorage.setItem("username", null)
      localStorage.setItem("expirationDate", null)
      this.$router.push('/');
    }
  }
}
</script>
