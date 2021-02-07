<template>
  <v-app>

    <v-app-bar app>
      <v-container class="d-flex justify-space-between align-center">
        <v-toolbar-title style="cursor: pointer" @click="$router.push('/')">Library</v-toolbar-title>
        <v-spacer/>

        <v-btn
            class="mr-4"
            @click="$router.push('/sprint-plan')"
        >
          Sprint plan
        </v-btn>

        <div>
          <v-btn
              v-if="usernameGetter === null"
              class="mr-4"
              color="green"
              @click="$router.push('/login')"
          >
            Login
          </v-btn>
          <v-btn
              v-else
              class="mr-4"
              color="red"
              @click="logoutAction"
          >
            Logout
          </v-btn>
        </div>

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
import {mapActions, mapGetters} from "vuex";

export default {
  name: 'App',

  created() {
    const username = localStorage.getItem("username");
    if (username) {
      this.usernameMutation(username)
    }
  },

  computed: {
    ...mapGetters([
      'usernameGetter',
    ]),
  },

  methods: {
    ...mapActions([
      'logoutAction',
    ]),
  }
};
</script>
