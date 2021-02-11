<template>
  <v-app>

    <v-app-bar app>
      <v-container class="d-flex justify-space-between align-center">
        <v-toolbar-title style="cursor: pointer" @click="$router.push('/')">Library</v-toolbar-title>
        <v-spacer/>

        <v-btn
            class="mr-4"
            text
            @click="$router.push('/sprint-plan')"
        >
          Sprint plan
        </v-btn>
        <v-btn
            v-if="usernameGetter != null"
            text
            color="primary"
            class="mr-4"
            @click="$router.push('/save-book')"
        >
          <v-icon left>mdi-book-outline</v-icon>
          Add book
        </v-btn>

        <div>
          <v-btn
              v-if="usernameGetter === null"
              class="mr-4"
              text
              color="primary"
              @click="$router.push('/login')"
          >
            <v-icon left>mdi-account</v-icon>
            Login
          </v-btn>
          <v-btn
              v-if="usernameGetter === null"
              color="primary"
              depressed
              class="mr-4"
              @click="$router.push(`/registration`)"
          >
            Register
          </v-btn>
          <v-btn
              v-else
              class="mr-4"
              text
              color="error"
              @click="logoutAction"
          >
            <v-icon left>mdi-logout-variant</v-icon>
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
