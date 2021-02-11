<template>
  <div>
    <div v-if="usernameGetter">
      <h4>You are logged in as {{ usernameGetter }}</h4>
    </div>
    <v-container>
      <v-row>
        <v-col class="col-md-3">
          <Filters/>
        </v-col>

        <v-col class="col-md-9">
              <v-row>
                <v-col class="col-sm-12 col-md-6">
                  <Book/>
                </v-col>
                <v-col class="col-sm-12 col-md-6">
                  <Book/>
                </v-col>
              </v-row>
        </v-col>
      </v-row>
    </v-container>

  </div>
</template>

<script>

import {mapGetters} from "vuex";
import Book from "@/components/Book";
import Filters from "@/components/Filters";

export default {
  name: 'Home',
  components: {
    Book,
    Filters
  },
  computed: {
    ...mapGetters([
      'usernameGetter',
    ]),
  }
}
</script>
