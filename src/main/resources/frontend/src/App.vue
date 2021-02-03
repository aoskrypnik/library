<template>
  <v-app>

    <v-app-bar app>
      <v-container class="d-flex justify-space-between align-center">
        <v-toolbar-title style="cursor: pointer" @click="$router.push('/')">Library</v-toolbar-title>
        <v-spacer/>

        <v-btn
            class="mr-4"
            @click="goToSprintPlanPage"
        >
          Sprint plan
        </v-btn>

        <v-btn
            v-if="username === 'null' || username === undefined"
            class="mr-4"
            color="green"
            @click="goToLogin"
        >
          Login
        </v-btn>

      </v-container>
    </v-app-bar>

    <v-main>
      <v-container>
        <router-view></router-view>
      </v-container>
    </v-main>

  </v-app>
</template>

<script>

export default {
  name: 'App',

  data: () => ({
    username: null
  }),

  created() {
    this.username = localStorage.username
  },

  methods: {
    goToSprintPlanPage() {
      this.$router.push('/sprint-plan')
    },
    goToLogin() {
      this.$router.push('/login')
    },
  }
};
</script>
